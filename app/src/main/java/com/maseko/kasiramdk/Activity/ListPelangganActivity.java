package com.maseko.kasiramdk.Activity;

import android.app.ProgressDialog;
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
import com.maseko.kasiramdk.Respon.ResponListBarang;
import com.maseko.kasiramdk.Respon.ResponListPelanggan;
import com.maseko.kasiramdk.Result.ListPelangganResult;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.SharedPreference.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPelangganActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewPelanggan)
    RecyclerView recyclerViewPelanggan;

    ListPelangganAdapter listPelangganAdapter;

    private List<ListPelangganResult> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pelanggan);
        ButterKnife.bind(this);

      //  results = Collections.singletonList(new ListPelangganResult("Mas Kevin", "24 Mei 2019"));
       // listPelangganAdapter = new ListPelangganAdapter(ListPelangganActivity.this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ListPelangganActivity.this);
        recyclerViewPelanggan.setLayoutManager(mLayoutManager);
        recyclerViewPelanggan.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPelanggan.setAdapter(listPelangganAdapter);

        listPelanggan();
    }

    public void listPelanggan() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();
        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ResponListPelanggan> call = getResponse.listPelanggan(SaveSharedPreference.getIdUser(ListPelangganActivity.this));

        call.enqueue(new Callback<ResponListPelanggan>() {
            @Override
            public void onResponse(Call<ResponListPelanggan> call, Response<ResponListPelanggan> response) {
                if (response.body().getValue().equals(1)) {
                    results = response.body().getResult();

                    listPelangganAdapter = new ListPelangganAdapter(ListPelangganActivity.this, results);
                    recyclerViewPelanggan.setAdapter(listPelangganAdapter);

                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(ListPelangganActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponListPelanggan> call, Throwable t) {
                dialog.hide();
                Toast.makeText(ListPelangganActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
