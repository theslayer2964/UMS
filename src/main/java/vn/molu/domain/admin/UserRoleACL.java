package vn.molu.domain.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="AD_USER_ROLE_ACL")
public class UserRoleACL implements Serializable{

    private Long userRoleACLsId;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_USER_ROLE_ACL_SEQ")
    @SequenceGenerator(name = "AD_USER_ROLE_ACL_SEQ", sequenceName = "AD_USER_ROLE_ACL_SEQ", allocationSize = 1)
    @Column(name="user_role_acl_id")
    public Long getUserRoleACLsId() {
        return userRoleACLsId;
    }

    public void setUserRoleACLsId(Long userRoleACLsId) {
        this.userRoleACLsId = userRoleACLsId;
    }
    
    private User user;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName="user_id", nullable=false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    private UserRole userRole;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_role_id", referencedColumnName="user_role_id", nullable=false)
    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    
}
