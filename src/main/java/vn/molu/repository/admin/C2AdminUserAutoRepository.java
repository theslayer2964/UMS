package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.molu.domain.admin.C2AdminUserAuto;

@Repository
public interface C2AdminUserAutoRepository extends JpaRepository<C2AdminUserAuto, Long> {
}
