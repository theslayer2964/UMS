package vn.molu.service.guest;

import vn.molu.domain.guest.Propose_Ref_User;

import java.util.List;

public interface Propose_Ref_UserService {
    List<Propose_Ref_User> getUsersByProposeId(Long id);

    Propose_Ref_User save(Propose_Ref_User user);
}
