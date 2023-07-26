package com.example.recyclerviewdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DescargasActivity extends AppCompatActivity {
    EditText edt_codigo, edt_fecha, edt_file, edt_rutaurl;

    Button btRegistrar, btBuscar, btEliminar, btModificar, btVaciar, btListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargas);

        edt_codigo = findViewById(R.id.edt_itemcodigo);
        edt_fecha = findViewById(R.id.edt_fecha);
        edt_file = findViewById(R.id.edt_itemfile);
        edt_rutaurl = findViewById(R.id.edt_itemrutaurl);

        btListar = findViewById(R.id.btnListar);
        btRegistrar = findViewById(R.id.btnRegistrar);
        btBuscar = findViewById(R.id.btnBuscar);
        btEliminar = findViewById(R.id.btnEliminar);
        btModificar = findViewById(R.id.btnModificar);
        btVaciar = findViewById(R.id.btnVaciar);

        btRegistrar.setOnClickListener(view -> {
            Registrar();
        });
        btBuscar.setOnClickListener(view -> {
            Buscar();
        });
        btEliminar.setOnClickListener(view -> {
            Eliminar();
        });
        btModificar.setOnClickListener(view -> {
            Modificar();
        });
        btVaciar.setOnClickListener(view -> {
            Vaciar();
        });
        btListar.setOnClickListener(view -> {
            listar();
        });

    }


    private void Vaciar() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        int cantidad = BaseDeDatos.delete("descargas", null, null);
        BaseDeDatos.close();

        edt_codigo.setText("");
        edt_file.setText("");
        edt_rutaurl.setText("");
        edt_fecha.setText("");


        if (cantidad >= 1) {
            Toast.makeText(this, "Eliminacion exitosa", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "no existe el articulo que deseas eliminar", Toast.LENGTH_SHORT).show();

            edt_codigo.setText("");
            edt_file.setText("");
            edt_rutaurl.setText("");
            edt_fecha.setText("");

            BaseDeDatos.close();
        }

    }

    private void Eliminar() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edt_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            int cantidad = BaseDeDatos.delete("descargas", "codigo=" + codigo, null);
            BaseDeDatos.close();

            edt_codigo.setText("");
            edt_file.setText("");
            edt_file.setText("");
            edt_rutaurl.setText("");

            if (cantidad == 1) {
                Toast.makeText(this, "Eliminacion exitosa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "no existe el articulo que deseas eliminar", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();

            }

        } else {
            Toast.makeText(this, "Debes ingresar un codigo para eliminiar", Toast.LENGTH_SHORT).show();
        }

    }


    private void Modificar() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        String codigo = edt_codigo.getText().toString();
        String nombre = edt_file.getText().toString();
        String ruta = edt_rutaurl.getText().toString();
        String fecha = edt_fecha.getText().toString();

        if (!codigo.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("ruta", ruta);
            registro.put("fecha", fecha);

            int cantidad = BasedeDatos.update("descargas", registro, "codigo=" + codigo, null);

            BasedeDatos.close();

            if (cantidad == 1) {
                Toast.makeText(this, "articulo modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "el articulo no existe", Toast.LENGTH_SHORT).show();
                edt_codigo.setText("");
                edt_file.setText("");
                edt_fecha.setText("");
                edt_rutaurl.setText("");
            }
        }


    }

    private void Buscar() {


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edt_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery(

                    ("select nombre, ruta, fecha from descargas where codigo=" + codigo), null);

            if (fila.moveToFirst()) {
                edt_file.setText(fila.getString(0));
                edt_rutaurl.setText(fila.getString(1));
                edt_fecha.setText(fila.getString(2));
                BaseDeDatos.close();

            } else {
                Toast.makeText(this, "no existe el codigo buscado", Toast.LENGTH_SHORT).show();
                edt_codigo.setText("");
                edt_rutaurl.setText("");
                edt_fecha.setText("");
                edt_file.setText("");
                BaseDeDatos.close();
            }

        } else
            Toast.makeText(this, "Debes ingresar un codigo de busqueda", Toast.LENGTH_SHORT).show();
    }

    private void Registrar() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        SQLiteDatabase BasedeDatos = admin.getWritableDatabase();

        String codigo = edt_codigo.getText().toString();
        String nombre = edt_file.getText().toString();
        String rutaurl = edt_rutaurl.getText().toString();
        String fecha = edt_fecha.getText().toString();

        if (!codigo.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("ruta", rutaurl);
            registro.put("fecha", fecha);

            BasedeDatos.insert("descargas", null, registro);
            BasedeDatos.close();

            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "llená los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public void listar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        if (BaseDeDatos != null) {
            Cursor c = BaseDeDatos.rawQuery("select * from descargas", null);
            int cantidad = c.getCount();
            int i = 0;

            String[] arreglo = new String[cantidad];

            if (c.moveToFirst()) {
                do {
                    String linea = c.getString(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(3);

                    arreglo[i] = linea;
                    i++;

                } while (c.moveToNext());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arreglo);

            ListView lista = findViewById(R.id.lista);
            lista.setAdapter(adapter);

        }


    }

}
