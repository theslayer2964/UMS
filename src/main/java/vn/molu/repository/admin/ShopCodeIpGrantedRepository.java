package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.molu.domain.admin.ShopCodeIPGranted;

import javax.transaction.Transactional;

public interface ShopCodeIpGrantedRepository extends JpaRepository<ShopCodeIPGranted, String> {

    @Query(value = "select * from SHOPCODE_IP_GRANTED where shop_code = :shopCode", nativeQuery = true)
    ShopCodeIPGranted findByShopCode(String shopCode);


    @Modifying
    @Transactional
    @Query(value = "update SHOPCODE_IP_GRANTED set ip_granted=:ip_granted, SHOP_TYPE=:SHOP_TYPE where shop_code = :shopCode", nativeQuery = true)
    int updateIP(String ip_granted, int SHOP_TYPE, String shopCode);
}
