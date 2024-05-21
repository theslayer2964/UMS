package vn.molu.webapp.command.admin;

import org.springframework.web.multipart.MultipartFile;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.webapp.command.AbstractCommand;

public class AddUserCommand extends AbstractCommand<C2AdminUserAuto> {
    public AddUserCommand() {
        this.pojo = new C2AdminUserAuto();
    }

    private MultipartFile file;
    private String uploadDate;
    private String form_type;
    private String email;
    private String branch;
    private String chucdanh;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getChucdanh() {
        return chucdanh;
    }

    public void setChucdanh(String chucdanh) {
        this.chucdanh = chucdanh;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }
}
