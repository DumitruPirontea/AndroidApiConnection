package com.example.androidapi_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.MyViewHolder> {

    private Context mContext; //contexto
    private List<Coin> mData; //lista monedas

    //-----------------------Constructor--------------------------------//


    public CoinAdapter(Context mContext, List<Coin> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.coins_view,  parent, false);

        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mint.setText(mData.get(position).getMint());
        holder.number.setText(mData.get(position).getNumber());
        holder.date_in.setText(mData.get(position).getDate_in());
        holder.date_out.setText(mData.get(position).getDate_out());
        holder.material.setText(mData.get(position).getMaterial());
        holder.denomination.setText(mData.get(position).getDenomination());

        Glide.with(mContext).load(mData.get(position).getImage_obverse()).into(holder.image_obverse);
        Glide.with(mContext).load(mData.get(position).getImage_reverse()).into(holder.image_reverse);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mint;
        TextView number;
        TextView date_in;
        TextView date_out;
        TextView material;
        TextView denomination;
        ImageView image_obverse;
        ImageView image_reverse;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mint = itemView.findViewById(R.id.textView_mint);
            number = itemView.findViewById(R.id.textView_number);
            date_in = itemView.findViewById(R.id.textView_date_in);
            date_out = itemView.findViewById(R.id.textView_date_out);
            material = itemView.findViewById(R.id.textView_material);
            denomination = itemView.findViewById(R.id.textView_denomination);
            image_obverse = itemView.findViewById(R.id.imageView_obverse);
            image_reverse = itemView.findViewById(R.id.imageView_reverse);

        }
    }


}

