package com.alan.moviles.contactossqlite;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alan.moviles.contactossqlite.Pojo.AdapterContactos;

import com.alan.moviles.contactossqlite.Pojo.Contacto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListaContactos extends AppCompatActivity implements AdapterContactos.OnClickEventsContacts{

    RecyclerView rvContactos;
    FloatingActionButton fabButton;
    ImageView connection;
    //creacion de variables de Firebase
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    Contacto cl;

    String mensaje_titulo;
    String mensaje_contenido;


    Boolean connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        rvContactos = findViewById(R.id.recyclerView);
        fabButton = findViewById(R.id.fab);
        connection = findViewById(R.id.offline);
        connect = isOnline(getApplicationContext());
        enablePersistence();

        Toast.makeText(this, connect.toString(), Toast.LENGTH_SHORT).show();


        if(connect){
            connection.setVisibility(View.GONE);
            connection.setVisibility(View.INVISIBLE);
        }else{
            connection.setVisibility(View.VISIBLE);
        }

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        
        final AdapterContactos adapter = new AdapterContactos(this, this);
        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext());
        rvContactos.setLayoutManager(l);
        rvContactos.setAdapter(adapter);


        //inicializar Firebase en el proyecto
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("contacts");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 cl = dataSnapshot.getValue(Contacto.class);
                adapter.addContact(cl);
                notification("add");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.updateContact(cl);
                notification("update");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.removeContact(cl);
                notification("remove");
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.keepSynced(true);
    }
    @Override
    public void onClickMore(){
        Intent i = new Intent(this, ContactViewAcitvity.class);
        startActivity(i);
    }
    public void notification(String tipo){
        switch (tipo){
            case "add":
                mensaje_titulo = "Nuevo contacto agregado";
                mensaje_contenido = "Abre la app para ver los nuevos contactos";
                break;

            case "remove":
                mensaje_titulo = "Contactos eliminados";
                mensaje_contenido = "Abre la app para ver los cambios";
        }
        try {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_contacts)
            .setContentTitle(mensaje_titulo)
            .setContentText(mensaje_contenido);
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + getPackageName() + "/raw/point");
        builder.setSound(alarmSound);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    private void enablePersistence() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
