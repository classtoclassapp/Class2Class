package com.etsi.tit.c2c.tablon;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.etsi.tit.c2c.MainMenuActivity;
import com.etsi.tit.c2c.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Forum extends AppCompatActivity {

    //Variables
    List<Anuncio> listaAnuncios = new ArrayList<Anuncio>();
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        //PONER BOTON ATRAS EN TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //CONFIGURACIÓN DEL SWIPE PARA SOLO FUNCIONAR EN EL TOP
        listView = (ListView) findViewById(R.id.listView1);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                //
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                boolean enable = false;
                if(listView != null && listView.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
                swipeRefreshLayout.setEnabled(enable);
            }
        });

        //EJECUCION BOTON FLOAT
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(Forum.this, AddForum.class);
                startActivity(form);
            }
        });

       //COLORES
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);
        swipeRefreshLayout.setProgressViewOffset(false, 125 , 225);

        //FUNCION
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new GetData().execute();
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    final Toast toast = Toast.makeText(getApplicationContext(), "ERROR: " +
                            "No dispone de conexión a Internet. " +
                            "Conectese y vuelva a intentarlo", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 2000);
                }
            }
        });

        //COMPROBAR RED, OBTENER ANUNCIOS
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new GetData().execute();
        }
        else {
            final Toast toast = Toast.makeText(getApplicationContext(), "ERROR: No dispone de conexión a Internet. Conectese y vuelva a intentarlo", Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 2000);
        }
    }


    //AL PRESIONAR BACK BUTTON IR A MENU PRINCIPAL
    public void onBackPressed() {
        Intent form = new Intent(Forum.this, MainMenuActivity.class);
        startActivity(form);
    }

    //FUNCIÓN PARA CUANDO DAMOS HACIA ATRAS EN LA BARRA *******************************************/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*Clase Asíncrona*/
    public class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute (String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Anuncio>>(){}.getType();
            listaAnuncios = gson.fromJson(s,listType); // parse to List
            Collections.reverse(listaAnuncios);
            AnuncioAdapter adapter = new AnuncioAdapter(Forum.this, R.layout.item_forum, listaAnuncios);
            ListView listView1 = (ListView)findViewById(R.id.listView1);
            listView1.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            String urlString = null;

            RestAPI rest = new RestAPI();
            urlString = rest.getAddressCollection();
            stream = rest.GetHTTPData(urlString);
            return stream;
        }
    }
}
