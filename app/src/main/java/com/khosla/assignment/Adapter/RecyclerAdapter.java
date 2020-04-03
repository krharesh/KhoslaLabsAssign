package com.khosla.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khosla.assignment.Interfaces.ItemClickListener;
import com.khosla.assignment.Model.WeatherPoJo;
import com.khosla.assignment.R;

import java.util.List;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView card_date,card_temp,card_desc;
    public ImageView card_icon;
    private ItemClickListener itemClickListener;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        card_icon = (ImageView)itemView.findViewById(R.id.card_icon);
        card_date = (TextView)itemView.findViewById(R.id.card_date);
        card_temp = (TextView)itemView.findViewById(R.id.card_temp);
        card_desc = (TextView)itemView.findViewById(R.id.card_desc);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }
}

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    private List<WeatherPoJo> mlist;
    private Context context;

    public RecyclerAdapter(List<WeatherPoJo> list, Context context) {
        this.mlist = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.weather_list,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, final int i) {
        if (mlist.get(i).weather_desc.equalsIgnoreCase("Clear")){
            recyclerViewHolder.card_icon.setImageResource(R.drawable.clear);
        } else if (mlist.get(i).weather_desc.equalsIgnoreCase("Clouds")){
            recyclerViewHolder.card_icon.setImageResource(R.drawable.clouds);
        } else if (mlist.get(i).weather_desc.equalsIgnoreCase("Rain")){
            recyclerViewHolder.card_icon.setImageResource(R.drawable.rain);
        }
        recyclerViewHolder.card_date.setText(mlist.get(i).getWeather_date());
        recyclerViewHolder.card_desc.setText(mlist.get(i).getWeather_desc());
        recyclerViewHolder.card_temp.setText(mlist.get(i).getTemp());

        recyclerViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, mlist.get(i).getTemp(), Toast.LENGTH_SHORT).show();
            }
        });
        }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}