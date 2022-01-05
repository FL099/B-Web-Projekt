package com.example.projekt.interfaces;

import com.example.projekt.data.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {

    File getFile(int id);
    String upload( MultipartFile[] files, List<String> allowedTypes);


}
