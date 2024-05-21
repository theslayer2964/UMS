package vn.molu.webapp.command.admin;

import org.springframework.web.multipart.MultipartFile;
import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.webapp.command.AbstractCommand;

public class C2AdminUserCommand extends AbstractCommand<C2UserAdminDTO> {

    public C2AdminUserCommand() {
        this.pojo = new C2UserAdminDTO();
    }
    private String displayName;
    private String status;
    private String shop_code;
    private String program;
    private String shopcode_old;
    private MultipartFile file;
    private String uploadDate;
    private String email;
    public String getUploadDate() {
        return uploadDate;
    }
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
    public String getShopcode_old() {
        return shopcode_old;
    }
    public void setShopcode_old(String shopcode_old) {
        this.shopcode_old = shopcode_old;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
