package vn.molu.webapp.security;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.molu.common.Constants;
import vn.molu.common.utils.DesEncrypterUtils;
import vn.molu.common.utils.PasswordUtils;
import vn.molu.common.utils.WebCommonUtil;
import vn.molu.dao.CommonDAO;
import vn.molu.domain.admin.User;
import vn.molu.dto.admin.admin.C2UserDTO;
import vn.molu.service.admin.UserService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
	private transient final Logger log = Logger.getLogger(getClass());

	protected UserCache userCache = null;

	private final UserService userService;

	private final CommonDAO commonDAO;

	private final LdapUserLookup ldapUserLookup;

	/**
	 * Creates new instance of MyUserDetailService
	 * @param userService
	 * @param commonDAO
	 * @param ldapUserLookup
	 */
	public MyUserDetailService(UserService userService, CommonDAO commonDAO, LdapUserLookup ldapUserLookup) {

		this.userService = userService;
		this.commonDAO = commonDAO;
		this.ldapUserLookup = ldapUserLookup;
	}

	/**
	 * Set UserCache
	 *
	 * @param userCache
	 *            user cache to set
	 */
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	/**
	 * Locates the user based on the username. In the actual implementation, the
	 * search may possibly be case insensitive, or case insensitive depending on
	 * how the implementaion instance is configured. In this case, the
	 * <code>UserDetails</code> object that comes back may have a username that
	 * is of a different case than what was actually requested..
	 *
	 * @param passUserName
	 *            the username presented to the
	 *            {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
	 *             if the user could not be found or the user has no
	 *             GrantedAuthority
	 * @throws org.springframework.dao.DataAccessException
	 *             if user could not be found for a repository-specific reason
	 */
	public UserDetails loadUserByUsername(String passUserName) throws UsernameNotFoundException, DataAccessException {
		String[] userPassArr = WebCommonUtil.splitUsernameAndPassword(passUserName);
		String username = userPassArr[0].toLowerCase();
		String password = userPassArr[1];

		User account = null;

		// Check user already have been exists
		LdapUser ldapUser = null;
		try { // login with UMS table USer
			account = userService.loadUserByUsername(username);
			if (account != null && account.getAccountType() == 3){
				C2UserDTO c2UserDTO = commonDAO.findActiveUserByUserName(username);
				if (c2UserDTO != null) {
					account.setPassword(account.getPassword() + Constants.DELIMITER_COMMA + c2UserDTO.getPassword());
				}
			}
		} catch (Exception oe) {
			oe.printStackTrace();
			log.info("User is not exists!");
		}

		if (account == null) { // login with UMS check in C2_admin_user
			C2UserDTO c2UserDTO = commonDAO.findActiveUserByUserName(username);
			if (c2UserDTO != null){
				account = new User();
				account.setUserName(c2UserDTO.getUserName().toLowerCase());
				account.setPassword(c2UserDTO.getPassword());
				account.setDisplayName(c2UserDTO.getFullName());
				account.setFlgDelete(Constants.FLG_DELETE_ON);
				account.setAccountType(3);
				account.setCreatedDate(new Timestamp(new Date().getTime()));

				if(account.getPassword().equals(DesEncrypterUtils.getInstance().encrypt(password))||account.getPassword().equals(PasswordUtils.encryptSHA(password))){
					userService.save(account);
				}
				account.setPassword(account.getPassword() + Constants.DELIMITER_COMMA + c2UserDTO.getPassword());
			}
		}

		if (account == null) {// login with UMS check in employee
			try {
				boolean res = ldapUserLookup.authenticate(username, password);
				if (res) {
					ldapUser = ldapUserLookup.getUser(username);
					if (ldapUser != null) {
						account = new User();
						account.setUserName(username.toLowerCase());
						account.setPassword(DesEncrypterUtils.getInstance().encrypt(password));
						account.setDisplayName(ldapUser.getDisplayName());
						account.setFlgDelete(Constants.FLG_DELETE_ON);
						account.setAccountType(2);
						account.setCreatedDate(new Timestamp(new Date().getTime()));

						account = userService.save(account);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw e;
			}
		}

		if (account == null) {
			throw new UsernameNotFoundException("UserProcessingFilter.usernameNotFound:" + username);
		}

		if (account.getFlgDelete() == null) {
			throw new UsernameNotFoundException(
					"UserProcessingFilter.usernameNotFound:" + username + " .Flag of delete is NULL");
		} else if (!account.getFlgDelete().equals(Constants.FLG_DELETE_ON)) {
			if (account.getFlgDelete().equals(Constants.FLG_DELETE_OFF)) {
				throw new LockedException("User is deleted:" + username);
			} else {
				throw new UsernameNotFoundException(
						"UserProcessingFilter.usernameNotFound:" + username + ". Flag of delete not available.");
			}
		}

		// this line of code is used to check whether the user has login or not
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(Constants.GLOBAL_META_ROLE_PREFIX + Constants.LOGON_ROLE));
		if (account.getUserName().equalsIgnoreCase(Constants.ADMIN_ROLE)) {
			authorities.add(new SimpleGrantedAuthority(Constants.GLOBAL_META_ROLE_PREFIX + Constants.ADMIN_ROLE));
		}

		List<String> listOfUrl = userService.findUrlByUserId(account.getUserId());
		for (String url : listOfUrl) {
			authorities.add(new SimpleGrantedAuthority(Constants.GLOBAL_META_ROLE_PREFIX + url));
		}

		MyUserDetail loginUser = new MyUserDetail(username, username + Constants.SECURITY_CREDENTIAL_DELIMITER + account.getPassword() + Constants.SECURITY_CREDENTIAL_DELIMITER + account.getAccountType(), account.getDisplayName(), true, true, true, true, authorities);

		BeanUtils.copyProperties(account, loginUser);
		return loginUser;
	}
}