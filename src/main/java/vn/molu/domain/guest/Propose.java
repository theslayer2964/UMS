package vn.molu.domain.guest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import vn.molu.domain.IdEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AttributeOverride(name = "id", column = @Column(name = "propose_id"))
@Table(name = "PROPOSE")
public class Propose extends IdEntity {

    @Basic
    @Column(name = "created_datetime")
    private Timestamp createdDate;

    @Basic
    @Column(name = "modified_datetime")
    private Timestamp modifiedDate;

    @Basic
    @Column(name = "proposer_name")
    private String proposer_name;

    @Basic
    @Column(name = "status")
    private Integer status;

    @Basic
    @Column(name = "reviewer")
    private String reviewer;

    @Basic
    @Column(name = "message", length = 2000)
    private String message;

    @Lob
    @Column(name = "propose_file")
    private byte[] proposeFile;

    @JsonIgnore
    @OneToMany(mappedBy = "propose")
    private Set<Propose_Ref_User> users = new HashSet<>();

    public void addUser(Propose_Ref_User user){
        users.add(user);
        user.setPropose(this);
    }

    public void remote(Propose_Ref_User user){
        users.remove(user);
        user.setPropose(null);
    }
}
