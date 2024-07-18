package vn.molu.domain.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "GROUP_USER_PERMISSION")
@Entity
public class GroupUserPermission implements Serializable {

    @Id
    @Column(name = "GROUP_USER_ID")
    private String groupUserId;
    @Column(name = "USER_ROLE")
    private Integer userRole;
    @Column(name = "PERMISSION")
    private String permission;
    @Column(name = "PROGRAM_ID")
    private Integer programId;
    @Column(name = "DESCRIPTION")
    private String description;

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(String groupUserId) {
        this.groupUserId = groupUserId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GroupUserPermission{" +
                "groupUserId='" + groupUserId + '\'' +
                ", userRole=" + userRole +
                ", permission='" + permission + '\'' +
                ", programId=" + programId +
                ", description='" + description + '\'' +
                '}';
    }
}
