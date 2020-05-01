package com.maseko.kasiramdk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maseko.kasiramdk.Activity.KeranjangPelangganActivity;
import com.maseko.kasiramdk.Result.ListPelangganResult;

import java.util.List;

public class ListPelangganAdapter extends RecyclerView.Adapter<ListPelangganAdapter.ViewHolder> {

    private Context context;
    private List<ListPelangganResult> results;

    public ListPelangganAdapter(Context context, List<ListPelangganResult> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pelanggan, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListPelangganResult result = results.get(position);

        if (result.getStatus() == 1){
            holder.namaPelanggan.setText(result.getNamaPelanggan() + "  (DONE)");
        }else {
            holder.namaPelanggan.setText(result.getNamaPelanggan());
        }


        holder.tanggal.setText(result.getTanggal());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, KeranjangPelangganActivity.class);
                intent.putExtra("id_pelanggan", result.getIdPelanggan());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView namaPelanggan, tanggal;

        private LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            namaPelanggan = itemView.findViewById(R.id.EdNamaPelanggan);
            tanggal = itemView.findViewById(R.id.EdTanggal);
            linearLayout = itemView.findViewById(R.id.klik);

        }

        @Override
        public void onClick(View view) {

        }
    }
}