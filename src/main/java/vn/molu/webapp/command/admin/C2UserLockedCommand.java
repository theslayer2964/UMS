package vn.molu.webapp.command.admin;

import org.springframework.web.multipart.MultipartFile;
import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.webapp.command.AbstractCommand;

public class C2UserLockedCommand extends AbstractCommand<C2UserAdminDTO> {
    public C2UserLockedCommand() {
        this.pojo = new C2UserAdminDTO();
    }
    private String uploadDate;
    private MultipartFile file;
    private String username;
    private String granted_ip;
    private String form_type;
    public String getGranted_ip() {
        return granted_ip;
    }

    public void setGranted_ip(String granted_ip) {
        this.granted_ip = granted_ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }
}
