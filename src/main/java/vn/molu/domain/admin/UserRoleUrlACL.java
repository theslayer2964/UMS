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
@Table(name="AD_USER_ROLE_URL_ACL")
public class UserRoleUrlACL implements Serializable {

    private Long userRoleUrlACLId;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_USER_ROLE_URL_ACL_SEQ")
    @SequenceGenerator(name = "AD_USER_ROLE_URL_ACL_SEQ", sequenceName = "AD_USER_ROLE_URL_ACL_SEQ", allocationSize = 1)
    @Column(name="user_role_url_acl_id")
    public Long getUserRoleUrlACLId() {
        return userRoleUrlACLId;
    }

    public void setUserRoleUrlACLId(Long userRoleUrlACLId) {
        this.userRoleUrlACLId = userRoleUrlACLId;
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
    
    private Url url;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="url_id", referencedColumnName="url_id", nullable=false)
    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
    
    
}
