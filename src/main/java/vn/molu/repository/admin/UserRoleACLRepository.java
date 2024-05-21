package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.molu.domain.admin.UserRoleACL;
import vn.molu.repository.common.MyRepository;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Repository
public interface UserRoleACLRepository extends MyRepository<UserRoleACL, Long> {

    @Query(value = "select ur from UserRoleACL ur join ur.user u where u.userId =:userId ")
    List<UserRoleACL> findByUserId(@Param("userId") Long UserId);
}
