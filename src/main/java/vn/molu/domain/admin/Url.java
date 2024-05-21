package vn.molu.domain.admin;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
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
import javax.persistence.UniqueConstraint;

@SuppressWarnings("serial")
@Entity
@Table(name = "AD_URL", uniqueConstraints = {@UniqueConstraint(columnNames = "code"), @UniqueConstraint(columnNames = "path")})
public class Url implements Serializable {

    private Long urlId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_URL_SEQ")
    @SequenceGenerator(name = "AD_URL_SEQ", sequenceName = "AD_URL_SEQ", allocationSize = 1)
    @Column(name = "url_id")
    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    private String code;

    @Basic
    @Column(name = "code", length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    @Basic
    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String path;

    @Basic
    @Column(name = "path", length = 100)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private UrlGroup urlGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "url_group_id", referencedColumnName = "url_group_id", nullable = true)
    public UrlGroup getUrlGroup() {
        return urlGroup;
    }

    public void setUrlGroup(UrlGroup urlGroup) {
        this.urlGroup = urlGroup;
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
}
