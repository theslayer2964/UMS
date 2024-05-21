package vn.molu.service.admin;

import vn.molu.domain.admin.UserRole;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UserRoleService {
    UserRole findByCode(String code);

    List<UserRole> findAllByUserIdAndUserGroupId(Long userId, Long userGroupId, String queryNotExists);

    List<UserRole> findAllByUserId(Long userId, String queryNotExists);

    void delete(Long userRoleId);

    List<UserRole> findAllByUserGroupId(Long userGroupId, String queryExists);

    List<UserRole> findAll();

    UserRole save(UserRole pojo);

    UserRole findById(Long urlGroupId);

    UserRole update(UserRole pojo);
}
