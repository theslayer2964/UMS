package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.Query;
import vn.molu.domain.admin.UserGroupRoleACL;
import vn.molu.repository.common.MyRepository;

import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-10
 */

public interface UserGroupRoleACLRepository extends MyRepository<UserGroupRoleACL, Long> {

    @Query(value = "select u from UserGroupRoleACL u join u.userGroup g where g.userGroupId =:userGroupId ")
    List<UserGroupRoleACL> findByUserGroupId(Long userGroupId);
}
