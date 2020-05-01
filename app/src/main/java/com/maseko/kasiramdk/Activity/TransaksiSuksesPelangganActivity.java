package com.maseko.kasiramdk.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.maseko.kasiramdk.Api.UserClient;
import com.maseko.kasiramdk.Config.ApiConfig;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.Respon.ResponListTransaksi;
import com.maseko.kasiramdk.Result.ListTransaksi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiSuksesPelangganActivity extends AppCompatActivity {

    @BindView(R.id.list)
    TextView list;

    StringBuilder strBuilder = new StringBuilder();
    List<ListTransaksi> resultsTransaksi;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_sukses_pelanggan);
        ButterKnife.bind(this);


        listTransaksi();

    }

    public void listTransaksi() {
        total = 0;
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();
        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ResponListTransaksi> call = getResponse.transaksiSukses(getIntent().getStringExtra("id_pelanggan"));

        call.enqueue(new Callback<ResponListTransaksi>() {
            @Override
            public void onResponse(Call<ResponListTransaksi> call, Response<ResponListTransaksi> response) {
                if (response.body().getValue().equals(1)) {
                    resultsTransaksi = response.body().getResult();

                    for (int i = 0; i < resultsTransaksi.size(); i++ ){
                        strBuilder.append(i +". "+ resultsTransaksi.get(i).getNamaBarang()
                                +"  " + resultsTransaksi.get(i).getHarga()
                                +"  "+ resultsTransaksi.get(i).getJumlah()
                                +"  "+ resultsTransaksi.get(i).getTotal()
                                +"\n");

                        total = total + Integer.parseInt(resultsTransaksi.get(i).getTotal());
                    }

                    strBuilder.append("Rp. "+Integer.toString(total));

                    list.setText(strBuilder);



                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(TransaksiSuksesPelangganActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponListTransaksi> call, Throwable t) {
                dialog.hide();
                Toast.makeText(TransaksiSuksesPelangganActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
