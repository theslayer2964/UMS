package vn.molu.dto.admin.admin;

public class UserLoginDTO {
    private Long user_id;
    private String user_name;
    private String full_name;
    private String shop_code;
    private String create_date;
    private String emp_code;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", shop_code='" + shop_code + '\'' +
                ", create_date='" + create_date + '\'' +
                ", emp_code='" + emp_code + '\'' +
                '}';
    }
}
