package com.deshielo.inventarioo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventarioScroll extends AppCompatActivity {

    DatabaseReference databaseProducto;
    ListView listViewProductos;
    List<Producto>productoList;
    List<Producto>productoListReset;
    Button btn_up_prod;
    SearchView searchView;

    private static final int FILE_SHARE_PERMISSION = 102;
    private ImageView bar_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario_scroll);
////////////////////////////////////////////////////////
        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), registroscroll.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(startIntent, 0);
                overridePendingTransition(0,0);
            }
        });
        Button btn_inv = findViewById(R.id.btn_inv);
        btn_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), InventarioScroll.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(startIntent, 0);
                overridePendingTransition(0,0);
            }
        });
        Button btn_cam = findViewById(R.id.btn_cam);
        btn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Camara.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(startIntent, 0);
                overridePendingTransition(0,0);
            }
        });
////////////////////////////////////////////////////////
        databaseProducto= FirebaseDatabase.getInstance().getReference("productos");
        listViewProductos=(ListView)findViewById(R.id.listViewProductos);

        productoList=new ArrayList<>();
        productoListReset=new ArrayList<>();


        Button btn_up_prod = findViewById(R.id.btn_up_prod);

        searchView = findViewById(R.id.search);

        //aquí estaba el position

    }
//////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStart() {
        super.onStart();
        searchView = findViewById(R.id.search);
        final ProductoLista adapter=new ProductoLista(InventarioScroll.this,productoList);
        listViewProductos.setAdapter(adapter);

        databaseProducto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productoList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot productoSnapshot:dataSnapshot.getChildren()){
                        Producto producto=productoSnapshot.getValue(Producto.class);
                        productoList.add(producto);
                        productoListReset.add(producto);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
/////////////////////////////////////////////////////////////////////////////////////

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                productoList = productoListReset;
                buscar(s);
                return true;
            }


        });

        listViewProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Producto producto=productoList.get(position);
                showUpdateDialog(producto.getProdId(),producto.getpUsuario(),producto.getpUsername(),producto.getpHostname(),producto.getpUbicacion(),producto.getpMarca(),producto.getpModelo(),producto.getpAnoMan(),producto.getpNumSer(),producto.getpProcesador(),producto.getpVelocidad(),producto.getpRam(),producto.getpDD(),producto.getpDisco(),producto.getpWinVersion(),producto.getpIdiomaSO(),producto.getpMicroVersion(),producto.getpAntivirus(),producto.getpSoftware());
                return false;
            }
        });
    }
