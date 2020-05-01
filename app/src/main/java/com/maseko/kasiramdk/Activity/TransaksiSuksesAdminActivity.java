package com.maseko.kasiramdk.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maseko.kasiramdk.R;

import butterknife.ButterKnife;

public class TransaksiSuksesAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_sukses_admin);
        ButterKnife.bind(this);
    }
}
