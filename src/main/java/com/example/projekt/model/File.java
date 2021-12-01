package com.example.projekt.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String filename;

    private String mimeType;

    /**
     * Der Konstruktor dient dazu, per 
     * @param filename
     */
    private File(Integer id, String filename, String mimeType)
    {
        this.id = id;
        this.filename = filename;
        this.mimeType = mimeType;
    }

    public static File loadFromDB(Integer id)
    {
        // load information from database,
        // construct file

        String fileNameFromDatabase = "myfile.jpg";
        String mimeTypeFromDatabase = "jpg";

        return new File(id, fileNameFromDatabase, mimeTypeFromDatabase);
    }

    public String getFileName()
    {
        return this.filename;
    }

    public String getMimeType()
    {
        return this.mimeType;
    }
    

}
