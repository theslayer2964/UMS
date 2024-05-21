package vn.molu.service.admin;

import vn.molu.domain.admin.ShopCodeIPGranted;

public interface ShopCodeIpGrantedService {
    ShopCodeIPGranted findByShopcode(String shopcode);
    ShopCodeIPGranted update(ShopCodeIPGranted ipGranted);
    void updateGrantedIp(ShopCodeIPGranted shopGrantedIp);
    String getIP_Granted_ByShopCode(String shop_code);
}
