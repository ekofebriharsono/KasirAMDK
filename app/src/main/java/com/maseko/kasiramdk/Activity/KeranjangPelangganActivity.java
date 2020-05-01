package com.maseko.kasiramdk.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maseko.kasiramdk.Api.UserClient;
import com.maseko.kasiramdk.BarangAdminAdapter;
import com.maseko.kasiramdk.BarangPelangganAdapter;
import com.maseko.kasiramdk.Config.ApiConfig;
import com.maseko.kasiramdk.KeranjangPelangganAdapter;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.Respon.ResponKeranjang;
import com.maseko.kasiramdk.Respon.ResponListBarang;
import com.maseko.kasiramdk.Respon.ServerResponse;
import com.maseko.kasiramdk.Result.ListBarangResult;
import com.maseko.kasiramdk.Result.ListKeranjangPelangganResult;
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

public class KeranjangPelangganActivity extends AppCompatActivity {

    @BindView(R.id.recyclerviewKeranjang)
    RecyclerView recyclerviewKeranjang;

    @BindView(R.id.hargaTotal)
    TextView hargaTotal;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.kembalian)
    TextView kembalian;

    KeranjangPelangganAdapter keranjangPelangganAdapter;
    List<ListKeranjangPelangganResult> results = new ArrayList<>();
    int total = 0;
    int Kembalian = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_pelanggan);
        ButterKnife.bind(this);

        // results = Collections.singletonList(new ListKeranjangPelangganResult("Cleo", "3000", "3"));
        //  keranjangPelangganAdapter = new KeranjangPelangganAdapter(KeranjangPelangganActivity.this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(KeranjangPelangganActivity.this);
        recyclerviewKeranjang.setLayoutManager(mLayoutManager);
        recyclerviewKeranjang.setItemAnimator(new DefaultItemAnimator());
        recyclerviewKeranjang.setAdapter(keranjangPelangganAdapter);

        listKeranjang();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (editText.getText().length() == 0) {
                    editText.setError("Harap masukkan jumlah uang");
                    editText.setText("0");
                }
                 Kembalian = (Integer.parseInt(editText.getText().toString()) - total);
                kembalian.setText("Rp. " + Integer.toString(Kembalian));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick(R.id.btnBayar)
    public void bayar() {
       // startActivity(new Intent(KeranjangPelangganActivity.this, TransaksiSuksesAdminActivity.class));
        tambahTransaksi();
    }

    public void listKeranjang() {
        total = 0;
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();
        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ResponKeranjang> call = getResponse.listKeranjang(getIntent().getStringExtra("id_pelanggan"));

        call.enqueue(new Callback<ResponKeranjang>() {
            @Override
            public void onResponse(Call<ResponKeranjang> call, Response<ResponKeranjang> response) {
                if (response.body().getValue().equals(1)) {
                    results = response.body().getResult();


                    for (int i = 0; i < results.size(); i++) {
                        total = total + Integer.parseInt(results.get(i).getTotal());
                    }

                    hargaTotal.setText("Rp. " + Integer.toString(total));

                    keranjangPelangganAdapter = new KeranjangPelangganAdapter(KeranjangPelangganActivity.this, results);
                    recyclerviewKeranjang.setAdapter(keranjangPelangganAdapter);

                    onItemClick();

                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(KeranjangPelangganActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponKeranjang> call, Throwable t) {
                dialog.hide();
                Toast.makeText(KeranjangPelangganActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onItemClick() {
        keranjangPelangganAdapter.setOnItemClickListener(new KeranjangPelangganAdapter.OnItemClickListener() {
            @Override
            public void onItemTambahClick(int position, ListKeranjangPelangganResult result) {
                int jumlah = (Integer.parseInt(result.getJumlah()) + 1);
                editKeranjang(result.getIdKeranjang(), result.getHarga(), Integer.toString(jumlah));

            }

            @Override
            public void onItemKurangClick(int position, ListKeranjangPelangganResult result) {
                int jumlah = (Integer.parseInt(result.getJumlah()) - 1);
                editKeranjang(result.getIdKeranjang(), result.getHarga(), Integer.toString(jumlah));
            }

        });
    }

    public void editKeranjang(String idKeranjang, String harga, String jumlah) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();
        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ServerResponse> call = getResponse.editKeranjang(idKeranjang, jumlah, harga);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getSuccess()) {
                    listKeranjang();
                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(KeranjangPelangganActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                dialog.hide();
                Toast.makeText(KeranjangPelangganActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void tambahTransaksi() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();
        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ServerResponse> call = getResponse.tambahTransaksi(SaveSharedPreference.getIdUser(KeranjangPelangganActivity.this),getIntent().getStringExtra("id_pelanggan"),editText.getText().toString(),Integer.toString(total), Integer.toString(Kembalian) );

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getSuccess()) {
                    startActivity(new Intent(KeranjangPelangganActivity.this, TransaksiSuksesAdminActivity.class));
                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(KeranjangPelangganActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                dialog.hide();
                Toast.makeText(KeranjangPelangganActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
