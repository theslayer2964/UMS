package vn.molu.dto.admin.admin;

public class HrNhanVienAllDataDTO {

    private String firstname;
    private String lastname;
    private String middle_name;
    private Integer active;
    private String mobile;
    private String email;
    private String msChucVu;
    private String tenChucDanh;
    private String msPhongBan;
    private String msTinh;
    private String tenPhongBan;
    private String maPhongBan;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsChucVu() {
        return msChucVu;
    }

    public void setMsChucVu(String msChucVu) {
        this.msChucVu = msChucVu;
    }

    public String getTenChucDanh() {
        return tenChucDanh;
    }

    public void setTenChucDanh(String tenChucDanh) {
        this.tenChucDanh = tenChucDanh;
    }

    public String getMsPhongBan() {
        return msPhongBan;
    }

    public void setMsPhongBan(String msPhongBan) {
        this.msPhongBan = msPhongBan;
    }

    public String getMsTinh() {
        return msTinh;
    }

    public void setMsTinh(String msTinh) {
        this.msTinh = msTinh;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    public String getMaPhongBan() {
        return maPhongBan;
    }

    public void setMaPhongBan(String maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", active=" + active +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", msChucVu='" + msChucVu + '\'' +
                ", tenChucDanh='" + tenChucDanh + '\'' +
                ", msPhongBan='" + msPhongBan + '\'' +
                ", msTinh='" + msTinh + '\'' +
                ", tenPhongBan='" + tenPhongBan + '\'' +
                ", maPhongBan='" + maPhongBan + '\'' +
                '}';
    }
}
