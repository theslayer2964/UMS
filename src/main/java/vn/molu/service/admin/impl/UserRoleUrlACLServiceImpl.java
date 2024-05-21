package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UserRoleUrlACL;
import vn.molu.repository.admin.UserRoleACLRepository;
import vn.molu.repository.admin.UserRoleUrlACLRepository;
import vn.molu.service.admin.UserRoleUrlACLService;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Service
public class UserRoleUrlACLServiceImpl implements UserRoleUrlACLService {
    private final Logger log = Logger.getLogger(getClass().toString());
    @Autowired
    private UserRoleUrlACLRepository userRoleUrlACLRepository;

    @Override
    public void delete(Long urlId) {
        log.info("Delete with id: " + urlId);

        try {
            userRoleUrlACLRepository.deleteById(urlId);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<UserRoleUrlACL> findAll() {
        log.info("Find all");

        try {
            return userRoleUrlACLRepository.findAll();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRoleUrlACL save(UserRoleUrlACL pojo) {
        log.info("save a UserRoleUrlACL");

        try {
            pojo = userRoleUrlACLRepository.save(pojo);
            userRoleUrlACLRepository.refresh(pojo);

            return pojo;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRoleUrlACL findById(Long urlGroupId) {
        log.info("find a UserRoleUrlACL"+urlGroupId);

        try {
            return userRoleUrlACLRepository.findById(urlGroupId).get();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRoleUrlACL update(UserRoleUrlACL pojo) {
        log.info("update a UserRoleUrlACL");

        try {
            return userRoleUrlACLRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<UserRoleUrlACL> findByUserRoleId(Long userRoleId) {
        return userRoleUrlACLRepository.findByUserRoleId(userRoleId);
    }
}
