package com.alan.moviles.contactossqlite.Pojo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.moviles.contactossqlite.ContactViewAcitvity;
import com.alan.moviles.contactossqlite.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterContactos extends RecyclerView.Adapter<AdapterContactos.HolderContacto> {

    private List<Contacto> contactolist = new ArrayList<>();
    private Context c;
    public OnClickEventsContacts onClickEventsContacts;

    public AdapterContactos(Context c, OnClickEventsContacts onClickEventsContacts){
        this.c = c;
        this.onClickEventsContacts = onClickEventsContacts;
    }
    //Actualizacion del Recycler
    public void addContact (Contacto contacto){
        contactolist.add(contacto);
        notifyItemInserted(contactolist.size());
    }
    public void removeContact (Contacto contacto){
        contactolist.remove(contacto);
        notifyItemRemoved(contactolist.size());
    }
    public void updateContact (Contacto contacto){
        contactolist.remove(contacto);
        notifyItemChanged(contactolist.size());
    }
    @Override
    public HolderContacto onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.cardview_contacto, viewGroup, false);
        return new HolderContacto(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderContacto holderContacto, int position) {
        Log.d("position", String.valueOf(position));
        holderContacto.nombre.setText(contactolist.get(position).getNombres());
    }

    @Override
    public int getItemCount() {
        return contactolist.size();
    }

    //Interface que se implementa en la actividad
    public interface OnClickEventsContacts{
        void onClickMore();
    }
    public class HolderContacto extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nombre;
        private CardView mostrar;
        private HolderContacto(View v) {
            super(v);
            nombre = v.findViewById(R.id.cardNameContact);
            mostrar = v.findViewById(R.id.cardview_contactos);

            mostrar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cardview_contactos:
                    onClickEventsContacts.onClickMore();
                    break;
            }
        }
    }
}
