package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.molu.domain.admin.ManagerUser;

import java.util.List;

public interface ManagerUserRepository extends JpaRepository<ManagerUser, Long> {
    @Query(value = "select * from MANAGER_USER where SHOP_CODE =:shop_code ", nativeQuery = true)
    List<ManagerUser> findByShopCode(String shop_code);
    @Query(value = "select * from MANAGER_USER where program = :program and position =:position", nativeQuery = true)
    List<ManagerUser> findManagerUserByProgramNPosition(String program, String position);
}
