package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UserRoleACL;
import vn.molu.repository.admin.UserRoleACLRepository;
import vn.molu.service.admin.UserRoleACLService;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Service
public class UserRoleACLServiceImpl implements UserRoleACLService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private UserRoleACLRepository userRoleACLRepository;

    @Override
    public List<UserRoleACL> findByUserId(Long userId) {
        return userRoleACLRepository.findByUserId(userId);
    }

    @Override
    public int insert(Long userId, Long[] userRoleIds) {
        return 0;
    }

    @Override
    public int delete(Long userId, Long[] userRoleIds) {
        return 0;
    }

    @Override
    public void delete(Long userRoleACLId) {
        log.info("Delete with id: " + userRoleACLId);

        try {
            userRoleACLRepository.deleteById(userRoleACLId);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<UserRoleACL> findAll() {
        log.info("Find all");

        try {
            return userRoleACLRepository.findAll();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRoleACL save(UserRoleACL pojo) {
        log.info("save a userRoleACL");

        try {
            pojo = userRoleACLRepository.save(pojo);
            userRoleACLRepository.refresh(pojo);
            return pojo;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRoleACL findById(Long userRoleACLId) {
        log.info("save a UserRoleACL");

        try {
            return userRoleACLRepository.findById(userRoleACLId).get();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRoleACL update(UserRoleACL pojo) {
        log.info("update a UserRoleACL");

        try {
            return userRoleACLRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

}
