package az.nicat.shoppingapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageFileService {
    String add(MultipartFile multipartFile);
}
