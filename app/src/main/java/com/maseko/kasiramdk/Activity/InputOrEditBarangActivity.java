package com.maseko.kasiramdk.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.maseko.kasiramdk.Api.UserClient;
import com.maseko.kasiramdk.Config.ApiConfig;
import com.maseko.kasiramdk.R;
import com.maseko.kasiramdk.Respon.ServerResponse;
import com.maseko.kasiramdk.SharedPreference.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputOrEditBarangActivity extends AppCompatActivity {

    @BindView(R.id.edNamaBarang)
    EditText edNamaBarang;

    @BindView(R.id.edHargaBarang)
    EditText edHargaBarang;

    @BindView(R.id.edStokBarang)
    EditText edStokBarang;

    @BindView(R.id.gambarBarang)
    ImageView gambarBarang;

    private static final int CAMERA_PIC_REQUEST = 1111;
    private static final String IMAGE_DIRECTORY = "/PresensiHistory";
    private String mImageFileLocation = "";
    private String postPath;
    Bitmap thumbnail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_or_edit_barang);
        ButterKnife.bind(this);

        if (getIntent().getStringExtra("aksi").equals("edit")) {
            edNamaBarang.setText(getIntent().getStringExtra("nama"));
            edStokBarang.setText(getIntent().getStringExtra("stok"));
            edHargaBarang.setText(getIntent().getStringExtra("harga"));

            Picasso.with(InputOrEditBarangActivity.this)
                    .load(getIntent().getStringExtra("gambar"))
                    .placeholder(R.drawable.ic_insert_photo)
                    .error(R.drawable.ic_insert_photo)
                    .into(gambarBarang);
        }
    }

    @OnClick(R.id.gambarBarang)
    public void openCamera() {
        takePhotoFromCamera();
    }

    private void takePhotoFromCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAMERA_PIC_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
       /* if (requestCode == CAMERA) {
            thumbnail = (Bitmap) data.getExtras().get("data");
            previewImg.setImageBitmap(thumbnail);
            //saveImage(thumbnail);
            previewImg.setVisibility(View.VISIBLE);
            close.setVisibility(View.VISIBLE);
            save_to_gallery.setVisibility(View.VISIBLE);
            btnCamera.setVisibility(View.GONE);

        }*/
        if (requestCode == CAMERA_PIC_REQUEST) {
            thumbnail = (Bitmap) data.getExtras().get("data");
            gambarBarang.setImageBitmap(thumbnail);
            postPath = mImageFileLocation;

        }
    }

    @OnClick(R.id.btnSimpan)
    public void simpanBarang() {

        if (thumbnail != null) {
            saveImage(thumbnail);
            tambahOrEditBarang();
        } else {
            tambahOrEditBarang();
        }

    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {

            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("filesave", "File Saved::--->" + f.getAbsolutePath());
            postPath = f.getAbsolutePath();
            //  Toast.makeText(FotoActivity.this, postPath, Toast.LENGTH_SHORT).show();

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void tambahOrEditBarang() {
        final ProgressDialog dialogUpload = new ProgressDialog(this);
        dialogUpload.setTitle("Upload image");
        dialogUpload.setMessage("Uploading ...");
        dialogUpload.setCancelable(false);
        dialogUpload.show();

        Map<String, RequestBody> map = new HashMap<>();

        if (postPath != null) {
            File file = new File(postPath);
            RequestBody gambar = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("file\"; filename=\"" + file.getName() + "\"", gambar);
        } else {
            RequestBody gambar = RequestBody.create(MediaType.parse("*/*"), "nothing");
            map.put("file\"; filename=\"" + "nothing" + "\"", gambar);
        }

        RequestBody idUser = RequestBody.create(MediaType.parse("*/*"), SaveSharedPreference.getIdUser(InputOrEditBarangActivity.this));
        RequestBody namaBarang = RequestBody.create(MediaType.parse("*/*"), edNamaBarang.getText().toString());
        RequestBody harga = RequestBody.create(MediaType.parse("*/*"), edHargaBarang.getText().toString());
        RequestBody stok = RequestBody.create(MediaType.parse("*/*"), edStokBarang.getText().toString());

        UserClient getResponse = ApiConfig.getRetrofit().create(UserClient.class);
        Call<ServerResponse> call;

        if (getIntent().getStringExtra("aksi").equals("edit")) {
            RequestBody idBarang = RequestBody.create(MediaType.parse("*/*"), getIntent().getStringExtra("id_barang"));
            map.put("id_barang", idBarang);
            map.put("id_user", idUser);
            map.put("nama_barang", namaBarang);
            map.put("harga", harga);
            map.put("stok", stok);
            call = getResponse.editBarang(map);
        } else {
            map.put("id_user", idUser);
            map.put("nama_barang", namaBarang);
            map.put("harga", harga);
            map.put("stok", stok);
            call = getResponse.upload(map);
        }

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body().getSuccess()) {
                    dialogUpload.hide();
                    Toast.makeText(InputOrEditBarangActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    dialogUpload.hide();
                    Toast.makeText(InputOrEditBarangActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                dialogUpload.hide();
                Toast.makeText(InputOrEditBarangActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
