package vn.molu.service.admin;

import vn.molu.domain.admin.UserGroupRoleACL;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UserGroupRoleACLService {
    void delete(Long urlId);

    List<UserGroupRoleACL> findAll();

    UserGroupRoleACL save(UserGroupRoleACL pojo);

    UserGroupRoleACL findById(Long userGroupRoleACLId);

    UserGroupRoleACL update(UserGroupRoleACL pojo);
    List<UserGroupRoleACL> findByUserGroupId(Long UserGroupId);

}
