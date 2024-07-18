package vn.molu.dto.admin.guest;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class ProposeDTO {
    private String proposer_name;
    private String reviewer;
    private Integer status;
    private String message;
    private MultipartFile file;
    private String sender;
    private Set<ProposeRefUserDTO> users;
}
