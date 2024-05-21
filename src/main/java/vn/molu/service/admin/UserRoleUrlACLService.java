package vn.molu.service.admin;

import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UserRoleUrlACL;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UserRoleUrlACLService {
    void delete(Long urlId);

    List<UserRoleUrlACL> findAll();

    UserRoleUrlACL save(UserRoleUrlACL pojo);

    UserRoleUrlACL findById(Long urlGroupId);

    UserRoleUrlACL update(UserRoleUrlACL pojo);
    List<UserRoleUrlACL> findByUserRoleId(Long userRoleId);
}
