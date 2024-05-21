package vn.molu.webapp.security;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.molu.common.Constants;
import vn.molu.common.utils.DesEncrypterUtils;
import vn.molu.common.utils.PasswordUtils;
import vn.molu.common.utils.WebCommonUtil;
import vn.molu.domain.admin.User;
import vn.molu.service.admin.UserService;

public class MyPasswordEncoder implements PasswordEncoder {
    private transient final Logger log = Logger.getLogger(getClass());

    @Autowired
    private LdapUserLookup ldapUserLookup;

    @Autowired
    private  UserService userService;

    public void setLdapUserLookup(LdapUserLookup ldapUserLookup) {
        this.ldapUserLookup = ldapUserLookup;
    }

    @Override
    public String encode(CharSequence charSequence) {
        return DesEncrypterUtils.getInstance().encrypt(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        boolean res = false;
        if (StringUtils.isNotEmpty(s)) {
            String[] userPassArr = WebCommonUtil.splitUsernameAndPassword(s);
            String userName = userPassArr[0];
            String encryptPass = userPassArr[1];
            try {
                res = encryptPass.split(Constants.DELIMITER_COMMA)[0].equals(DesEncrypterUtils.getInstance().encrypt(charSequence.toString())) ||
                        encryptPass.split(Constants.DELIMITER_COMMA)[1].equals(PasswordUtils.encryptSHA(charSequence.toString()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            if (!res) {
                try {
//                    res = ldapUserLookup.authenticate(userName, charSequence.toString());
                    LdapUser ldapUser=ldapUserLookup.getUser(userName);
                    if (ldapUser!=null){
                        User user=userService.loadUserByUsername(userName);
                        if(user!=null){
                            res=true;
                        }else {
                            res=false;
                        }
                    }else {
                        res=false;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return res;
    }
}
