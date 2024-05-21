package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.molu.common.Constants;
import vn.molu.domain.admin.ExplanationForm;
import vn.molu.repository.admin.ExplanationFormRepository;
import vn.molu.service.admin.ExplanationFormService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ExplanationFormSerivceImpl implements ExplanationFormService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private ExplanationFormRepository repository;
    @Override
    public ExplanationForm uploadFile(MultipartFile file, Date upload_date, String user_name, String form_type) throws IOException {
            log.info("upload file ");
            ExplanationForm form = new ExplanationForm();
            form.setFile_name(file.getOriginalFilename());
            form.setUser_name(user_name);
            form.setUpdate_date(upload_date);
            form.setFile_type(file.getContentType());
            form.setFile_path(null);
            form.setFileData(file.getBytes());
            form.setForm_type(form_type);
            form.setStatus(Constants.EXPLANATION_FORM_STATUS_SUBMIT);
            return repository.save(form);
        }

    @Override
    public ExplanationForm findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ExplanationForm> findExplanationFormByUser_name(String user_name) {
        return repository.findExplanationFormByUser_name(user_name);
    }

    @Override
    public List<String> findExplanationFormByStatus(String form_type,Integer status) {
        return repository.findExplanationFormByStatus(form_type, status);
    }

    @Override
    public List<ExplanationForm> findExplanationFormByUser_nameNFormtype(String username, Integer status) {
        return repository.findExplanationFormByUser_nameNFormtype(username, status);
    }

    @Override
    public int updateStatusFormById(Long id, Integer status) {
        return repository.updateFormStatusByid(id, status);
    }

}
