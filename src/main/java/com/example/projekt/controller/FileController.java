package com.example.projekt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.projekt.model.File;
import com.example.projekt.repository.FileRepository;

@RestController
@RequestMapping("/file")
public class FileController {
    public static String uploadDirectory = System.getProperty("user.dir") + "\\uploads";
    private FileRepository fileRepository;
    public static final List<String> allowedTypes = Arrays.asList(
            "image/png", //
            "image/jpeg", //
            "image/jpg", //
            "image/gif", //
            //"image/tiff",
            "image/svg+xml");

    /**
     * Constructor
     * @param fileRepository
     */
    public FileController(FileRepository fileRepository)
    {
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

    /**
     * Das soll dazu dienen, per ID eine File als JSON zu erhalten.
     * Erhalten bedeutet für den USER ein im Browser anzeigbaren filepath zu erhalten.
     * Normalerweise sollten Files nicht so im UI angebunden werden, aber vlt. ist es irgendwann hilfreich.
     * Best Case wäre wenn die Files in andere Klassen eingebunden werden, sowie User implements Image Extends File... 
     * Es macht wenig sinn dem User die ID für eine File zu schicken, anstelle davon ihm direkt den Filepath zu schicken.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public @ResponseBody File index(@PathVariable("id") Integer id) {
         File file = fileRepository.findById(id).orElse(new File());
         file.setFilename(uploadDirectory + file.getFileName());    // Braucht keine extra Klasse (evtl noch adaptieren, so liefert es den absoluten Pfad)
         return file;
        //return File.loadFromDB(id);
    }

  
    /**
     * Hier kann man mehrere Files, die mit dem Namen "files" empfangen werden, "hochladen".
     * @return
     * json in der Form
     * {
     * {"file" : "filename.type", "action" : "created/overwritten"},
     * {"file" : "filename2.type", "error" : "Type of Error"}
     * }
     */
    @RequestMapping(value = "/upload", produces = "raw/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String upload(@RequestParam("files") MultipartFile[] files)
    {
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
