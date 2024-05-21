package vn.molu.domain.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "AD_USER_ROLE", uniqueConstraints = { @UniqueConstraint(columnNames = "code") })
public class UserRole implements Serializable {

    private Long userRoleId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_USER_ROLE_SEQ")
    @SequenceGenerator(name = "AD_USER_ROLE_SEQ", sequenceName = "AD_USER_ROLE_SEQ", allocationSize = 1)
    @Column(name = "user_role_id")
    public Long getUserRoleId() {
	return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
	this.userRoleId = userRoleId;
    }

    private String code;
    
    @Basic
    @Column(name="code", length=30)
    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }
    
    private String name;

    @Basic
    @Column(name="name", length=100)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    private String description;
    
    @Basic
    @Column(name="description", length=500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    private List<UserRoleACL> userRoleACLS;
    @OneToMany(mappedBy = "userRole")
    public List<UserRoleACL> getUserRoleACLS() {
        return userRoleACLS;
    }

    public void setUserRoleACLS(List<UserRoleACL> userRoleACLS) {
        this.userRoleACLS = userRoleACLS;
    }

    private List<UserRoleUrlACL> userRoleUrlACLS;
    @OneToMany(mappedBy = "userRole")
    public List<UserRoleUrlACL> getUserRoleUrlACLS() {
        return userRoleUrlACLS;
    }

    public void setUserRoleUrlACLS(List<UserRoleUrlACL> userRoleUrlACLS) {
        this.userRoleUrlACLS = userRoleUrlACLS;
    }
}
