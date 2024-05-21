package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.molu.domain.admin.UserRoleUrlACL;
import vn.molu.repository.common.MyRepository;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
public interface UserRoleUrlACLRepository extends MyRepository<UserRoleUrlACL, Long> {

    @Query(value = "select uru from UserRoleUrlACL uru join uru.userRole ur where ur.userRoleId =:userRoleId")
    List<UserRoleUrlACL> findByUserRoleId(@Param("userRoleId") Long userRoleId);
}
