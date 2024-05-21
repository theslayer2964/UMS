package vn.molu.service.admin;


import vn.molu.domain.admin.UserRoleACL;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UserRoleACLService {
    List<UserRoleACL> findByUserId(Long userId);
    int insert(Long userId, Long[] userRoleIds);

    int delete(Long userId, Long[] userRoleIds);

    void delete(Long userRoleACLId);

    List<UserRoleACL> findAll();

    UserRoleACL save(UserRoleACL pojo);

    UserRoleACL findById(Long userRoleACLId);

    UserRoleACL update(UserRoleACL pojo);
}
