package com.etsi.tit.c2c.tablon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsi.tit.c2c.R;

import java.util.List;

/**
 * Created by josel on 14/03/2018.
 */

public class AnuncioAdapter extends BaseAdapter{

    /*Atributos*/
    private Context context;
    int layout;
    private List<Anuncio> anuncioList;

    /*Constructor*/
    public AnuncioAdapter(Context context, int layout, List<Anuncio> datos) {
        this.layout = layout;
        this.context = context;
        this.anuncioList = datos;
    }

    /*Metodos*/
    @Override
    public int getCount() {
        return anuncioList.size();
    }

    @Override
    public Object getItem(int i) {
        return anuncioList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, parent,false);

        ImageView imagen = (ImageView)view.findViewById(R.id.imagen);
        TextView txtAutor = (TextView)view.findViewById(R.id.autor);
        TextView txtTelefono = (TextView)view.findViewById(R.id.contacto);
        TextView txtAnuncio = (TextView)view.findViewById(R.id.anuncio);

        imagen.setImageResource(R.drawable.book);
        txtAutor.setText(anuncioList.get(position).nombre);
        txtTelefono.setText(anuncioList.get(position).telefono);
        txtAnuncio.setText(anuncioList.get(position).anuncio);

        return view;
    }
}
