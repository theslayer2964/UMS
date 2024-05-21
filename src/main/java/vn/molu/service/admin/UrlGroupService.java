package vn.molu.service.admin;

import vn.molu.domain.admin.UrlGroup;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UrlGroupService {
    UrlGroup findByCode(String code);

    List<UrlGroup> findAllByUserRoleId(Long userRoleId);

    void delete(Long urlGroupId);

    List<UrlGroup> findAll();

    UrlGroup save(UrlGroup pojo);

    UrlGroup findById(Long urlGroupId);

    UrlGroup update(UrlGroup pojo);
}
