package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.ShopDTO;

import java.util.List;

public interface ShopDAO {

    List<ShopDTO> findActiveShopCenCode2(String cen_code ,String status);
    List<ShopDTO> findShopDepartment(String cen_code ,String status);
    ShopDTO findById(String shopCode);
    Integer updateShop(String name, String address, String shop_code);
}
