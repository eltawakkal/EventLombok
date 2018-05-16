package com.example.thebestone.eventlombok.models;

public class User {

    private String namaUser;
    private String emailUser;
    private String imgUrlUser;

    public User() {
    }

    public User(String namaUser, String emailUser, String imgUrlUser) {
        this.namaUser = namaUser;
        this.emailUser = emailUser;
        this.imgUrlUser = imgUrlUser;
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
