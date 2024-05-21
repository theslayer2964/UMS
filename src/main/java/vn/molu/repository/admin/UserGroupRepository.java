package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.molu.domain.admin.UrlGroup;
import vn.molu.domain.admin.UserGroup;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    UserGroup findByCode(String code);

}
