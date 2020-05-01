package com.maseko.kasiramdk.Api;

import com.maseko.kasiramdk.Respon.Login;
import com.maseko.kasiramdk.Respon.Register;
import com.maseko.kasiramdk.Respon.ResponKeranjang;
import com.maseko.kasiramdk.Respon.ResponListBarang;
import com.maseko.kasiramdk.Respon.ResponListPelanggan;
import com.maseko.kasiramdk.Respon.ResponListTransaksi;
import com.maseko.kasiramdk.Respon.ServerResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UserClient {

    @FormUrlEncoded
    @POST("kasirAMDK/login_customer.php")
    Call<Login> login(@Field("username") String username,
                      @Field("password") String password);

    @FormUrlEncoded
    @POST("kasirAMDK/register_customer.php")
    Call<Register> register(@Field("nama") String nama,
                            @Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("kasirAMDK/list_barang.php")
    Call<ResponListBarang> listBarang(@Field("id_user") String id_user);

    @Multipart
    @POST("kasirAMDK/tambah_barang.php")
    Call<ServerResponse> upload(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("kasirAMDK/edit_barang.php")
    Call<ServerResponse> editBarang(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("kasirAMDK/hapus_barang.php")
    Call<ServerResponse> hapusBarang(@Field("id_barang") String id_barang);

    @FormUrlEncoded
    @POST("kasirAMDK/list_pelanggan.php")
    Call<ResponListPelanggan> listPelanggan(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("kasirAMDK/list_keranjang.php")
    Call<ResponKeranjang> listKeranjang(@Field("id_pelanggan") String id_pelanggan);

    @FormUrlEncoded
    @POST("kasirAMDK/edit_keranjang.php")
    Call<ServerResponse> editKeranjang(@Field("id_keranjang") String id_keranjang,
                                       @Field("jumlah") String jumlah,
                                       @Field("harga") String harga);

    @FormUrlEncoded
    @POST("kasirAMDK/edit_nama.php")
    Call<ServerResponse> editNama(@Field("id_user") String id_user,
                                  @Field("nama") String nama);

    @FormUrlEncoded
    @POST("kasirAMDK/edit_nama_toko.php")
    Call<ServerResponse> editNamaToko(@Field("id_user") String id_user,
                                      @Field("nama_toko") String nama_toko);


    @FormUrlEncoded
    @POST("kasirAMDK/tambah_pelanggan.php")
    Call<ServerResponse> tambahPelanggan(@Field("id_pelanggan") String id_pelanggan,
                                         @Field("id_user") String id_user,
                                         @Field("nama_pelanggan") String nama_pelanggan);

    @FormUrlEncoded
    @POST("kasirAMDK/tambah_keranjang.php")
    Call<ServerResponse> tambahBarangKeKeranjang(@Field("id_user") String id_user,
                                                 @Field("id_pelanggan") String id_pelanggan,
                                                 @Field("id_barang") String id_barang,
                                                 @Field("nama_barang") String nama_barang,
                                                 @Field("harga") String harga,
                                                 @Field("jumlah") String jumlah,
                                                 @Field("total") String total);

    @FormUrlEncoded
    @POST("kasirAMDK/tambah_transaksi.php")
    Call<ServerResponse> tambahTransaksi(@Field("id_user") String id_user,
                                         @Field("id_pelanggan") String id_pelanggan,
                                         @Field("uang") String uang,
                                         @Field("total") String total,
                                         @Field("kembali") String kembali);

    @FormUrlEncoded
    @POST("kasirAMDK/list_transaksi_sukses.php")
    Call<ResponListTransaksi> transaksiSukses(@Field("id_pelanggan") String id_pelanggan);


}
