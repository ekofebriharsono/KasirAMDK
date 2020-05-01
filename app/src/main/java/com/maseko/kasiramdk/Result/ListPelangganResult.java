package com.maseko.kasiramdk.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPelangganResult {
   /* String nama;
    String tanggal;

    public ListPelangganResult(String nama, String tanngal){
        this.nama = nama;
        this.tanggal = tanngal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }*/


    @SerializedName("id_pelanggan")
    @Expose
    private String idPelanggan;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_pelanggan")
    @Expose
    private String namaPelanggan;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("status")
    @Expose
    private int status;


    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
