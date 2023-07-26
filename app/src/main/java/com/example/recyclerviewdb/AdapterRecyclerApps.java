package com.example.recyclerviewdb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recyclerviewdb.Model.MListAppShow;

import java.util.ArrayList;


public class AdapterRecyclerApps extends RecyclerView.Adapter<AdapterRecyclerApps.ViewHolderApps> {

    ArrayList<MListAppShow> listApps;
    Context context;

    public AdapterRecyclerApps(ArrayList<MListAppShow> listApps, Context context) {
        this.listApps = listApps;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecyclerApps.ViewHolderApps onCreateViewHolder(@NonNull ViewGroup parent, int posicion) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.item_app, parent, false);
        return new ViewHolderApps(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderApps holder, final int posicion) {

        holder.descripcion.setText(listApps.get(posicion).getNombre());
        holder.img_app.setImageResource(listApps.get(posicion).getPhotoapp());

        String appdelete = listApps.get(posicion).getPackageName();
        Log.v("appdelete", appdelete);

        Uri uri = Uri.fromParts("package", appdelete, null);
        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, uri);
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listApps.size();
    }

    public class ViewHolderApps extends RecyclerView.ViewHolder {

        public TextView descripcion;
        public ImageView img_app;

        public ViewHolderApps(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.tv_descripcion);
            img_app = itemView.findViewById(R.id.img_app);
        }
    }
}
