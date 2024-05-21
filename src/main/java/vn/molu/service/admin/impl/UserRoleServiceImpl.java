package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.dao.admin.UserRoleDAO;
import vn.molu.domain.admin.UserRole;
import vn.molu.repository.admin.UserRoleRepository;
import vn.molu.service.admin.UserRoleService;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private UserRoleDAO userRoleDAO;

    final private UserRoleRepository userRoleRepository;
    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole findByCode(String code) {
        log.info("Find with code: " + code);

        try {
            UserRole userRole = userRoleRepository.findByCode(code);
            return userRole;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<UserRole> findAllByUserIdAndUserGroupId(Long userId, Long userGroupId, String queryNotExists) {
        List<UserRole> list=userRoleRepository.findAllByUserIdAndUserGroupId(userId,userGroupId);
        return userRoleRepository.findAllByUserIdAndUserGroupId(userId,userGroupId);
    }

    @Override
    public List<UserRole> findAllByUserId(Long userId, String queryNotExists) {
        return userRoleDAO.findAllByUserId(userId, queryNotExists);
    }

    @Override
    public void delete(Long userRoleId) {
        log.info("Delete with id: " + userRoleId);

        try {
            userRoleRepository.deleteById(userRoleId);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<UserRole> findAllByUserGroupId(Long userGroupId, String queryExists) {
        return userRoleRepository.findAllByUserGroupId(userGroupId);
    }

    @Override
    public List<UserRole> findAll() {
        log.info("Find all");

        try {
            return userRoleRepository.findAll();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRole save(UserRole pojo) {
        log.info("save a url");

        try {
            return userRoleRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRole findById(Long urlGroupId) {
        log.info("save a Url");

        try {
            return userRoleRepository.findById(urlGroupId).get();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UserRole update(UserRole pojo) {
        log.info("update a url");

        try {
            return userRoleRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }
}
