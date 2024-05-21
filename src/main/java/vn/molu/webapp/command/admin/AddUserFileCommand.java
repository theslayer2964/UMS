package vn.molu.webapp.command.admin;

import org.springframework.web.multipart.MultipartFile;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.webapp.command.AbstractCommand;

import java.util.List;

public class AddUserFileCommand extends AbstractCommand<C2AdminUserAuto> {
    public AddUserFileCommand() {
        this.pojo = new C2AdminUserAuto();
    }

    private MultipartFile file;
    private String fileType;
    private String listUserAuto;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getListUserAuto() {
        return listUserAuto;
    }

    public void setListUserAuto(String listUserAuto) {
        this.listUserAuto = listUserAuto;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
