package com.tachnologies.myligapro.moduloDelegado.menuDelegado.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.pojo.ItemMenuAdm;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemMenuAdapter extends RecyclerView.Adapter<ItemMenuAdapter.ViewHolder> {

    private List<ItemMenuAdm> items;
    private OnItemClickListener listener;
    private Context context;
    float dpToPixels;

    public ItemMenuAdapter(List<ItemMenuAdm> items, OnItemClickListener listener, Context contexto) {
        this.items = items;
        this.listener = listener;

        context = contexto;
        int height = context.getResources().getConfiguration().screenHeightDp;
        //se resta 60 que es el margen para mostrar el titulo de la pantalla
        height = height - 60;
        //se resta 56 por que es el tamaño estandar del toolbar
        height = height - 56;

        //convertir DP a pixeles
        dpToPixels = height * context.getResources().getDisplayMetrics().density;
        dpToPixels = dpToPixels/5;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_opcion_menu, parent, false);
        //context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemMenuAdm item = items.get(position);

        holder.setOnClickListener(item, listener);

        holder.tvNombre.setText(
                context.getString(
                        R.string.common_item_param, item.getNombre()));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context)
                .load(item.getCodImagen())
                .apply(options)
                .into(holder.imgPhoto);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(ItemMenuAdm item) {
        if (!items.contains(item)) {
            items.add(item);
            notifyItemInserted(items.size() - 1);
        } else {
            update(item);
        }
    }

    public void update(ItemMenuAdm item) {
        if (items.contains(item)) {
            final int index = items.indexOf(item);
            items.set(index, item);
            notifyItemChanged(index);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgFoto)
        AppCompatImageView imgPhoto;
        @BindView(R.id.tvNombre)
        AppCompatTextView tvNombre;
        @BindView(R.id.cvMenu)
        CardView cvMenu;

        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            //System.out.println("-------------------------------- ViewHolder");
            ButterKnife.bind(this, itemView);
            this.view = itemView;

            System.out.println("------------------- dpToPixels: " + dpToPixels);

            cvMenu.setMinimumHeight((int) dpToPixels);
        }

        void setOnClickListener(final ItemMenuAdm item, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }

    }
}
