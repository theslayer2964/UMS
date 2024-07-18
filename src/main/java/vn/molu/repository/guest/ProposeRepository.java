package vn.molu.repository.guest;

import org.springframework.stereotype.Repository;
import vn.molu.domain.guest.Propose;
import vn.molu.repository.common.MyRepository;

@Repository
public interface ProposeRepository extends MyRepository<Propose, Long> {
}
