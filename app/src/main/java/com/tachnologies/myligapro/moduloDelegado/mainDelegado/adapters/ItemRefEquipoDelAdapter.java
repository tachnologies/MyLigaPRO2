package com.tachnologies.myligapro.moduloDelegado.mainDelegado.adapters;

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
import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemRefEquipoDelAdapter extends RecyclerView.Adapter<ItemRefEquipoDelAdapter.ViewHolder>{
    private List<RefEquipoDelegado> equipos;
    private OnItemClickListener listener;
    private Context context;

    public ItemRefEquipoDelAdapter(List<RefEquipoDelegado> equipos, OnItemClickListener listener) {
        this.equipos = equipos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancha_liga, parent, false);
        context = parent.getContext();

        return new ItemRefEquipoDelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RefEquipoDelegado item = equipos.get(position);

        holder.setOnClickListener(item, listener);

        holder.tvData.setText(
                context.getString(
                        R.string.item_cancha_liga_data, item.getNombreEquipo()));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        if(item.getUrlLogoEquipo() != null && !item.getUrlLogoEquipo().isEmpty()){
            Glide.with(context)
                    .load(item.getUrlLogoEquipo())
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

    public void add(RefEquipoDelegado equipo){
        if (!equipos.contains(equipo)){
            equipos.add(equipo);
            notifyItemInserted(equipos.size()-1);
        } else {
            update(equipo);
        }
    }

    public void update(RefEquipoDelegado equipo) {
        if (equipos.contains(equipo)){
            final int index = equipos.indexOf(equipo);
            equipos.set(index, equipo);
            notifyItemChanged(index);
        }
    }

    public void remove(RefEquipoDelegado equipo){
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

        void setOnClickListener(final RefEquipoDelegado item, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
