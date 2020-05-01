package com.maseko.kasiramdk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maseko.kasiramdk.Activity.InputOrEditBarangActivity;
import com.maseko.kasiramdk.Result.ListBarangResult;
import com.maseko.kasiramdk.Result.ListKeranjangPelangganResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BarangAdminAdapter extends RecyclerView.Adapter<BarangAdminAdapter.ViewHolder> {

    private Context context;
    private List<ListBarangResult> results;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemEditClick(int position, ListBarangResult result);
        void onItemHapusClick(int position, ListBarangResult result);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BarangAdminAdapter(Context context, List<ListBarangResult> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_barang_admin, parent, false);
        ViewHolder holder = new ViewHolder(v, mListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListBarangResult result = results.get(position);

        holder.namaBarang.setText(result.getNamaBarang());
        holder.harga.setText("Rp. "+result.getHarga());
        holder.sisa.setText("Sisa "+result.getStok());

        Picasso.with(context)
                .load(result.getGambar())
                .placeholder(R.drawable.ic_insert_photo)
                .error(R.drawable.ic_insert_photo)
                .into(holder.gambar);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namaBarang, harga, sisa;
        private Button btnEditBarang, btnHapusBarang;
        private ImageView gambar;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);


            namaBarang = itemView.findViewById(R.id.namaBarang);
            harga = itemView.findViewById(R.id.hargaBarang);
            sisa = itemView.findViewById(R.id.sisaBarang);
            btnEditBarang = itemView.findViewById(R.id.btnEditBarang);
            btnHapusBarang = itemView.findViewById(R.id.btnHapusBarang);
            gambar = itemView.findViewById(R.id.imageView4);


            btnEditBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                      //  ListBarangResult result = results.get(position);
                        listener.onItemEditClick(position, results.get(position));

                    }
                }
            });

            btnHapusBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        //  ListBarangResult result = results.get(position);
                        listener.onItemHapusClick(position, results.get(position));

                    }
                }
            });

        }


    }
}