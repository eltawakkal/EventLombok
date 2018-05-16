package com.example.thebestone.eventlombok.models;

public class UserEvent {

    private String kodeEvent;
    private String emailUser;
    private String photoUser;
    private String namaEvent;
    private String descEvent;
    private String photoEvent;
    private String jenisEvent;
    private String lokasiEvent;
    private String waktuEvent;
    private String tglEvent;

    public UserEvent() {
    }

    public UserEvent(String kodeEvent, String emailUser, String photoUser, String namaEvent, String descEvent, String photoEvent, String jenisEvent, String lokasiEvent, String waktuEvent, String tglEvent) {
        this.kodeEvent = kodeEvent;
        this.emailUser = emailUser;
        this.photoUser = photoUser;
        this.namaEvent = namaEvent;
        this.descEvent = descEvent;
        this.photoEvent = photoEvent;
        this.lokasiEvent = lokasiEvent;
        this.waktuEvent = waktuEvent;
        this.tglEvent = tglEvent;
        this.jenisEvent = jenisEvent;
    }

    public String getPhotoEvent() {
        return photoEvent;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public String getKodeEvent() {
        return kodeEvent;
    }

    public String getJenisEvent() {
        return jenisEvent;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public String getDescEvent() {
        return descEvent;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getLokasiEvent() {
        return lokasiEvent;
    }

    public String getWaktuEvent() {
        return waktuEvent;
    }

    public String getTglEvent() {
        return tglEvent;
    }
}
