package vn.molu.service.guest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.guest.Propose;
import vn.molu.repository.guest.ProposeRepository;
import vn.molu.repository.guest.Propose_Ref_UserRepository;
import vn.molu.service.guest.ProposeService;

import java.util.List;


@Service
public class ProposeServiceImpl implements ProposeService {
    @Autowired
    private Propose_Ref_UserRepository userRepository;
    @Autowired
    private ProposeRepository proposeRepository;

    @Override
    public List<Propose> getListPropose() {
        return null;
    }

    @Override
    public Propose getProposeById(Long proposeId) {
        return proposeRepository.findById(proposeId).orElseGet(() -> new Propose());
    }

    @Override
    public Propose save(Propose propose) {
        return proposeRepository.save(propose);
    }
}
