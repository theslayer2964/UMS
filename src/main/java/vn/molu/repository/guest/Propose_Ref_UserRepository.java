package vn.molu.repository.guest;

import org.springframework.stereotype.Repository;
import vn.molu.domain.guest.Propose_Ref_User;
import vn.molu.repository.common.MyRepository;

@Repository
public interface Propose_Ref_UserRepository extends MyRepository<Propose_Ref_User, Long> {
}
