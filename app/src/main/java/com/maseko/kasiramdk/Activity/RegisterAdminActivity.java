package com.maseko.kasiramdk.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.maseko.kasiramdk.Api.UserClient;
import com.maseko.kasiramdk.Config.ApiConfig;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.Respon.Register;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAdminActivity extends AppCompatActivity {

    @BindView(R.id.edUsername)
    EditText edUsername;

    @BindView(R.id.edPassword)
    EditText edPassword;

    @BindView(R.id.edNama)
    EditText edNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        ButterKnife.bind(this);
    }

    public void register(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();

        final String Username = edUsername.getText().toString();
        final String Password = edPassword.getText().toString();
        final String Nama = edNama.getText().toString();

        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<Register> call = getResponse.register(Nama,Username, Password);

        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body().getValue().equals(1)) {
                  Toast.makeText(RegisterAdminActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    dialog.hide();
                } else {
                    dialog.hide();
                    Toast.makeText(RegisterAdminActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                dialog.hide();
                Toast.makeText(RegisterAdminActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnRegister)
    public void registerCustomer(){
        register();
    }
}
