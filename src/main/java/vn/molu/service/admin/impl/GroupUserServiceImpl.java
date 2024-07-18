package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.GroupUserPermission;
import vn.molu.repository.admin.GroupUserRepository;
import vn.molu.service.admin.GroupUserService;

import java.util.List;

@Service
public class GroupUserServiceImpl implements GroupUserService {
    @Autowired
    private GroupUserRepository groupUserRepository;

    @Override
    public List<GroupUserPermission> findAll() {
        return groupUserRepository.findAll();
    }

    @Override
    public List<GroupUserPermission> findGroupUserByRoleNProgram(Integer role, String programId) {
        return groupUserRepository.findGroupUserByRoleNProgram(role, programId);
    }
}
