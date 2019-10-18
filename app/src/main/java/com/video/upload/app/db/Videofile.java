package com.video.upload.app.db;

public class Videofile {
    public Videofile() {
    }

    public Videofile(String filepath, String status) {
        this.filepath = filepath;
        this.status=status;
    }

    String filepath,status;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
