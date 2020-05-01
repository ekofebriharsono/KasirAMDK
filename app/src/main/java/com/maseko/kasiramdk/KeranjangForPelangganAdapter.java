package com.maseko.kasiramdk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maseko.kasiramdk.Result.ListKeranjangPelangganResult;

import java.util.List;

public class KeranjangForPelangganAdapter extends RecyclerView.Adapter<KeranjangForPelangganAdapter.ViewHolder> {

    private Context context;
    private List<ListKeranjangPelangganResult> results;

    public KeranjangForPelangganAdapter(Context context, List<ListKeranjangPelangganResult> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_barang_keranjang, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListKeranjangPelangganResult result = results.get(position);

        holder.namaBarang.setText(result.getNamaBarang());
        holder.harga.setText(result.getHarga());
        holder.jumlah.setText(result.getJumlah());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView namaBarang, harga, jumlah;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            namaBarang = itemView.findViewById(R.id.namaBarang);
            harga = itemView.findViewById(R.id.hargaBarang);
            jumlah = itemView.findViewById(R.id.jumlahBarang);

        }

        @Override
        public void onClick(View view) {

        }
    }
}