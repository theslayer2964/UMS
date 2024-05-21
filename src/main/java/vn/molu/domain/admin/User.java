package vn.molu.domain.admin;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
@Table(name = "AD_USER", uniqueConstraints={@UniqueConstraint(columnNames="user_name"),@UniqueConstraint(columnNames="Email")})
@Entity
public class User implements Serializable {
    private Long userId;

    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_USER_SEQ")
    @SequenceGenerator(name = "AD_USER_SEQ", sequenceName = "AD_USER_SEQ", allocationSize = 1)
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String userName;

    @Basic
    @Column(name = "user_name", length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String email;

    @Basic
    @Column(name = "email", length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String password;

    @Basic
    @Column(name = "password", length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String displayName;

    @Basic
    @Column(name = "display_name", length = 50)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    private String flgDelete;

    @Basic
    @Column(name = "flg_delete", length = 1)
    public String getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(String flgDelete) {
        this.flgDelete = flgDelete;
    }

    
    private Timestamp createdDate;

    @Basic
    @Column(name = "created_datetime")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    private Timestamp modifiedDate;

    @Basic
    @Column(name = "modified_datetime")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    private UserGroup userGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_group_id", referencedColumnName = "user_group_id", nullable = true)
    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
    
	private Integer accountType;

	@Column(name="account_type", columnDefinition="int default 1")
	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
