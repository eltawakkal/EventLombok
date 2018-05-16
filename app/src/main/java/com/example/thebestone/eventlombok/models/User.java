package com.example.thebestone.eventlombok.models;

public class User {

    private String namaUser;
    private String emailUser;
    private String imgUrlUser;
    private String status;

    public User() {
    }

    public User(String namaUser, String emailUser, String imgUrlUser, String status) {
        this.namaUser = namaUser;
        this.emailUser = emailUser;
        this.imgUrlUser = imgUrlUser;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getImgUrlUser() {
        return imgUrlUser;
    }
}
