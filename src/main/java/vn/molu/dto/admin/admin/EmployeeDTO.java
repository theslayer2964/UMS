package vn.molu.dto.admin.admin;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {
    private String emp_code;
    private String emp_name;
    private String shop_code;
    private Integer emp_type;
    private Integer status;
    private String user_name;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public Integer getEmp_type() {
        return emp_type;
    }

    public void setEmp_type(Integer emp_type) {
        this.emp_type = emp_type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "emp_code='" + emp_code + '\'' +
                ", emp_name='" + emp_name + '\'' +
                ", shop_code='" + shop_code + '\'' +
                ", emp_type=" + emp_type +
                ", status=" + status +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
