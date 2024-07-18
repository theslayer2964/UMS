package vn.molu.domain.guest;

import lombok.Data;
import vn.molu.domain.IdEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@AttributeOverride(name = "id", column = @Column(name = "propose_ref_user_id"))
@Table(name = "PROPOSE_REF_USER")
public class Propose_Ref_User extends IdEntity {

    @Basic
    @Column(name = "user_name")
    private String username;

    @Basic
    @Column(name = "shop_code")
    private String shopCode;

    @Basic
    @Column(name = "type_propose")
    private Integer typePropose;

    @Basic
    @Column(name = "action_time")
    private Timestamp actionTime;

    @Basic
    @Column(name = "status")
    private Integer status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "propose_id", referencedColumnName = "propose_id")
    private Propose propose;
}
