package com.example.recyclerviewdb;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdb.Model.Descargas;
import com.example.recyclerviewdb.Model.MListAppLocal;
import com.example.recyclerviewdb.Model.MListAppRemote;
import com.example.recyclerviewdb.Model.MListAppShow;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescargasFragment extends Fragment {

    View rootview;

    ArrayList<Descargas> listaDescargas;
    RecyclerView recyclerViewUp;
    AdminSQLiteOpenHelper conn;
    ImageView img_deletedescarga;
    private CheckBox chk_select_all;
    TextView txt_apps;

    int estadoApps = 0;
    ArrayList<MListAppRemote> listAppRemote;
    ArrayList<MListAppLocal> listaAppLocal;

    ArrayList<String> arrayListLocal;
    ArrayList<String> arrayListRemote;


    public DescargasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootview = inflater.inflate(R.layout.fragment_descargas, container, false);
        conn = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        chk_select_all = rootview.findViewById(R.id.chk_select_all);
        listaDescargas = new ArrayList<>();

        recyclerViewUp = rootview.findViewById(R.id.recycler_Lista);
        img_deletedescarga = rootview.findViewById(R.id.img_deletedescarga);

        recyclerViewUp.setLayoutManager(new LinearLayoutManager(getContext()));


        cargarBottomSheet();

        consultarListaDescarga();

        final AdapterRecyclerDescarga adapter = new AdapterRecyclerDescarga(listaDescargas, getContext());
        recyclerViewUp.setAdapter(adapter);

        chk_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk_select_all.isChecked()) {
                    for (Descargas model : listaDescargas) {
                        model.setEstadocheck(true);
                    }
                } else {
                    for (Descargas model : listaDescargas) {
                        model.setEstadocheck(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });


        img_deletedescarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk_select_all.isChecked()) {

                    for (int a = 0; a < listaDescargas.size(); a++) {     //eliminar todos las archivos creados...
                        String rootDeleteFile = listaDescargas.get(a).getRuta();
                        File file = new File(rootDeleteFile);
                        boolean deleted = file.delete();
                    }
                    listaDescargas.clear(); //eliminar del recyclerView
                    adapter.notifyDataSetChanged();
                    chk_select_all.setChecked(false);
                    deleteDownloadAll(); // eliminar de Base de Datos
                } else {
                    Snackbar.make(v, "Marca Seleccionar todos, para eliminar todos los items", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return rootview;
    }


    private void cargarBottomSheet() {

        listAppRemote = new ArrayList<>();

        String cadena_respuesta=  "{\n" +
                "    \"packageName\": \"com.discord\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"packageName\": \"org.telegram.messenger\"\n" +
                "  }";



                        listaApps(cadena_respuesta);



//        AddBottomSheetDialogFragment addPhotoBottomDialogFragment =
//                AddBottomSheetDialogFragment.newInstance();
//        addPhotoBottomDialogFragment.show(getFragmentManager(),
//                "add_photo_dialog_fragment");

    }

    private void listaApps(String cadena_respuesta) {
        try {
            JSONArray jsonArray = new JSONArray(cadena_respuesta);

            arrayListRemote = new ArrayList<>();

            for (int a = 0; a < jsonArray.length(); a++) {
                JSONObject jsonObject = jsonArray.getJSONObject(a);
                arrayListRemote.add(jsonObject.getString("packageName"));
            }

            listaAppLocal = new ArrayList<>();
            List<PackageInfo> packList = getContext().getPackageManager().getInstalledPackages(0);
            arrayListLocal = new ArrayList<>();
            for (int i = 0; i < packList.size(); i++) {
                PackageInfo packInfo = packList.get(i);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.v("packInfo", String.valueOf(packInfo.applicationInfo));
                }
                if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    // listaAppLocal.add(new MListAppLocal(R.drawable.publicaciones6, packInfo.applicationInfo.loadLabel(getContext().getPackageManager()).toString(), packInfo.packageName));
                    arrayListLocal.add(packInfo.packageName);
                }
            }

            arrayListLocal.removeAll(arrayListRemote);

            Log.v("arrayListRemote", arrayListRemote.toString());
            Log.v("arrayListLocal", arrayListLocal.toString());

            ArrayList<MListAppShow> mListAppShows = new ArrayList<>();

            for (int nom = 0; nom < arrayListLocal.size(); nom++) {
                String packageName = arrayListLocal.get(nom);
                PackageManager manager = getContext().getPackageManager();
                PackageInfo info = null;
                try {
                    info = manager.getPackageInfo(packageName, 0);

                    if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        String nombrePackage = (String) info.applicationInfo.loadLabel(manager);
                        mListAppShows.add(new MListAppShow(R.drawable.baseline_history_toggle_off_24, nombrePackage, arrayListLocal.get(nom)));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Log.v("mlistShow", String.valueOf(mListAppShows));

            if (mListAppShows.size() > 0) {
                AddBottomSheetDialogFragment.apkArrayList(mListAppShows);
                AddBottomSheetDialogFragment addPhotoBottomDialogFragment =
                        AddBottomSheetDialogFragment.newInstance();
                addPhotoBottomDialogFragment.show(getFragmentManager(),
                        "add_photo_dialog_fragment");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void cleaningAllTrash(File ArchivoDirectorio) { /* Parametro File (Ruta) */
        if (ArchivoDirectorio.isDirectory()) /* Si es Directorio */ {
            /* Recorremos sus Hijos y los ELiminamos */
            for (File hijo : ArchivoDirectorio.listFiles())
                cleaningAllTrash(hijo); /*Recursividad Para Saber si no hay Subcarpetas */
        } else
            ArchivoDirectorio.delete(); /* Si no, se trata de un File ,solo lo Eliminamos*/
    }


    private void consultarListaDescarga() {

        SQLiteDatabase db = conn.getReadableDatabase();
        Descargas descargas = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_DESCARGAS, null);

        while (cursor.moveToNext()) {
            descargas = new Descargas();
            descargas.setCodigo(cursor.getString(0));
            descargas.setNombre(cursor.getString(1));
            descargas.setRuta(cursor.getString(2));
            descargas.setFecha(cursor.getString(3));
            descargas.setUrlwebdata(cursor.getString(4));
            descargas.setEstadopdf(cursor.getString(5));
            listaDescargas.add(descargas);
        }
    }


    private void deleteDownloadAll() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        int cantidad = BaseDeDatos.delete("descargas", null, null);

        BaseDeDatos.close();
    }
}
