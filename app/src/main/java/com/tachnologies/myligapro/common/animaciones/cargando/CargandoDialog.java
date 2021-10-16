package com.tachnologies.myligapro.common.animaciones.cargando;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tachnologies.myligapro.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CargandoDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.ivCargando)
    ImageView ivCargando;
    @BindView(R.id.tvMensaje)
    TextView tvMensaje;
    private int resMsge;

    public CargandoDialog(){
        this.resMsge = R.string.common_cargando;
    }

    public CargandoDialog(int res){
        this.resMsge = res;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.cargando_dialog, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Glide.with(this).asGif().load(R.drawable.balon2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCargando);

        tvMensaje.setText(resMsge);

        return dialog;
    }

    public void setMensajeCarga(int resMsje) {
        this.tvMensaje.setText(resMsje);
    }
}