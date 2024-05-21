package vn.molu.webapp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.molu.common.Constants;
import vn.molu.domain.admin.ExplanationForm;
import vn.molu.service.admin.ExplanationFormService;

import java.util.List;

@Controller
public class FileController {
    @Autowired
    private ExplanationFormService explanationFormService;

    @RequestMapping(value = "/file/preview.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<byte[]> previewFile(@RequestParam("id") Long id) {
        ExplanationForm file = explanationFormService.findById(id);
        HttpHeaders headers = new HttpHeaders();
        if (file != null && file.getFileData() != null) {
            if (file.getFile_type().equals(Constants.FILE_TYPE_PDF)) {
                headers.setContentType(MediaType.parseMediaType(Constants.FILE_TYPE_PDF));
                headers.add("content-disposition", "inline;filename=" + file.getFile_name());
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
                return response;
            } else if (file.getFile_type().equals(Constants.FILE_TYPE_IMG)) {
                headers.setContentType(MediaType.parseMediaType(Constants.FILE_TYPE_IMG));
                headers.add("content-disposition", "inline;filename=" + file.getFile_name());
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
                return response;
            } else {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFile_name());
                ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
                return response;
            }
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/file/download.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("id") Long id) {
        ExplanationForm file = explanationFormService.findById(id);
        if (file != null && file.getFileData() != null) {
            ByteArrayResource resource = new ByteArrayResource(file.getFileData());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFile_name());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }


    @RequestMapping(value = "/ajax/getHistoryByUsername.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody List<ExplanationForm> getHistory(@RequestParam("username") String username) {
        try {
            return explanationFormService.findExplanationFormByUser_name(username);
        } catch (Exception e) {
            return null;
        }
    }
}
