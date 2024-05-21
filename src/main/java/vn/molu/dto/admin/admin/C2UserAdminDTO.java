package vn.molu.dto.admin.admin;

import java.io.Serializable;

public class C2UserAdminDTO implements Serializable {

    private Long userId;
    private String username;
    private String fullName;
    private String status;
    private String shopCode;
    private String description;
    private String center_code;
    private String granted_ip;
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCenter_code() {
        return center_code;
    }

    public void setCenter_code(String center_code) {
        this.center_code = center_code;
    }

    public String getGranted_ip() {
        return granted_ip;
    }

    public void setGranted_ip(String granted_ip) {
        this.granted_ip = granted_ip;
    }

    @Override
    public String toString() {
        return "C2UserAdminDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", status='" + status + '\'' +
                ", shopCode='" + shopCode + '\'' +
                ", description='" + description + '\'' +
                ", center_code='" + center_code + '\'' +
                ", granted_ip='" + granted_ip + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
