package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.molu.domain.admin.UserRole;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByCode(String code);
    @Query(value = "select model from UserRole model where not exists (select 1 from UserGroupRoleACL where userRole.userRoleId = model.userRoleId and userGroup.userGroupId  = :userGroupId)")
    List<UserRole> findAllByUserGroupId(@Param("userGroupId") Long userGroupId);
    @Query( value = "select model from UserRole model where not exists (select 1 from UserRoleACL where userRole.userRoleId = model.userRoleId and user.userId  = :userId) and not exists (select 1 from UserGroupRoleACL where userRole.userRoleId = model.userRoleId and userGroup.userGroupId = :userGroupId)")
    List<UserRole> findAllByUserIdAndUserGroupId(@Param("userId") Long userId,@Param("userGroupId") Long userGroupId);
}
