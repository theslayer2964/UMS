package vn.molu.service.admin;

import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UrlGroup;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UrlService {
    Url findByCode(String code);
    Url findByPath(String path);
    List<Url> findAllByUserRoleId(Long userRoleId, Long urlGroupId);
    void delete(Long urlId);

    List<Url> findAll();

    Url save(Url pojo);

    Url findById(Long urlGroupId);

    Url update(Url pojo);

    List<Url> findAllNotAssignUserRoleId(Long userRoleId);
}
