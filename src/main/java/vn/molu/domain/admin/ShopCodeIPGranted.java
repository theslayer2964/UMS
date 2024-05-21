package vn.molu.domain.admin;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "SHOPCODE_IP_GRANTED")
@Entity
public class ShopCodeIPGranted implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_USER_SEQ")
    @SequenceGenerator(name = "AD_USER_SEQ", sequenceName = "AD_USER_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Column(name = "shop_code")
    private String shopCode;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "ip_granted")
    private String ipGranted;
    @Column(name = "SHOP_TYPE")
    private int shopType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getIpGranted() {
        return ipGranted;
    }

    public void setIpGranted(String ipGranted) {
        this.ipGranted = ipGranted;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "ShopCodeIPGranted{" +
                "id='" + id + '\'' +
                ", shopCode='" + shopCode + '\'' +
                ", shopName='" + shopName + '\'' +
                ", ipGranted='" + ipGranted + '\'' +
                ", shopType=" + shopType +
                '}';
    }
}
