package com.maseko.kasiramdk.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.maseko.kasiramdk.Api.UserClient;
import com.maseko.kasiramdk.BarangAdminAdapter;
import com.maseko.kasiramdk.Config.ApiConfig;
import com.maseko.kasiramdk.ListPelangganAdapter;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.Respon.Register;
import com.maseko.kasiramdk.Respon.ResponListBarang;
import com.maseko.kasiramdk.Respon.ServerResponse;
import com.maseko.kasiramdk.Result.ListBarangResult;
import com.maseko.kasiramdk.Result.ListPelangganResult;
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

public class ListBarangActivity extends AppCompatActivity {

    @BindView(R.id.recyclerviewBarang)
    RecyclerView recyclerviewBarang;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    BarangAdminAdapter barangAdminAdapter;

    private List<ListBarangResult> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);
        ButterKnife.bind(this);

        // results = Collections.singletonList(new ListBarangResult("Cleo", "3000","10"));
        // barangAdminAdapter = new BarangAdminAdapter(ListBarangActivity.this, results);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ListBarangActivity.this);
        recyclerviewBarang.setLayoutManager(mLayoutManager);
        recyclerviewBarang.setItemAnimator(new DefaultItemAnimator());
        recyclerviewBarang.setAdapter(barangAdminAdapter);

        listBarang();
    }

    @OnClick(R.id.fab)
    public void addBarang() {
        Intent intent = new Intent(ListBarangActivity.this, InputOrEditBarangActivity.class);
        intent.putExtra("aksi", "tambah");
        startActivity(intent);
    }

    public void listBarang() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();
        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ResponListBarang> call = getResponse.listBarang(SaveSharedPreference.getIdUser(ListBarangActivity.this));

        call.enqueue(new Callback<ResponListBarang>() {
            @Override
            public void onResponse(Call<ResponListBarang> call, Response<ResponListBarang> response) {
                if (response.body().getValue().equals(1)) {
                    results = response.body().getResult();

                    barangAdminAdapter = new BarangAdminAdapter(ListBarangActivity.this, results);
                    recyclerviewBarang.setAdapter(barangAdminAdapter);

                    onItemClick();

                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(ListBarangActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponListBarang> call, Throwable t) {
                dialog.hide();
                Toast.makeText(ListBarangActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onItemClick() {
        barangAdminAdapter.setOnItemClickListener(new BarangAdminAdapter.OnItemClickListener() {
            @Override
            public void onItemEditClick(int position, ListBarangResult result) {
                // Toast.makeText(ListBarangActivity.this, result.getNamaBarang(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListBarangActivity.this, InputOrEditBarangActivity.class);
                intent.putExtra("aksi", "edit");
                intent.putExtra("id_barang", result.getIdBarang());
                intent.putExtra("nama", result.getNamaBarang());
                intent.putExtra("stok", result.getStok());
                intent.putExtra("harga", result.getHarga());
                intent.putExtra("gambar", result.getGambar());
                startActivity(intent);

            }

            @Override
            public void onItemHapusClick(int position, ListBarangResult result) {
                hapusBarang(result.getIdBarang());
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listBarang();
    }

    public void hapusBarang(String idBarang) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();
        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ServerResponse> call = getResponse.hapusBarang(idBarang);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(ListBarangActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.hide();
                    listBarang();
                } else {
                    dialog.hide();
                    Toast.makeText(ListBarangActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                dialog.hide();
                Toast.makeText(ListBarangActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
