package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.ShopCodeIPGranted;
import vn.molu.repository.admin.ShopCodeIpGrantedRepository;
import vn.molu.service.admin.ShopCodeIpGrantedService;

@Service
public class ShopCodeIpGrantedServiceImpl implements ShopCodeIpGrantedService {

    @Autowired
    private ShopCodeIpGrantedRepository shopCodeIpGrantedRepository;

    @Override
    public ShopCodeIPGranted findByShopcode(String shopcode) {
        return shopCodeIpGrantedRepository.findByShopCode(shopcode);
    }

    @Override
    public ShopCodeIPGranted update(ShopCodeIPGranted ipGranted) {
        return shopCodeIpGrantedRepository.save(ipGranted);
    }

    @Override
    public void updateGrantedIp(ShopCodeIPGranted shopGrantedIp) {
        shopCodeIpGrantedRepository.updateIP(shopGrantedIp.getIpGranted(), shopGrantedIp.getShopType(), shopGrantedIp.getShopCode());
    }

    @Override
    public String getIP_Granted_ByShopCode(String shop_code) {
        ShopCodeIPGranted shopCodeIPGranted = shopCodeIpGrantedRepository.findByShopCode(shop_code);
        ShopCodeIPGranted mSaleIp = shopCodeIpGrantedRepository.findByShopCode("mSale");
        if (shopCodeIPGranted != null) {
            if (shopCodeIPGranted.getShopType() == 1) {
                String shopIp = shopCodeIPGranted.getIpGranted();
                String final_ip = mSaleIp.getIpGranted() + ";" + shopIp;
                shopCodeIPGranted.setIpGranted(final_ip);
            } else if (shopCodeIPGranted.getShopType() == 2) {
                shopCodeIPGranted.setIpGranted(mSaleIp.getIpGranted());
            }
            return shopCodeIPGranted.getIpGranted();
        } else
            return null;

    }
}
