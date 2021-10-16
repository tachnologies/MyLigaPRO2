package com.tachnologies.myligapro.moduloAdminJugadores.adapters;

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
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.adapters.OnItemClickListener;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminJugadorAdapter extends RecyclerView.Adapter<AdminJugadorAdapter.ViewHolder>{
    private List<Jugador> jugadores;
    private OnItemClickListener listener;
    private Context context;

    public AdminJugadorAdapter(List<Jugador> jugadores, OnItemClickListener listener){
        this.jugadores = jugadores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancha_liga,
                parent, false);
        context = parent.getContext();

        return new AdminJugadorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Jugador item = jugadores.get(position);
        holder.setOnClickListener(item, listener);

        holder.tvData.setText(
            context.getString( R.string.common_doble_parametro_string,
                item.getNombre(), item.getApellido()));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        if(item.getUrlFoto() != null && !item.getUrlFoto().isEmpty()){
            Glide.with(context)
                    .load(item.getUrlFoto())
                    .apply(options)
                    .into(holder.imgPhoto);
        }else{
            Glide.with(context)
                    .load(R.drawable.jugador)
                    .apply(options)
                    .into(holder.imgPhoto);
        }
    }

    public void add(Jugador jugador){
        if (!jugadores.contains(jugador)){
            jugadores.add(jugador);
            notifyItemInserted(jugadores.size()-1);
        } else {
            update(jugador);
        }
    }

    public void update(Jugador jugador) {
        if (jugadores.contains(jugador)){
            final int index = jugadores.indexOf(jugador);
            jugadores.set(index, jugador);
            notifyItemChanged(index);
        }
    }

    public void remove(Jugador jugador){
        int index = -1;
        for(int i = 0; i < jugadores.size(); i++){
            if(jugador.getUid().equals(jugadores.get(i).getUid())){
                index = i;
                break;
            }
        }

        if(index != -1){
            jugadores.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
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

        void setOnClickListener(final Jugador item, final OnItemClickListener listener) {
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
