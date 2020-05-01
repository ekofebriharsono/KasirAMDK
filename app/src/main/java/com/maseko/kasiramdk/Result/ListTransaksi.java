package com.maseko.kasiramdk.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTransaksi {

    @SerializedName("nama_barang")
    @Expose
    private String namaBarang;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("total")
    @Expose
    private String total;

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

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
