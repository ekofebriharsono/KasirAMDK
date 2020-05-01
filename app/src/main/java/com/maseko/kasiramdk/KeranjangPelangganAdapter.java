package com.maseko.kasiramdk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maseko.kasiramdk.Result.ListBarangResult;
import com.maseko.kasiramdk.Result.ListKeranjangPelangganResult;
import com.maseko.kasiramdk.Result.ListPelangganResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KeranjangPelangganAdapter extends RecyclerView.Adapter<KeranjangPelangganAdapter.ViewHolder> {

    private Context context;
    private List<ListKeranjangPelangganResult> results;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemTambahClick(int position, ListKeranjangPelangganResult result);
        void onItemKurangClick(int position, ListKeranjangPelangganResult result);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public KeranjangPelangganAdapter(Context context, List<ListKeranjangPelangganResult> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_barang_keranjang, parent, false);
        ViewHolder holder = new ViewHolder(v, mListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListKeranjangPelangganResult result = results.get(position);

        holder.namaBarang.setText(result.getNamaBarang());
        holder.harga.setText(result.getHarga());
        holder.jumlah.setText(result.getJumlah());

       /* Picasso.with(context)--------------
                .load(result.getGambar())
                .placeholder(R.drawable.ic_insert_photo)onItemClick();
                .error(R.drawable.ic_insert_photo)
                .into(holder.imageView4);*/

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namaBarang, harga, jumlah;
        private ImageView imageView4;
        private Button btnTambah,btnKurang;

        public ViewHolder(View itemView,  final OnItemClickListener listener) {
            super(itemView);


            namaBarang = itemView.findViewById(R.id.namaBarang);
            harga = itemView.findViewById(R.id.hargaBarang);
            jumlah = itemView.findViewById(R.id.jumlahBarang);
            imageView4 = itemView.findViewById(R.id.imageView4);
            btnTambah = itemView.findViewById(R.id.btnTambah);
            btnKurang = itemView.findViewById(R.id.btnKurang);

            btnKurang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        //  ListBarangResult result = results.get(position);
                        listener.onItemKurangClick(position, results.get(position));

                    }
                }
            });

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