package com.example.juan.marvelheroes.Models;

/**
 * Created by Juan on 17/07/2017.
 */

public class Thumbnail {

    private String path;
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFullPath(){
        return path + "." + extension;
    }
}
