package com.deshielo.inventarioo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class ProductoLista extends ArrayAdapter <Producto> {
    private Activity context;
    private List<Producto> productoList;
    public ProductoLista(Activity context, List<Producto>productoList){

        super(context, R.layout.list_layout, productoList);
        this.context=context;
        this.productoList=productoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);

        TextView mostrar_usuario=(TextView) listViewItem.findViewById(R.id.mostrar_usuario);
        TextView mostrar_hostname=(TextView) listViewItem.findViewById(R.id.mostrar_hostname);
        TextView mostrar_marca=(TextView) listViewItem.findViewById(R.id.mostrar_marca);

        Producto producto=productoList.get(position);

        mostrar_usuario.setText(producto.getpUsuario());
        mostrar_hostname.setText(producto.getpHostname());
        mostrar_marca.setText(producto.getpMarca());

        return listViewItem;

    }
}
