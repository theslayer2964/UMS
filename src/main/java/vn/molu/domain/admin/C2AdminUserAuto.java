package vn.molu.domain.admin;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "C2_ADMIN_USER_AUTO")
@Entity
public class C2AdminUserAuto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_USER_SEQ")
    @SequenceGenerator(name = "AD_USER_SEQ", sequenceName = "AD_USER_SEQ", allocationSize = 1)
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "full_name")
    private String full_name;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "password")
    private String password;
    @Column(name = "toThu")
    private String toThu;
    @Column(name = "shop_code")
    private String shop_code;
    @Column(name = "granted_ip", length = 500)
    private String granted_ip;
    @Column(name = "access_schedule")
    private String access_schedule;
    @Column(name = "program")
    private String program;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "cmnd")
    private String cmnd;
    @Column(name = "ngaycap")
    private String ngaycap;
    @Column(name = "noicap")
    private String noicap;
    @Column(name = "status")
    private int status;
    @Column(name = "type")
    private int type;
    @Column(name = "city")
    private String city;
    @Column(name = "district")
    private String district;
    @Column(name = "phone")
    private String phone;
    @Column(name = "groupUser")
    private int groupUser;
    @Column(name = "position")
    private String position;
    @Column(name = "email")
    private String email;

    public int getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(int groupUser) {
        this.groupUser = groupUser;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToThu() {
        return toThu;
    }

    public void setToThu(String toThu) {
        this.toThu = toThu;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public String getGranted_ip() {
        return granted_ip;
    }

    public void setGranted_ip(String granted_ip) {
        this.granted_ip = granted_ip;
    }

    public String getAccess_schedule() {
        return access_schedule;
    }

    public void setAccess_schedule(String access_schedule) {
        this.access_schedule = access_schedule;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getNgaycap() {
        return ngaycap;
    }

    public void setNgaycap(String ngaycap) {
        this.ngaycap = ngaycap;
    }

    public String getNoicap() {
        return noicap;
    }

    public void setNoicap(String noicap) {
        this.noicap = noicap;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "C2AdminUserAuto{" +
                "user_id=" + user_id +
                ", full_name='" + full_name + '\'' +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", toThu='" + toThu + '\'' +
                ", shop_code='" + shop_code + '\'' +
                ", granted_ip='" + granted_ip + '\'' +
                ", access_schedule='" + access_schedule + '\'' +
                ", program='" + program + '\'' +
                ", birthday='" + birthday + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", ngaycap='" + ngaycap + '\'' +
                ", noicap='" + noicap + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", phone='" + phone + '\'' +
                ", groupUser=" + groupUser +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
