package vn.molu.service.admin;

import vn.molu.domain.admin.GroupUserPermission;

import java.util.List;

public interface GroupUserService {
    List<GroupUserPermission> findAll();
    List<GroupUserPermission> findGroupUserByRoleNProgram(Integer role, String programId);
}
