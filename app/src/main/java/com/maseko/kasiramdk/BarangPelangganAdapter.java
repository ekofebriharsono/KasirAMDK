package com.maseko.kasiramdk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maseko.kasiramdk.Activity.InputOrEditBarangActivity;
import com.maseko.kasiramdk.Activity.KeranjangForPelangganActivity;
import com.maseko.kasiramdk.Result.ListBarangResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BarangPelangganAdapter extends RecyclerView.Adapter<BarangPelangganAdapter.ViewHolder> {

    private Context context;
    private List<ListBarangResult> results;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemTambahClick(int position, ListBarangResult result);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BarangPelangganAdapter(Context context, List<ListBarangResult> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_barang_pelanggan, parent, false);
        ViewHolder holder = new ViewHolder(v, mListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListBarangResult result = results.get(position);

        holder.namaBarang.setText(result.getNamaBarang());
        holder.harga.setText(result.getHarga());

        Picasso.with(context)
                .load(result.getGambar())
                .placeholder(R.drawable.ic_insert_photo)
                .error(R.drawable.ic_insert_photo)
                .into(holder.imageView4);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namaBarang, harga;
        private Button btnTambah;
        private ImageView imageView4;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            namaBarang = itemView.findViewById(R.id.namaBarang);
            harga = itemView.findViewById(R.id.hargaBarang);
            btnTambah = itemView.findViewById(R.id.btnTambah);
            imageView4 = itemView.findViewById(R.id.imageView4);

            btnTambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        //  ListBarangResult result = results.get(position);
                        listener.onItemTambahClick(position, results.get(position));

                    }
                }
            });
        }


    }
}