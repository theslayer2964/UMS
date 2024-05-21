package vn.molu.domain.admin;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="Manager_User")
//@Table(name = "Manager_User", uniqueConstraints={@UniqueConstraint(columnNames="user_name"),@UniqueConstraint(columnNames="Email")})
@Entity
public class ManagerUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_MANAGER_USER_SEQ")
    @SequenceGenerator(name = "AD_MANAGER_USER_SEQ", sequenceName = "AD_MANAGER_USER_SEQ", allocationSize = 1)
    @Column(name = "manager_user_id")
    private Long managerUserId;

    @Column(name = "position")
    private String position;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String username;

    @Column(name = "shop_code")
    private String shop_code;

    public Long getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(Long managerUserId) {
        this.managerUserId = managerUserId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    @Override
    public String toString() {
        return "ManagerUser{" +
                "managerUserId=" + managerUserId +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", shop_code='" + shop_code + '\'' +
                '}';
    }
}
