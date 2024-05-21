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
@Table(name="AD_USER_GROUP_ROLE_ACL")
public class UserGroupRoleACL implements Serializable{

    private Long userGroupRoleACLId;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_USER_GROUP_ROLE_ACL_SEQ")
    @SequenceGenerator(name = "AD_USER_GROUP_ROLE_ACL_SEQ", sequenceName = "AD_USER_GROUP_ROLE_ACL_SEQ", allocationSize = 1)
    @Column(name="user_group_role_acl_id")
    public Long getUserGroupRoleACLId() {
        return userGroupRoleACLId;
    }

    public void setUserGroupRoleACLId(Long userGroupRoleACLId) {
        this.userGroupRoleACLId = userGroupRoleACLId;
    }
    
    private UserGroup userGroup;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_group_id", referencedColumnName="user_group_id", nullable=false, updatable = true)
    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
    
    private UserRole userRole;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_role_id", referencedColumnName="user_role_id", nullable=false, updatable = true)
    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    
    
}
