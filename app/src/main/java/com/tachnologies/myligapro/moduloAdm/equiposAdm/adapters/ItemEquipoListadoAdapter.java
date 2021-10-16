package com.tachnologies.myligapro.moduloAdm.equiposAdm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.pojo.ItemEquipoListado;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemEquipoListadoAdapter extends RecyclerView.Adapter<ItemEquipoListadoAdapter.ViewHolder>{
    private List<ItemEquipoListado> equipos;
    private OnItemClickListener listener;
    private Context context;

    public ItemEquipoListadoAdapter(List<ItemEquipoListado> equipos, OnItemClickListener listener) {
        this.equipos = equipos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancha_liga, parent, false);
        context = parent.getContext();

        return new ItemEquipoListadoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemEquipoListado item = equipos.get(position);

        holder.setOnClickListener(item, listener);

        holder.tvData.setText(
                context.getString(
                        R.string.item_cancha_liga_data, item.getNombreEquipo()));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        if(item.getUrlFotoEquipo() != null && !item.getUrlFotoEquipo().isEmpty()){
            Glide.with(context)
                    .load(item.getUrlFotoEquipo())
                    .apply(options)
                    .into(holder.imgPhoto);
        }else{
            Glide.with(context)
                    .load(R.drawable.escudo_equipo)
                    .apply(options)
                    .into(holder.imgPhoto);
        }

    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public void add(ItemEquipoListado equipo){
        if (!equipos.contains(equipo)){
            equipos.add(equipo);
            notifyItemInserted(equipos.size()-1);
        } else {
            update(equipo);
        }
    }

    public void update(ItemEquipoListado equipo) {
        if (equipos.contains(equipo)){
            final int index = equipos.indexOf(equipo);
            equipos.set(index, equipo);
            notifyItemChanged(index);
        }
    }

    public void remove(ItemEquipoListado equipo){
        int index = -1;
        for(int i = 0; i < equipos.size(); i++){
            if(equipo.getUidEquipo().equals(equipos.get(i).getUidEquipo())){
                index = i;
                break;
            }
        }

        if(index != -1){
            equipos.remove(index);
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

        void setOnClickListener(final ItemEquipoListado item, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongItemClick(item);
                    return true;
                }
            });
        }


    }
}
