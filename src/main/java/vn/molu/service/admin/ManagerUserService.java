package vn.molu.service.admin;

import vn.molu.domain.admin.ManagerUser;

import java.util.List;

public interface ManagerUserService {
    List<ManagerUser> findByShopCode(String deaprtId);
    ManagerUser add(ManagerUser user);
    ManagerUser update(ManagerUser user);
    ManagerUser findUserById(Long managerUserId);
    void deleteById(Long managerUserId);
    List<ManagerUser> findByProgramNPosition(String program, String position);
}
