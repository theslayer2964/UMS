package vn.molu.service.guest;

import vn.molu.domain.guest.Propose;

import java.util.List;

public interface ProposeService {
    List<Propose> getListPropose();
    Propose getProposeById(Long proposeId);

    Propose save(Propose propose);
}
