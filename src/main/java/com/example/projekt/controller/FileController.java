package com.example.projekt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.projekt.model.File;
import com.example.projekt.repository.FileRepository;

@RestController
@RequestMapping("/file")
public class FileController {
    public static String uploadDirectory = System.getProperty("user.dir") + "\\uploads"; //"C:\\Users\\flori\\#fh\\5sem\\Bweb\\Projekt\\uploads\\" ;
    private FileRepository fileRepository;

    /**
     * Constructor
     * @param fileRepository
     */
    public FileController(FileRepository fileRepository)
    {
        this.fileRepository = fileRepository;
        try {
            Files.createDirectories(Path.of(uploadDirectory).normalize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Das soll dazu dienen, per ID eine File als JSON zu erhalten.
     * Erhalten bedeutet für den USER ein im Browser anzeigbaren filepath zu erhalten.
     * Normalerweiße sollten Files nicht so im UI angebunden werden, aber vlt. ist es irgendwann hilfreich.
     * Best Case wäre wenn die Files in andere Klassen eingebunden werden, sowie User implements Image Extends File... 
     * Es macht wenig sinn dem User die ID für eine File zu schicken, anstelle davon ihm direkt den Filepath zu schicken.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public @ResponseBody File index(@PathVariable("id") Integer id) {
        return File.loadFromDB(id);
    }

  
    /**
     * Hier kann man mehrere Files, die mit dem Namen "files" empfangen werden, "hochladen".
     * @return
     */
    @RequestMapping("/upload")
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
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append( "{\"file\": \"" + file.getOriginalFilename() + "\",\n");

            try
            {
                Boolean ex = Files.exists(fileNameAndPath);

                Files.write(fileNameAndPath, file.getBytes());
                if(ex){
                    fileNames.append("\"action\" : \"overwritten\"");
                }else {
                    fileNames.append("\"action\" : \"created\"");
                }
            }
            catch(IOException e) 
            {
                e.printStackTrace();
            }
            fileNames.append("}");
        }
        fileNames.append("}");
        // TODO - respond with JSON
        return fileNames.toString() ;
    }

}
