package vn.molu.service.admin;

import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UserGroup;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UserGroupService {

    UserGroup findByCode(String code);
    void delete(Long urlId);

    List<UserGroup> findAll();

    UserGroup save(UserGroup pojo);

    UserGroup findById(Long userGroupId);

    UserGroup update(UserGroup pojo);
}
