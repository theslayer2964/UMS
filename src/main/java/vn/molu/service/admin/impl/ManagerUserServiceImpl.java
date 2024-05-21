package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.ManagerUser;
import vn.molu.repository.admin.ManagerUserRepository;
import vn.molu.service.admin.ManagerUserService;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ManagerUserServiceImpl implements ManagerUserService {

    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private ManagerUserRepository managerUserRepository;

    @Override
    public List<ManagerUser> findByShopCode(String deaprtId) {
        return managerUserRepository.findByShopCode(deaprtId);
    }

    @Override
    public ManagerUser add(ManagerUser user) {
        return managerUserRepository.save(user);
    }

    @Override
    public ManagerUser update(ManagerUser user) {
        return null;
    }

    @Override
    public ManagerUser findUserById(Long managerUserId) {
        log.info("Find with id: " + managerUserId);
        try {
            return managerUserRepository.findById(managerUserId).get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteById(Long managerUserId) {
        log.info("Delete manager user id: " + managerUserId);
        try {
            managerUserRepository.deleteById(managerUserId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ManagerUser> findByProgramNPosition(String program, String position) {
        return managerUserRepository.findManagerUserByProgramNPosition(program, position);
    }
}
