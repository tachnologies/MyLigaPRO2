package com.tachnologies.myligapro.moduloAdm.listadoCanchas.view.adapters;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.pojo.ItemCanchaListado;

import java.util.List;
import android.content.Context;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemCanchaListadoAdapter extends RecyclerView.Adapter<ItemCanchaListadoAdapter.ViewHolder>{
    private List<ItemCanchaListado> items;
    private OnItemClickListener listener;
    private Context context;

    public ItemCanchaListadoAdapter(List<ItemCanchaListado> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancha_liga, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCanchaListado item = items.get(position);

        holder.setOnClickListener(item, listener);

        holder.tvData.setText(
                context.getString(
                        R.string.item_cancha_liga_data, item.getNombre()));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context)
                .load(R.drawable.campo_2)
                .apply(options)
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ItemCanchaListado getPrimero(){
        return items.get(0);
    }

    public void add(ItemCanchaListado item){
        if (!items.contains(item)){
            items.add(item);
            notifyItemInserted(items.size()-1);
        } else {
            update(item);
        }
    }

    public void update(ItemCanchaListado item) {
        if (items.contains(item)){
            final int index = items.indexOf(item);
            items.set(index, item);
            notifyItemChanged(index);
        }
    }

    public void remove(ItemCanchaListado item){
        if (items.contains(item)){
            final int index = items.indexOf(item);
            items.remove(index);
            notifyItemRemoved(index);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgPhoto)
        AppCompatImageView imgPhoto;
        @BindView(R.id.tvData)
        AppCompatTextView tvData;

        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        void setOnClickListener(final ItemCanchaListado item, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }

    }
}
