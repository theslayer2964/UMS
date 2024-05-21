package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.GroupUser;
import vn.molu.repository.admin.GroupUserRepository;
import vn.molu.service.admin.GroupUserService;

import java.util.List;

@Service
public class GroupUserServiceImpl implements GroupUserService {
    @Autowired
    private GroupUserRepository groupUserRepository;

    @Override
    public List<GroupUser> findAll() {
        return groupUserRepository.findAll();
    }

    @Override
    public List<GroupUser> findGroupUserByRoleNProgram(Integer role, String programId) {
        return groupUserRepository.findGroupUserByRoleNProgram(role, programId);
    }
}
