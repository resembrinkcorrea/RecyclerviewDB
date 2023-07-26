package com.example.recyclerviewdb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdb.Model.Descargas;

import java.io.File;
import java.util.ArrayList;

public class AdapterRecyclerDescarga extends RecyclerView.Adapter<AdapterRecyclerDescarga.DescargaViewHolder> {

    ArrayList<Descargas> listaDescargas;
    Context context;

    public AdapterRecyclerDescarga(ArrayList<Descargas> listaDescargas, Context context) {
        this.listaDescargas = listaDescargas;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecyclerDescarga.DescargaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_descarga, null);
        return new DescargaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerDescarga.DescargaViewHolder holder, final int position) {


//        holder.imageView.setAnimation(AnimationUtils.loadAnimation(context , R.anim.fade_transition_animation));
//        holder.card_animdownload.setAnimation(AnimationUtils.loadAnimation(context , R.anim.fade_scale_animation));
        holder.fecha.setText(listaDescargas.get(position).getFecha());
        holder.nombre.setText(listaDescargas.get(position).getNombre());
        holder.chk_delete.setChecked(listaDescargas.get(position).isEstadocheck());
        holder.chk_delete.setTag(listaDescargas.get(position));

        String codigo = listaDescargas.get(position).getCodigo();

        String ultimo = codigo.substring(codigo.length() - 1);

        int dato = Integer.parseInt(ultimo);

        if (dato == 0 || dato == 5) {
            holder.img_icon.setImageResource(R.drawable.baseline_history_toggle_off_24);
        } else if (dato == 1 || dato == 6) {
            holder.img_icon.setImageResource(R.drawable.baseline_history_toggle_off_24);
        } else if (dato == 2 || dato == 7) {
            holder.img_icon.setImageResource(R.drawable.baseline_history_toggle_off_24);
        } else if (dato == 3 || dato == 8) {
            holder.img_icon.setImageResource(R.drawable.baseline_history_toggle_off_24);
        } else if (dato == 4 || dato == 9) {
            holder.img_icon.setImageResource(R.drawable.baseline_history_toggle_off_24);
        }


        holder.chk_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Descargas model = (Descargas) cb.getTag();

                model.setEstadocheck(cb.isChecked());
                listaDescargas.get(position).setEstadocheck(cb.isChecked());

            }
        });

        holder.img_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaDescargas.get(position).isEstadocheck()) {
                    deleteItemFromList(v, position); //Eliminar Recycler

                    String rutaSelected = listaDescargas.get(position).getRuta();
                    eliminarArchivoDB(rutaSelected); //Eliminar Base de Datos

                    File filecachepdf = new File(rutaSelected); // eliminar el archivo fisico de la ssd
                    deleteRecursive(filecachepdf);
                }
            }
        });


        holder.card_animdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rutaSelected = listaDescargas.get(position).getRuta();
                String nameFile = listaDescargas.get(position).getNombre();

//                Intent intent = new Intent(context, VisorPdfPrivado.class);
//                intent.putExtra("ViewType", "storage");
//                intent.putExtra("SSDFILE", rutaSelected);
//                intent.putExtra("Materia", nameFile);
//                intent.putExtra("EstadoConexion", "SinConexion");
//                context.startActivity(intent);
//                Toast.makeText(context, "Archivo Off-Line ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void eliminarArchivoDB(String rutassd) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        if (!rutassd.isEmpty()) {

            BaseDeDatos.rawQuery("Delete from descargas where ruta='" + rutassd + "'", null).moveToFirst();
            BaseDeDatos.close();
        }
    }

    private void deleteItemFromList(View v, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Eliminar Archivo ?")
                .setCancelable(false)
                .setPositiveButton("CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listaDescargas.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }

    @Override
    public int getItemCount() {
        return listaDescargas.size();
    }


    public class DescargaViewHolder extends RecyclerView.ViewHolder {

        public TextView nombre;
        public TextView ruta;
        public TextView fecha;
        public LinearLayout card_animdownload;
        public CheckBox chk_delete;
        public ImageView img_trash;
        public ImageView img_icon;


        public DescargaViewHolder(View itemView) {
            super(itemView);

            fecha = itemView.findViewById(R.id.edt_itemfecha);
            nombre = itemView.findViewById(R.id.edt_itemfile);
            ruta = itemView.findViewById(R.id.edt_itemrutaurl);
            img_icon = itemView.findViewById(R.id.img_icondescarga);

            card_animdownload = itemView.findViewById(R.id.card_animdownload);
            chk_delete = itemView.findViewById(R.id.chk_delete);
            img_trash = itemView.findViewById(R.id.img_trash);
        }
    }

    private void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }
}
