package vn.molu.service.admin;

import org.springframework.web.multipart.MultipartFile;
import vn.molu.domain.admin.ExplanationForm;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ExplanationFormService {
    ExplanationForm uploadFile(MultipartFile file, Date upload_date, String user_name, String form_type) throws IOException;
    ExplanationForm findById(Long id);
    List<ExplanationForm> findExplanationFormByUser_name(String user_name);
    List<String> findExplanationFormByStatus(String form_type, Integer status);
    List<ExplanationForm> findExplanationFormByUser_nameNFormtype(String username, Integer explanationFormUnlock);
    int updateStatusFormById(Long id, Integer status);
}
