package com.maseko.kasiramdk.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.maseko.kasiramdk.Api.UserClient;
import com.maseko.kasiramdk.Config.ApiConfig;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.Respon.Login;
import com.maseko.kasiramdk.SharedPreference.SaveSharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.checkBoxLoginCustomer)
    CheckBox checkBoxLoginCustomer;

    @BindView(R.id.checkBox)
    CheckBox checkBoxIngat;

    @BindView(R.id.edNip)
    EditText username;

    @BindView(R.id.edPassword)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (SaveSharedPreference.getIngat(LoginActivity.this)){
            username.setText(SaveSharedPreference.getUsername(LoginActivity.this));
            password.setText(SaveSharedPreference.getPassword(LoginActivity.this));
            checkBoxIngat.setChecked(true);
        }
    }

    @OnClick(R.id.btnLogin)
    public void loginAdmin(){
        login();
    }

    @OnClick(R.id.btnRegister)
    public void register(){
        startActivity(new Intent(LoginActivity.this, RegisterAdminActivity.class));
    }

    public void login() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();

        final String Username = username.getText().toString();
        final String Password = password.getText().toString();

        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<Login> call = getResponse.login(Username, Password);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getValue().equals(1)) {
                    SaveSharedPreference.setIdUser(LoginActivity.this, response.body().getIdUser());
                    SaveSharedPreference.setNama(LoginActivity.this, response.body().getNama());
                    SaveSharedPreference.setUsername(LoginActivity.this, response.body().getUsername());
                    SaveSharedPreference.setPassword(LoginActivity.this, response.body().getPassword());
                    SaveSharedPreference.setNamaToko(LoginActivity.this, response.body().getNamaToko());

                    if (checkBoxIngat.isChecked()) {
                        SaveSharedPreference.setIngat(LoginActivity.this, true);
                    } else {
                        SaveSharedPreference.setIngat(LoginActivity.this, false);
                    }

                    if (checkBoxLoginCustomer.isChecked()){
                        startActivity(new Intent(LoginActivity.this, DashboardPelangganActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    finish();

                } else {
                    dialog.hide();
                    Toast.makeText(LoginActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                dialog.hide();
                Toast.makeText(LoginActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
