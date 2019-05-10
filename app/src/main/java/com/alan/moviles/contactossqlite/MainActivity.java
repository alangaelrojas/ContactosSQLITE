package com.alan.moviles.contactossqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDB;

    EditText ncontrol, name, sur, age, weigth, heigth, desc, career;
    Button guardar, ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DataBaseHelper(this);

        ncontrol = findViewById(R.id.edtncontrol);
        name = findViewById(R.id.edtnombre);
        sur = findViewById(R.id.edtapellidos);
        age = findViewById(R.id.edtEdad);
        career = findViewById(R.id.edtcarrera);
        weigth = findViewById(R.id.edtPeso);
        heigth = findViewById(R.id.edtEstatura);
        desc = findViewById(R.id.edtDescripcion);
        guardar = findViewById(R.id.btnguardar);
        ver = findViewById(R.id.btnir);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDatos();
                ncontrol.setText("");
                name.setText("");
                sur.setText("");
                career.setText("");
                age.setText("");
                weigth.setText("");
                heigth.setText("");
                desc.setText("");
            }
        });
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ContactViewAcitvity.class);
                startActivity(i);
            }
        });
    }

    private void sendDatos(){
        int ncontro = Integer.parseInt(ncontrol.getText().toString());
        String nombre = name.getText().toString();
        String apes = sur.getText().toString();
        String caree = career.getText().toString();
        String edad = age.getText().toString();
        String peso = weigth.getText().toString();
        String estatura = heigth.getText().toString();
        String descripcion = desc.getText().toString();

        Boolean result = myDB.insertData(ncontro, nombre, apes, caree, edad, peso, estatura, descripcion);

        if(result){
            Toast.makeText(getApplicationContext(), "Inserción exitosa", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Error en inserción", Toast.LENGTH_SHORT).show();
        }
    }
}
