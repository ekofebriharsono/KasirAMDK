package com.maseko.kasiramdk.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultPelanggan {
    @SerializedName("id_pelanggan")
    @Expose
    private String idPelanggan;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_pelanggan")
    @Expose
    private String namaPelanggan;

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

}
