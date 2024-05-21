package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.UserGroup;
import vn.molu.repository.admin.UserGroupRepository;
import vn.molu.service.admin.UserGroupService;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    public UserGroup findByCode(String code) {
        log.info("Find with code: " + code);

        try {
            UserGroup userGroup = userGroupRepository.findByCode(code);
            return userGroup;
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public void delete(Long userGroupId) {
        log.info("Delete with id: " + userGroupId);

        try {
            userGroupRepository.deleteById(userGroupId);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<UserGroup> findAll() {
        log.info("Find all");

        try {
            return userGroupRepository.findAll();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserGroup save(UserGroup pojo) {
        log.info("save a UserGroup");

        try {
            return userGroupRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserGroup findById(Long userGroupId) {
        log.info("save a UserGroup");

        try {
            return userGroupRepository.findById(userGroupId).get();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserGroup update(UserGroup pojo) {
        log.info("update a UserGroup");

        try {
            return userGroupRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }
}
