package com.example.projekt.services;

import com.example.projekt.interfaces.IFileService;
import com.example.projekt.data.model.File;
import com.example.projekt.data.repository.IFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService implements IFileService {

    public static String uploadDirectory = System.getProperty("user.dir") + "\\uploads";
    IFileRepository fileRepository;

    public FileService(IFileRepository fileRepository){
        this.fileRepository = fileRepository;

        /**
         *WICHTIG!: Sonst kommts immer zu Errors, wenns neu gestartet wird
         */
        try {
            Files.createDirectories(Path.of(uploadDirectory).normalize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile(int id) {
        File file = fileRepository.findById(id).orElse(new File());
        file.setFilename(uploadDirectory + "\\"+ file.getFileName());    // Braucht keine extra Klasse (evtl noch adaptieren, so liefert es den absoluten Pfad)
        return file;
    }

    @Override
    public String upload(MultipartFile[] files, List<String> allowedTypes) {
        StringBuilder fileNames = new StringBuilder();
        fileNames.append("{");
        Boolean first = true;
        for (MultipartFile file : files)
        {
            if (!first){
                fileNames.append(",\n");
            }
            else {first = false;}

            // TODO: User-path, resourcen-path
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

            fileNames.append( "{\"file\": \"" + file.getOriginalFilename() + "\",\n");
            String type = file.getContentType();
            if (allowedTypes.contains(type)) {
                try {
                    Boolean ex = Files.exists(fileNameAndPath); //Reihenfolge wichtig

                    //speichert File mit neuer Id in DB
                    fileRepository.save(new File(file.getOriginalFilename(), type));

                    Files.write(fileNameAndPath, file.getBytes());
                    if (ex) {
                        fileNames.append("\"action\" : \"overwritten\"");
                    } else {
                        fileNames.append("\"action\" : \"created\"");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    fileNames.append("\"error\": \"Error while saving file\"");
                }
            }else{
                fileNames.append("\"error\": \"File format not allowed\"");
            }
            fileNames.append("}");
        }
        fileNames.append("}");
        // Liefert gültiges JSON,
        return fileNames.toString() ;
    }
}
