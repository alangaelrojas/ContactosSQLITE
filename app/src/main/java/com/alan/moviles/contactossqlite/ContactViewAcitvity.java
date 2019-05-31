package com.alan.moviles.contactossqlite;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alan.moviles.contactossqlite.Pojo.Contacto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactViewAcitvity extends AppCompatActivity {

    //Firebase variables
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    DataBaseHelper myDB;

    EditText ncontrol, namee, sur, age, weigth, heigth, desc, career;
    Button ver, actulizar, eliminar;

    String nombre, apes, car, edad, peso, estatura, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        //Firebase init
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("contacts");

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
                if(isOnline(getApplicationContext())){
                    readDataFirebase();
                }
                else{
                    readData();
                }
            }
        });

        actulizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline(getApplicationContext())){
                    updateDataFirebase();

                }
                else {
                    updateData();
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOnline(getApplicationContext())){
                    deleteDataFirebase();
                }
                else {
                    deleteData();
                }
            }
        });
    }
    private void readDataFirebase(){
        String ncontro = ncontrol.getText().toString();
        databaseReference = database.getReference("contacts/"+ncontro);
        try {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd =
                            new GenericTypeIndicator<HashMap<String, Object>>() {};
                    Map<String, Object> objectHashMap = dataSnapshot.getValue(objectsGTypeInd);
                    try {
                        ArrayList<Object> objectArrayList = new ArrayList<>(objectHashMap.values());
                        namee.setText(objectArrayList.get(1).toString());
                        career.setText(objectArrayList.get(0).toString());
                        sur.setText(objectArrayList.get(5).toString());
                        age.setText(objectArrayList.get(6).toString());
                        weigth.setText(objectArrayList.get(2).toString());
                        heigth.setText(objectArrayList.get(3).toString());
                        desc.setText(objectArrayList.get(4).toString());
                    }
                    catch (Exception ex){
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void updateDataFirebase(){
        int ncontro = Integer.parseInt(ncontrol.getText().toString());
        String nombre = namee.getText().toString();
        String apes = sur.getText().toString();
        String caree = career.getText().toString();
        String edad = age.getText().toString();
        String peso = weigth.getText().toString();
        String estatura = heigth.getText().toString();
        String descripcion = desc.getText().toString();

        databaseReference = database.getReference("contacts/"+ncontro);
        databaseReference.setValue(new Contacto(nombre, apes, caree, edad, estatura, peso, descripcion));
        Toast.makeText(getApplicationContext(), "Guardado Exitosamente en Firebase", Toast.LENGTH_LONG).show();
    }
    private void deleteDataFirebase(){
        String ncontro = ncontrol.getText().toString();
        databaseReference = database.getReference("contacts/"+ncontro);
        databaseReference.removeValue();
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

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() && networkInfo.isConnected();
    }
}
