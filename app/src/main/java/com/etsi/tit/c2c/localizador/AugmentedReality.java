package com.etsi.tit.c2c.localizador;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.etsi.tit.c2c.MapsActivity;
import com.etsi.tit.c2c.R;
import com.etsi.tit.c2c.tablon.AddForum;
import com.etsi.tit.c2c.tablon.Forum;

public class AugmentedReality extends AppCompatActivity {
    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_reality);

        /*Boton en la toolbar para ir hacia atras*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //EJECUCION BOTON FLOAT
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(AugmentedReality.this, MapsActivity.class);
                startActivity(form);
                myWebView.destroy();
            }
        });

        /*Generamos cliente de Chrome para webview*/
        myWebView = (WebView) this.findViewById(R.id.webView);
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        /*Configuración de WebView para JS y acceso a archivos*/
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        String url = "file:///android_asset/AR/examples/Index_"+AulaUtils.getDireccion()+"Arrow.html";
        myWebView.loadUrl(url);

        /*Vemos si tenemos permisos para camara y los solicitamos al usuario*/
        String permission = Manifest.permission.CAMERA;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    /*Función para cuando le damos al boton de atrás de la barra*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent form = new Intent(AugmentedReality.this, QR.class);
        startActivity(form);
        myWebView.destroy();
        finish();
    }

    /*Función que comprueba el resultado de la solicitud de permisos y da un mensaje*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"Permiso a cámara concendido. " +
                        "Lanzando aplicación de AR ", Toast.LENGTH_SHORT).show();
                // perform your action here

            } else {
                Toast.makeText(getApplicationContext(),"Permiso no concedido. " +
                        "Si quiere utilizar la función de AR, debe conceder " +
                        "permiso a la aplicación", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
