package com.alan.moviles.contactossqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContactViewAcitvity extends AppCompatActivity {

    DataBaseHelper myDB;

    EditText ncontrol, namee, sur, age, weigth, heigth, desc, career;
    Button ver, actulizar, eliminar;

    String nombre, apes, car, edad, peso, estatura, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        myDB = new DataBaseHelper(this);


        //casteo de widgets
        ncontrol = findViewById(R.id.txtncontrol);
        namee = findViewById(R.id.txtNombre);
        sur = findViewById(R.id.txtApellidos);
        age = findViewById(R.id.txtEdad);
        career = findViewById(R.id.txtCarrera);
        weigth = findViewById(R.id.txtPeso);
        heigth = findViewById(R.id.txtEstatura);
        desc = findViewById(R.id.txtDescripcion);
        ver = findViewById(R.id.btnverDatos);
        actulizar = findViewById(R.id.btnUpdate);
        eliminar = findViewById(R.id.btnDelete);

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });

        actulizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

    }
    private void readData() {
        int ncontro = Integer.parseInt(ncontrol.getText().toString());
        Cursor cursor = myDB.readData(ncontro);
        if (cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
            nombre = cursor.getString(0);
            apes = cursor.getString(1);
            edad = cursor.getString(2);
            car = cursor.getString(3);
            estatura = cursor.getString(4);
            peso = cursor.getString(5);
            descripcion = cursor.getString(6);
        }
        namee.setText(nombre);
        sur.setText(apes);
        career.setText(car);
        age.setText(edad);
        weigth.setText(peso);
        heigth.setText(estatura);
        desc.setText(descripcion);
    }
    private void updateData(){
        int ncontro = Integer.parseInt(ncontrol.getText().toString());
        String nombre = namee.getText().toString();
        String apes = sur.getText().toString();
        String caree = career.getText().toString();
        String edad = age.getText().toString();
        String peso = weigth.getText().toString();
        String estatura = heigth.getText().toString();
        String descripcion = desc.getText().toString();

        Boolean result = myDB.updateData(ncontro, nombre, apes, caree, edad, peso, estatura, descripcion);

        if(result){
            Toast.makeText(getApplicationContext(), "1 rows affected", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "No rows affected", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteData(){
        int ncontro = Integer.parseInt(ncontrol.getText().toString());
        int result = myDB.deleteData(ncontro);

        Toast.makeText(getApplicationContext(), result+" Rows affected", Toast.LENGTH_SHORT).show();
    }
}
