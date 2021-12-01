package com.example.projekt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.json.simple.JSONObject;    

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.projekt.model.File;
import com.example.projekt.repository.FileRepository;

@RestController
@RequestMapping("/file")
public class FileController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/uplods";
    private FileRepository fileRepository;

    /**
     * Constructor
     * @param fileRepository
     */
    public FileController(FileRepository fileRepository)
    {
        this.fileRepository = fileRepository;
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
    public String upload(@RequestParam("files") MultipartFile[] files)
    {
        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files)
        {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            try 
            {
                Files.write(fileNameAndPath, file.getBytes());
            }
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }

        // TODO - respond with JSON
        return "ich wäre gerne eine adequate antwort auf die anfrage";
    }

}
