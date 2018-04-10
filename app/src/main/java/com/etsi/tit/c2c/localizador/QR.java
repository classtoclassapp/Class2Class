package com.etsi.tit.c2c.localizador;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.etsi.tit.c2c.MapsActivity;
import com.etsi.tit.c2c.R;
import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QR extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    //Variables
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        AulaUtils.setAr(true);

        //Permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }

        //View
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Scanner View
                    ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
                    mScannerView = new ZXingScannerView(this);
                    contentFrame.addView(mScannerView);
                    mScannerView.setResultHandler(this);
                    mScannerView.startCamera();

                } else {
                    Intent vuelta = new Intent(QR.this, PedirClase.class);
                    startActivity(vuelta);
                    finish();
                }
                return;
            }
        }
    }

    //AL PRESIONAR BACK BUTTON IR A MENU PRINCIPAL ************************************************/
    public void onBackPressed() {
        Intent form = new Intent(QR.this, PedirClase.class);
        startActivity(form);
    }

    //FUNCIÓN PARA CUANDO DAMOS HACIA ATRAS EN LA BARRA *******************************************/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //FUNCIONES PARA LA LECTURA *******************************************************************/

    @Override
    public void handleResult(Result result) {
        String localizadorOrigen = result.getText();
        Boolean comprobacion = AulaUtils.compruebaLocalizador(localizadorOrigen);
        if(comprobacion) {
            AulaUtils.setAulaOrigen(localizadorOrigen);
            AulaUtils.devuelveDirecciónAR();
            if(AulaUtils.getDireccion().equalsIgnoreCase("Mapas")) {
                Intent ar = new Intent(QR.this, MapsActivity.class);
                startActivity(ar);
            }
            else {
                Intent ar = new Intent(QR.this, AugmentedReality.class);
                startActivity(ar);
            }
        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error de lectura");
            builder.setMessage("El código que ha escaneado no se corresponde con ningún localizador válido" +
                    "de la escuela. Por favor, vuelva a intentarlo con un código válido");
            builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            mScannerView.resumeCameraPreview(this);
        }
    }
}