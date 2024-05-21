package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.UserGroupRoleACL;
import vn.molu.repository.admin.UserGroupRoleACLRepository;
import vn.molu.service.admin.UserGroupRoleACLService;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Service
public class UserGroupRoleACLServiceImpl implements UserGroupRoleACLService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private UserGroupRoleACLRepository userGroupRoleACLRepository;

    @Override
    public void delete(Long urlId) {
        log.info("Delete with id: " + urlId);

        try {
            userGroupRoleACLRepository.deleteById(urlId);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<UserGroupRoleACL> findAll() {
        log.info("Find all");

        try {
            return userGroupRoleACLRepository.findAll();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserGroupRoleACL save(UserGroupRoleACL pojo) {
        log.info("save a UserGroupRoleACL");

        try {

            pojo = userGroupRoleACLRepository.save(pojo);
            userGroupRoleACLRepository.refresh(pojo);

            return pojo;
//            return userGroupRoleACLDAO.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserGroupRoleACL findById(Long userGroupRoleACLId) {
        log.info("find a UserGroupRoleACL by id");

        try {
            return userGroupRoleACLRepository.findById(userGroupRoleACLId).get();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserGroupRoleACL update(UserGroupRoleACL pojo) {
        log.info("update a UserGroupRoleACL");

        try {
            return userGroupRoleACLRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<UserGroupRoleACL> findByUserGroupId(Long userGroupId) {
        return userGroupRoleACLRepository.findByUserGroupId(userGroupId);
    }
}
