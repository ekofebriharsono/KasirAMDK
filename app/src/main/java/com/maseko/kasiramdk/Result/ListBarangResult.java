package com.maseko.kasiramdk.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListBarangResult {
    @SerializedName("id_barang")
    @Expose
    private String idBarang;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_barang")
    @Expose
    private String namaBarang;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("stok")
    @Expose
    private String stok;
    @SerializedName("gambar")
    @Expose
    private String gambar;

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