/////////////////////////////////////////////
    private void buscar(String s) {
            ArrayList<Producto>miLista = new ArrayList<>();
            if(s.isEmpty()){
                //Toast.makeText(getApplicationContext(), "Está vacío.", Toast.LENGTH_LONG).show();
                productoList = productoListReset;
                for (Producto obj : productoList){
                    miLista.add(obj);
                    ProductoLista adapter=new ProductoLista(InventarioScroll.this,miLista);
                    listViewProductos.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }else{
                for (Producto obj : productoList){
                        if(obj.getpUsuario().toLowerCase().contains(s.toLowerCase())){
                            miLista.add(obj);
                        }else{
                            miLista.remove(obj);
                        }
                        ProductoLista adapter=new ProductoLista(InventarioScroll.this,miLista);
                        listViewProductos.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                }
                productoList = miLista;
            }
    }
    /////////////////////////////////////////////

    private void showUpdateDialog(final String prodId, String pUsuario, final String pUsername, String pHostname, String pUbicacion, String pMarca, String pModelo, String pAnoMan, String pNumSer, String pProcesador, String pVelocidad, String pRam, String pDD, String pDisco, String pWinVersion, String pIdiomaSO, String pMicroVersion, String pAntivirus, String pSoftware){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);

        final TextView titulo_update = (TextView)dialogView.findViewById(R.id.titulo_update);
        final EditText up_usuario=(EditText)dialogView.findViewById(R.id.up_usuario);
        final EditText up_username=(EditText)dialogView.findViewById(R.id.up_username);
        final EditText up_hostname=(EditText)dialogView.findViewById(R.id.up_hostname);
        final EditText up_ubicacion=(EditText)dialogView.findViewById(R.id.up_ubicacion);
        final EditText up_marca=(EditText)dialogView.findViewById(R.id.up_marca);
        final EditText up_modelo=(EditText)dialogView.findViewById(R.id.up_modelo);
        final EditText up_ano_m=(EditText)dialogView.findViewById(R.id.up_ano_m);
        final EditText up_num_serie=(EditText)dialogView.findViewById(R.id.up_num_serie);
        final EditText up_pro=(EditText)dialogView.findViewById(R.id.up_pro);
        final EditText up_vel=(EditText)dialogView.findViewById(R.id.up_vel);
        final EditText up_ram=(EditText)dialogView.findViewById(R.id.up_ram);
        final EditText up_dd=(EditText)dialogView.findViewById(R.id.up_dd);
        final EditText up_disco=(EditText)dialogView.findViewById(R.id.up_disco);
        final EditText up_w_version=(EditText)dialogView.findViewById(R.id.up_w_version);
        final EditText up_idioma_so=(EditText)dialogView.findViewById(R.id.up_idioma_so);
        final EditText up_microsoft_office_version=(EditText)dialogView.findViewById(R.id.up_microsoft_office_version);
        final EditText up_antivirus=(EditText)dialogView.findViewById(R.id.up_antivirus);
        final EditText up_software=(EditText)dialogView.findViewById(R.id.up_software);



        final Button btn_up_prod=(Button)dialogView.findViewById(R.id.btn_up_prod);
        final Button btn_del_prod=(Button)dialogView.findViewById(R.id.btn_del_prod);
        final String productQR = "Sí funciona";
        bar_code = (ImageView) dialogView.findViewById(R.id.bar_code);
        final Button share=(Button)dialogView.findViewById(R.id.share);
        final AlertDialog alertDialog=dialogBuilder.create();

        final ImageView cerrar_btn=(ImageView) dialogView.findViewById(R.id.cerrar_btn);
        cerrar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 23){
                    if(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        shareQRCode();
                    }else{
                        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, FILE_SHARE_PERMISSION);
                    }
                }else{
                    shareQRCode();
                }
            }
        });



        Query query = databaseProducto.child(prodId);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot!=null){

                            titulo_update.setText("Actualizar equipo: " + dataSnapshot.child("pUsername").getValue(String.class));

                            up_usuario.setText(dataSnapshot.child("pUsuario").getValue(String.class));
                            up_username.setText(dataSnapshot.child("pUsername").getValue(String.class));
                            up_hostname.setText(dataSnapshot.child("pHostname").getValue(String.class));
                            up_ubicacion.setText(dataSnapshot.child("pUbicacion").getValue(String.class));
                            up_marca.setText(dataSnapshot.child("pMarca").getValue(String.class));
                            up_modelo.setText(dataSnapshot.child("pModelo").getValue(String.class));
                            up_ano_m.setText(dataSnapshot.child("pAnoMan").getValue(String.class));
                            up_num_serie.setText(dataSnapshot.child("pNumSer").getValue(String.class));
                            up_pro.setText(dataSnapshot.child("pProcesador").getValue(String.class));
                            up_vel.setText(dataSnapshot.child("pVelocidad").getValue(String.class));
                            up_ram.setText(dataSnapshot.child("pRam").getValue(String.class));
                            up_dd.setText(dataSnapshot.child("pDD").getValue(String.class));
                            up_disco.setText(dataSnapshot.child("pDisco").getValue(String.class));
                            up_w_version.setText(dataSnapshot.child("pWinVersion").getValue(String.class));
                            up_idioma_so.setText(dataSnapshot.child("pIdiomaSO").getValue(String.class));
                            up_microsoft_office_version.setText(dataSnapshot.child("pMicroVersion").getValue(String.class));
                            up_antivirus.setText(dataSnapshot.child("pAntivirus").getValue(String.class));
                            up_software.setText(dataSnapshot.child("pSoftware").getValue(String.class));
                        }

                        else {
                            //up_username.setText("There is no mutching data.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        btn_up_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario=up_usuario.getText().toString().trim();
                String username=up_username.getText().toString().trim();
                String hostname=up_hostname.getText().toString().trim();
                String ubicacion=up_ubicacion.getText().toString().trim();
                String marca=up_marca.getText().toString().trim();
                String modelo=up_modelo.getText().toString().trim();
                String anoman=up_ano_m.getText().toString().trim();
                String numserie=up_num_serie.getText().toString().trim();
                String procesador=up_pro.getText().toString().trim();
                String velocidad=up_vel.getText().toString().trim();
                String ram=up_ram.getText().toString().trim();
                String dd=up_dd.getText().toString().trim();
                String disco=up_disco.getText().toString().trim();
                String windowsversion=up_w_version.getText().toString().trim();
                String idiomaso=up_idioma_so.getText().toString().trim();
                String microsoftversion=up_microsoft_office_version.getText().toString().trim();
                String antivirus=up_antivirus.getText().toString().trim();
                String software=up_software.getText().toString().trim();

                updateProducto(prodId, usuario, username, hostname, ubicacion, marca, modelo, anoman, numserie, procesador, velocidad, ram, dd, disco, windowsversion, idiomaso, microsoftversion, antivirus, software);

            }
        });

        btn_del_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProducto(prodId);
            }
        });


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode("Usuario: "+pUsuario+"\nUsername: "+pUsername+"\nHostname: "+pHostname+"\nUbicación: "+pUbicacion+"\nMarca: "+pMarca+"\nModelo: "+pModelo+"\nAño de Manufactura: "+pAnoMan+"\nNúmero de serie: "+pNumSer+"\nProcesador: "+pProcesador+"\nVelocidad: "+pVelocidad+"\nRAM: "+pRam+"\nDisco duro: "+pDD+"\nDisco de estado: "+pDisco+"\nWindows Versión: "+pWinVersion+"\nIdioma del SO: "+pIdiomaSO+"\nMicrosoft Versión: "+pMicroVersion+"\nAntivirus: "+pAntivirus+"\nSoftware: "+pSoftware, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            bar_code.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void shareQRCode() {
        bar_code.setDrawingCacheEnabled(true);
        Bitmap bitmap = bar_code.getDrawingCache();
        File file = new File(Environment.getExternalStorageDirectory(), "bar_code.jpg");
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();

            Intent intent = new Intent(Intent.ACTION_SEND);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(InventarioScroll.this, "com.deshielo.inventarioo", file));
            }else{
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            }

            intent.setType("image/*");
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteProducto(String prodId) {
        DatabaseReference drProducto=FirebaseDatabase.getInstance().getReference("productos").child(prodId);
        drProducto.removeValue();
        Toast.makeText(this, "Producto eliminado.", Toast.LENGTH_LONG).show();
    }

    /////////////////////////////////
    private boolean updateProducto(String id,String usuario,String username,String hostname,String ubicacion,String marca,String modelo,String anoman,String numserie,String procesador,String velocidad,String ram,String dd,String disco,String windowsversion,String idiomaso,String microsoftversion,String antivirus,String software){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("productos").child(id);
        Producto producto= new Producto(id, usuario, username, hostname, ubicacion, marca, modelo, anoman, numserie, procesador, velocidad, ram, dd, disco, windowsversion, idiomaso, microsoftversion, antivirus, software);
        databaseReference.setValue(producto);
        Toast.makeText(this,"Producto actualizado.", Toast.LENGTH_LONG).show();
        return true;
    }
    ////////////////////////////////

    private boolean checkPermission(String permission){
        int result = ContextCompat.checkSelfPermission(InventarioScroll.this, permission);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    private void requestPermission(String permission, int code){
        if(ActivityCompat.shouldShowRequestPermissionRationale(InventarioScroll.this, permission)){

        }else{
            ActivityCompat.requestPermissions(InventarioScroll.this, new String[]{permission}, code);
        }
    }

}