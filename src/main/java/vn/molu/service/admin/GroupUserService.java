package vn.molu.service.admin;

import vn.molu.domain.admin.GroupUser;

import java.util.List;

public interface GroupUserService {
    List<GroupUser> findAll();
    List<GroupUser> findGroupUserByRoleNProgram(Integer role,String programId);
}
