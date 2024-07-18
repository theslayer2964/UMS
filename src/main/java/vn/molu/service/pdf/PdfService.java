package vn.molu.service.pdf;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PdfService {
    public String readPdf(MultipartFile file) throws IOException;
}
