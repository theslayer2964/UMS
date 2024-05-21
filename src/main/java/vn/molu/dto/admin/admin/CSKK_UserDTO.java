package vn.molu.dto.admin.admin;

public class CSKK_UserDTO {
    private String username;
    private String email;
    private String fullName;
    private String shopCode;
    private String position;
    private String branch;
    private String phone;
    private String shopName;
    private String ghi_chu;
    private Integer status;
    private String error_explain;
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError_explain() {
        return error_explain;
    }

    public void setError_explain(String error_explain) {
        this.error_explain = error_explain;
    }

    @Override
    public String toString() {
        return "CSKK_UserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", shopCode='" + shopCode + '\'' +
                ", position='" + position + '\'' +
                ", branch='" + branch + '\'' +
                ", phone='" + phone + '\'' +
                ", shopName='" + shopName + '\'' +
                ", ghi_chu='" + ghi_chu + '\'' +
                ", status='" + status + '\'' +
                ", error_explain='" + error_explain + '\'' +
                '}';
    }
}
