package vn.molu.dto.admin.admin;

import java.io.Serializable;

public class ShopDTO implements Serializable {
    private String shop_code;
    private String center_code;
    private String status;
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public String getCenter_code() {
        return center_code;
    }

    public void setCenter_code(String center_code) {
        this.center_code = center_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShopDTO{" +
                "shop_code='" + shop_code + '\'' +
                ", center_code='" + center_code + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
