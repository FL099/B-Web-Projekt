package com.example.projekt.controller;

import com.example.projekt.exceptions.Exceptionhandler;
import com.example.projekt.interfaces.IFileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.projekt.data.model.File;

@RestController
@RequestMapping("/file")
public class FileController {

    private final IFileService fileService;
    public static final List<String> allowedTypes = Arrays.asList(
            "image/png", //
            "image/jpeg", //
            "image/jpg", //
            "image/gif", //
            //"image/tiff",
            "image/svg+xml");

    /**
     * Constructor
     */
    public FileController(IFileService fileService)
    {
        this.fileService = fileService;

    }

    /**
     * Das soll dazu dienen, per ID eine File als JSON zu erhalten.
     * Erhalten bedeutet für den USER ein im Browser anzeigbaren filepath zu erhalten.
     * Normalerweise sollten Files nicht so im UI angebunden werden, aber vlt. ist es irgendwann hilfreich.
     * Best Case wäre wenn die Files in andere Klassen eingebunden werden, sowie User implements Image Extends File... 
     * Es macht wenig sinn dem User die ID für eine File zu schicken, anstelle davon ihm direkt den Filepath zu schicken.
     * @param id
     * @return file Link
     */
    @GetMapping("/{id}")
    public @ResponseBody File index(@PathVariable("id") Integer id) {
         return fileService.getFile(id);
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
        return fileService.upload(files, allowedTypes);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Exceptionhandler.handleGeneralExceptions(ex);
    }

}
