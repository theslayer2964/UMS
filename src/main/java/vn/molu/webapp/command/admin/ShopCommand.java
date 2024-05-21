package vn.molu.webapp.command.admin;

import vn.molu.dto.admin.admin.ShopDTO;
import vn.molu.webapp.command.AbstractCommand;

public class ShopCommand extends AbstractCommand<ShopDTO> {
    private String granted_ip_mSale;
    private String grantedIp_shop;
    private Integer shop_type;
    public String getGranted_ip_mSale() {
        return granted_ip_mSale;
    }

    public void setGranted_ip_mSale(String granted_ip_mSale) {
        this.granted_ip_mSale = granted_ip_mSale;
    }

    public ShopCommand() {
        this.pojo = new ShopDTO();
    }

    public String getGrantedIp_shop() {
        return grantedIp_shop;
    }

    public void setGrantedIp_shop(String grantedIp_shop) {
        this.grantedIp_shop = grantedIp_shop;
    }

    public Integer getShop_type() {
        return shop_type;
    }

    public void setShop_type(Integer shop_type) {
        this.shop_type = shop_type;
    }
}
