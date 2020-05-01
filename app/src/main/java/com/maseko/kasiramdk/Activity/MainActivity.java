package com.maseko.kasiramdk.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maseko.kasiramdk.Api.UserClient;
import com.maseko.kasiramdk.BarangAdminAdapter;
import com.maseko.kasiramdk.Config.ApiConfig;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.Respon.Register;
import com.maseko.kasiramdk.Respon.ServerResponse;
import com.maseko.kasiramdk.SharedPreference.SaveSharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.namaAdmin)
    TextView namaAdmin;

    @BindView(R.id.namaToko)
    TextView namaToko;

    @BindView(R.id.btnEditNama)
    Button btnEditNama;

    @BindView(R.id.btnEditNamaToko)
    Button btnEditNamaToko;

    boolean statusUpdateNama = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        namaAdmin.setText("Nama Admin   : " + SaveSharedPreference.getNama(MainActivity.this));
        namaToko.setText("Nama Toko     : " + SaveSharedPreference.getNamaToko(MainActivity.this));
    }

    @OnClick(R.id.btnLogout)
    public void doLogout() {
        this.finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @OnClick(R.id.btnPelanggan)
    public void listPelanggan() {
        startActivity(new Intent(MainActivity.this, ListPelangganActivity.class));
    }

    @OnClick(R.id.btnBarang)
    public void listBarang() {
        startActivity(new Intent(MainActivity.this, ListBarangActivity.class));
    }

    @OnClick(R.id.btnEditNama)
    public void editNama() {
        dialogEdit("editNama");
    }

    @OnClick(R.id.btnEditNamaToko)
    public void editNamaTOko() {
        dialogEdit("editNamaToko");
    }

    public void dialogEdit(String jenisAksi) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_edit_nama);

        final EditText edNama = dialog.findViewById(R.id.edNama);
        final TextView title = dialog.findViewById(R.id.title);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);


        if (jenisAksi.equals("editNamaToko")) {
            title.setText("Edit Nama Toko");
            edNama.setHint("Masukkan Nama Toko");

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateNamaToko(edNama.getText().toString());
                }
            });
        } else {
            title.setText("Edit Nama");
            edNama.setHint("Masukkan Nama ");
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateNama(edNama.getText().toString());
                }
            });
        }


        dialog.show();
    }

    public void updateNama(final String nama) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();

        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ServerResponse> call = getResponse.editNama(SaveSharedPreference.getIdUser(MainActivity.this), nama);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    namaAdmin.setText("Nama Admin : "+nama);
                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(MainActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                dialog.hide();
                Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateNamaToko(final String namaTokoUpdate) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();

        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ServerResponse> call = getResponse.editNamaToko(SaveSharedPreference.getIdUser(MainActivity.this), namaTokoUpdate);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    namaToko.setText("Nama Toko : "+namaTokoUpdate);
                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(MainActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                dialog.hide();
                Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
