package vn.molu.domain.admin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "EXPLANATION_FORM")
public class ExplanationForm implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPAINATION_FORM_SEQ")
    @SequenceGenerator(name = "EXPAINATION_FORM_SEQ", sequenceName = "EXPAINATION_FORM_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "update_date")
    private Date update_date;
    @Column(name = "file_name")
    private String file_name;
    @Column(name = "file_type")
    private String file_type;
    @Column(name = "file_path")
    private String file_path;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "form_type")
    private String form_type;
    @Column(name = "status")
    private Integer status;
    @Lob
    @Column(name = "form_data")
    private byte[] fileData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
