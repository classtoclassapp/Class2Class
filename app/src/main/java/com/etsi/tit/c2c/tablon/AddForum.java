package com.etsi.tit.c2c.tablon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.etsi.tit.c2c.R;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

/**
 * Created by josel on 19/02/2018.
 */

public class AddForum extends AppCompatActivity {

    private EditText t1;
    private EditText t2;
    private EditText t3;
    private EditText t4;

    private String nombre;
    private String email;
    private String numero;
    private String anuncio;
    private Button submitButton;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_add);

        //ASIGNAR VARIABLES AL BOTON DE SUBIR
        Button submitButton = (Button) findViewById(R.id.submitButton);

        //PONER BOTON ATRAS EN TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**********************************************************************************************/
    /**                                 AL DARLE A PUBLICAR                                      **/
    /**********************************************************************************************/
    public void onSubmitButton(View view) {

        if (counter == 0) {
            counter++;
            t1 = (EditText) findViewById(R.id.usernameF);
            t2 = (EditText) findViewById(R.id.emailF);
            t3 = (EditText) findViewById(R.id.phoneF);
            t4 = (EditText) findViewById(R.id.announceF);
            nombre = t1.getText().toString();
            email = t2.getText().toString();
            numero = t3.getText().toString();
            anuncio = t4.getText().toString();

            /**************************************************************************************/
            /**                                 COMPROBAR RED Y OBTENER ANUNCIOS                 **/
            /**************************************************************************************/
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            //COMPROBAR CONEXION A LA RED Y SI HAS RELLENADO CAMPOS
            if (networkInfo != null && networkInfo.isConnected()) {
                if(nombre.equals("")||email.equals("")||numero.equals("")||anuncio.equals("")) {
                    counter = 0;
                    final Toast toast = Toast.makeText(getApplicationContext(), "ERROR: " +
                            "Debe rellenar todos los campos del formulario", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 2000);
                }
                else {
                    new MakeDataBaseConnection().execute(nombre, email, numero, anuncio);
                }
            }
            else {
                counter = 0;
                final Toast toast = Toast.makeText(getApplicationContext(), "ERROR: " +
                        "No dispone de conexión a Internet." +
                        " Conectese y vuelva a intentarlo", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 2000);
            }
            /**************************************************************************************/
            /**************************************************************************************/
        }
    }
    /**********************************************************************************************/
    /**********************************************************************************************/


    /**********************************************************************************************/
    /**                             COMPORTAMIENTO BOTONES PARA IR ATRAS                         **/
    /**********************************************************************************************/

    //AL PRESIONAR BACK BUTTON IR A MENU PRINCIPAL
    public void onBackPressed() {
        Intent form = new Intent(AddForum.this, Forum.class);
        startActivity(form);
    }

    //FUNCIÓN PARA CUANDO DAMOS HACIA ATRAS EN LA BARRA *******************************************/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    /**********************************************************************************************/
    /**********************************************************************************************/


    /**********************************************************************************************/
    /**                             CLASE ASINCRONA PARA LEER ANUNCIOS                           **/
    /**********************************************************************************************/
    private class MakeDataBaseConnection extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            //PUBLICAR ANUNCIO EN SEGUNDO PLANO
            return publicaAnuncio(urls);
        }
        // POST EJECUCION: MOSTRAMOS POPUP CON RESULTADO Y VOLVEMOS A FORO
        @Override
        protected void onPostExecute(String result) {
            final Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 2000);

            //RESET
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");

            //VOLVER A FORO
            Intent form = new Intent(AddForum.this, Forum.class);
            startActivity(form);
        }
    }
    /**********************************************************************************************/
    /**********************************************************************************************/



    /**********************************************************************************************/
    /**                               FUNCION PUBLICA ANUNCIOS                                   **/
    /**********************************************************************************************/
    private String publicaAnuncio(String[] url) {
        try {

            MongoClass mc = new MongoClass();
            MongoDatabase db = mc.createConnection();

            if (db!=null) {

                //OBTENCIÓN DE LA DB Y LA TABLA
                MongoCollection<Document> anuncios = db.getCollection("anuncios");

                //CREAR OBJETO
                Document ob1 = new Document();
                ob1.append("nombre",nombre);
                ob1.append("email",email);
                ob1.append("telefono",numero);
                ob1.append("anuncio",anuncio);

                //INSERTAR
                anuncios.insertOne(ob1);
                return "Anuncio Publicado";
            }
            else {
                return "ERROR: Base de datos no disponible. Intentelo en otro momento";
            }
        }
        catch (Exception e) {
            return "ERROR: Se ha producido un error al procesar la solicitud";
        }
    }
    /**********************************************************************************************/
    /**********************************************************************************************/
}




