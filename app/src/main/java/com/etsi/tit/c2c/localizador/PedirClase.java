package com.etsi.tit.c2c.localizador;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.etsi.tit.c2c.MainMenuActivity;
import com.etsi.tit.c2c.R;

public class PedirClase extends AppCompatActivity {

    //Variables
    private EditText miCaja;       //textoAIntroducir
    private TextView miIntroducir; //textoArriba
    private Button botonIntro;     //boton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_clase);

        miCaja=(EditText)findViewById(R.id.aula);
        miIntroducir=(TextView)findViewById(R.id.textInput);
        botonIntro=(Button) findViewById(R.id.submitAula);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        botonIntro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String miAula=null;
                miAula=miCaja.getText().toString();
                boolean comp = AulaUtils.compruebaAula(miAula);
                if (comp) {
                    Intent botonIntro=new Intent(PedirClase.this,QR.class);
                    //Pasar datos
                    AulaUtils.setAulaDestino(miAula);
                    startActivity(botonIntro);
                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PedirClase.this);
                    builder.setTitle("Error de código");
                    builder.setMessage("El código que ha introducido no se corresponde con un aula " +
                            "válida, o bien no ha sido introducida todavía en la aplicación.\n\n" +
                            "Intentelo con otro código o bien espere a futuras actualizaciones.");
                    builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    //AL PRESIONAR BACK BUTTON IR A MENU PRINCIPAL ************************************************/
    public void onBackPressed() {
        Intent form = new Intent(PedirClase.this, MainMenuActivity.class);
        startActivity(form);
    }

    //FUNCIÓN PARA CUANDO DAMOS HACIA ATRAS EN LA BARRA *******************************************/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
