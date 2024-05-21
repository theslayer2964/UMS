package vn.molu.webapp.command.admin;

import org.springframework.web.multipart.MultipartFile;
import vn.molu.webapp.command.AbstractCommand;

public class ManagerUserByFileCommand extends AbstractCommand<String> {
    public ManagerUserByFileCommand() {
        this.pojo = new String();
    }
    private MultipartFile file;
    private String listRetire;
    public String getListRetire() {
        return listRetire;
    }
    public void setListRetire(String listRetire) {
        this.listRetire = listRetire;
    }
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
