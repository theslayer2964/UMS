package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.molu.common.utils.DesEncrypterUtils;
import vn.molu.dao.admin.UserDAO;
import vn.molu.domain.admin.User;
import vn.molu.domain.admin.UserRole;
import vn.molu.repository.admin.UserRepository;
import vn.molu.service.admin.UserService;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDAO userDAO;


    @Override
    public User loadUserByUsername(String username) {
        log.info("Find with username: " + username);

        try {
            return userRepository.getUserByUserName(username);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<User> findUser(Map<String, Object> props) {
        return userRepository.findByProperties(props);
    }

    @Override
    public List<String> findUrlByUserId(Long userId) {
        log.info("Get list of URL with userId: " + userId);
        try {
            return userDAO.findUrlsByUserId(userId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User findByEmail(String email) {
        log.info("Find with email: " + email);

        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delete(Long userId) {
        log.info("Delete with id: " + userId);

        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        log.info("Find all");

        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User save(User pojo) {
        log.info("save a User");

        try {
            return userRepository.save(pojo);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User findById(Long urlGroupId) {
        log.info("find a Url");

        try {
            return userRepository.findById(urlGroupId).get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User update(User pojo) {
        log.info("update a User");

        try {
            return userRepository.save(pojo);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User findByUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return userRepository.findByName(userDetails.getUsername());
            }
            return new User();
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public User getUserLogin_BHTT_System() {
        User userLogin = new User();
        userLogin.setUserName("TRI.HOANGMINH");
        userLogin.setPassword(DesEncrypterUtils.getInstance().encrypt("Theslayer@2964"));
        return userLogin;
    }
}
