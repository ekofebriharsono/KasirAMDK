package com.maseko.kasiramdk.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maseko.kasiramdk.Api.UserClient;
import com.maseko.kasiramdk.BarangAdminAdapter;
import com.maseko.kasiramdk.BarangPelangganAdapter;
import com.maseko.kasiramdk.Config.ApiConfig;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.RandomString;
import com.maseko.kasiramdk.Respon.ResponListBarang;
import com.maseko.kasiramdk.Respon.ServerResponse;
import com.maseko.kasiramdk.Result.ListBarangResult;
import com.maseko.kasiramdk.Result.ListTransaksi;
import com.maseko.kasiramdk.SharedPreference.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardPelangganActivity extends AppCompatActivity {

    @BindView(R.id.recyclerviewBarang)
    RecyclerView recyclerviewBarang;

    @BindView(R.id.EdNamaPelanggan)
    EditText EdNamaPelanggan;

    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    BarangPelangganAdapter barangPelangganAdapter;

    private List<ListBarangResult> results = new ArrayList<>();

    String id_pelanggan = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pelanggan);
        ButterKnife.bind(this);

        // results = Collections.singletonList(new ListBarangResult("Cleo", "3000","10"));
        // barangPelangganAdapter = new BarangPelangganAdapter(DashboardPelangganActivity.this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DashboardPelangganActivity.this);
        recyclerviewBarang.setLayoutManager(mLayoutManager);
        recyclerviewBarang.setItemAnimator(new DefaultItemAnimator());
        recyclerviewBarang.setAdapter(barangPelangganAdapter);

        listBarang();

    }

    @OnClick(R.id.btnKeranjang)
    public void keranjangPelanggan() {
        Intent intent = new Intent(DashboardPelangganActivity.this, KeranjangForPelangganActivity.class);
        intent.putExtra("id_pelanggan", id_pelanggan);
        startActivity(intent);
    }

    public void onItemClick() {
        barangPelangganAdapter.setOnItemClickListener(new BarangPelangganAdapter.OnItemClickListener() {
            @Override
            public void onItemTambahClick(int position, ListBarangResult result) {
                Toast.makeText(DashboardPelangganActivity.this, result.getNamaBarang(), Toast.LENGTH_SHORT).show();
                tambahBarangKeKeranjang(result.getIdBarang(), result.getNamaBarang(), result.getHarga(),"1",result.getHarga());


            }

        });
    }

    @OnClick(R.id.btnSimpan)
    public void simpanNamaPelanggan() {
        simpanNama();
    }

    public void tambahBarangKeKeranjang(String id_barang, String nama_barang, String harga, String jumlah, String total) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();


        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ServerResponse> call = getResponse.tambahBarangKeKeranjang(SaveSharedPreference.getIdUser(DashboardPelangganActivity.this), id_pelanggan,id_barang,nama_barang,harga,jumlah,total);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(DashboardPelangganActivity.this, "Barang telah ditambahkan!", Toast.LENGTH_SHORT).show();
                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(DashboardPelangganActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                btnSimpan.setEnabled(true);
                EdNamaPelanggan.setEnabled(true);
                dialog.hide();
                Toast.makeText(DashboardPelangganActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void simpanNama() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();

        RandomString randomString = new RandomString();
        id_pelanggan = randomString.getRandom();

        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ServerResponse> call = getResponse.tambahPelanggan(id_pelanggan,SaveSharedPreference.getIdUser(DashboardPelangganActivity.this), EdNamaPelanggan.getText().toString());

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getSuccess()) {
                    btnSimpan.setEnabled(false);
                    EdNamaPelanggan.setEnabled(false);
                    dialog.hide();
                } else {
                    dialog.hide();
                    btnSimpan.setEnabled(true);
                    EdNamaPelanggan.setEnabled(true);
                    Toast.makeText(DashboardPelangganActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                btnSimpan.setEnabled(true);
                EdNamaPelanggan.setEnabled(true);
                dialog.hide();
                Toast.makeText(DashboardPelangganActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void listBarang() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();
        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ResponListBarang> call = getResponse.listBarang(SaveSharedPreference.getIdUser(DashboardPelangganActivity.this));

        call.enqueue(new Callback<ResponListBarang>() {
            @Override
            public void onResponse(Call<ResponListBarang> call, Response<ResponListBarang> response) {
                if (response.body().getValue().equals(1)) {
                    results = response.body().getResult();

                    barangPelangganAdapter = new BarangPelangganAdapter(DashboardPelangganActivity.this, results);
                    recyclerviewBarang.setAdapter(barangPelangganAdapter);

                    onItemClick();

                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(DashboardPelangganActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponListBarang> call, Throwable t) {
                dialog.hide();
                Toast.makeText(DashboardPelangganActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
