package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.molu.domain.admin.GroupUserPermission;

import java.util.List;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUserPermission, String> {
    @Query(value = "select * from group_user_permission where user_role = :role and program_id = :programId", nativeQuery = true)
    List<GroupUserPermission> findGroupUserByRoleNProgram(Integer role, String programId);
}
