package com.deshielo.inventarioo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registroscroll extends AppCompatActivity {

    EditText reg_usuario, reg_username, reg_hostname, reg_ubicacion, reg_marca, reg_modelo, reg_ano_m, reg_num_serie, reg_pro, reg_vel, reg_ram, reg_dd, reg_disco, reg_w_version;
    EditText reg_idioma_so, reg_microsoft_office_version, reg_antivirus, reg_software;
    Button btn_add_prod;



    DatabaseReference databaseProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroscroll);

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
        databaseProducto= FirebaseDatabase.getInstance().getReference("productos");




        reg_usuario=(EditText)findViewById(R.id.reg_usuario);
        reg_username=(EditText)findViewById(R.id.reg_username);
        reg_hostname=(EditText)findViewById(R.id.reg_hostname);
        reg_ubicacion=(EditText)findViewById(R.id.reg_ubicacion);
        reg_marca=(EditText)findViewById(R.id.reg_marca);
        reg_modelo=(EditText)findViewById(R.id.reg_modelo);
        reg_ano_m=(EditText)findViewById(R.id.reg_ano_m);
        reg_num_serie=(EditText)findViewById(R.id. reg_num_serie);
        reg_pro=(EditText)findViewById(R.id.reg_pro);
        reg_vel=(EditText)findViewById(R.id.reg_vel);
        reg_ram=(EditText)findViewById(R.id.reg_ram);
        reg_dd=(EditText)findViewById(R.id.reg_dd);
        reg_disco=(EditText)findViewById(R.id.reg_disco);
        reg_w_version=(EditText)findViewById(R.id.reg_w_version);
        reg_idioma_so=(EditText)findViewById(R.id.reg_idioma_so);
        reg_microsoft_office_version=(EditText)findViewById(R.id.reg_microsoft_office_version);
        reg_antivirus=(EditText)findViewById(R.id.reg_antivirus);
        reg_software=(EditText)findViewById(R.id.reg_software);

        btn_add_prod=(Button)findViewById(R.id.btn_add_prod);

        btn_add_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_prod();

            }
        });
    }
////////////////////////////////////////////////////////////////
    private void add_prod(){
        String usuario=reg_usuario.getText().toString().trim();
        String username=reg_username.getText().toString().trim();
        String hostname=reg_hostname.getText().toString().trim();
        String ubicacion=reg_ubicacion.getText().toString().trim();
        String marca=reg_marca.getText().toString().trim();
        String modelo=reg_modelo.getText().toString().trim();
        String anoman=reg_ano_m.getText().toString().trim();
        String numserie=reg_num_serie.getText().toString().trim();
        String procesador=reg_pro.getText().toString().trim();
        String velocidad=reg_vel.getText().toString().trim();
        String ram=reg_ram.getText().toString().trim();
        String dd=reg_dd.getText().toString().trim();
        String disco=reg_disco.getText().toString().trim();
        String windowsversion=reg_w_version.getText().toString().trim();
        String idiomaso=reg_idioma_so.getText().toString().trim();
        String microsoftversion=reg_microsoft_office_version.getText().toString().trim();
        String antivirus=reg_antivirus.getText().toString().trim();
        String software=reg_software.getText().toString().trim();
        if(!TextUtils.isEmpty(usuario)){
            String id=databaseProducto.push().getKey();
            Producto producto=new Producto(id, usuario, username, hostname, ubicacion, marca, modelo, anoman, numserie, procesador, velocidad, ram, dd, disco, windowsversion, idiomaso, microsoftversion, antivirus, software);
            databaseProducto.child(id).setValue(producto);
            Toast.makeText(this, "Producto agregado.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Advertencia: Alguno de los campos está vacío.", Toast.LENGTH_SHORT).show();
        }
    }
    ////////////////////////////////////////////////////////////////

}
