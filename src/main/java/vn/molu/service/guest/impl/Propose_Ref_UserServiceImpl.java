package vn.molu.service.guest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.guest.Propose_Ref_User;
import vn.molu.repository.guest.Propose_Ref_UserRepository;
import vn.molu.service.guest.Propose_Ref_UserService;

import java.util.List;

@Service
public class Propose_Ref_UserServiceImpl implements Propose_Ref_UserService {
    @Autowired
    private Propose_Ref_UserRepository proposeRefUserRepository;

    @Override
    public List<Propose_Ref_User> getUsersByProposeId(Long id) {
        return null;
    }

    @Override
    public Propose_Ref_User save(Propose_Ref_User user) {
        return proposeRefUserRepository.save(user);
    }
}
