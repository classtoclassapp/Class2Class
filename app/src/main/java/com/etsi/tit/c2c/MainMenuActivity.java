package com.etsi.tit.c2c;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsi.tit.c2c.localizador.PedirClase;
import com.etsi.tit.c2c.tablon.Forum;

public class MainMenuActivity extends AppCompatActivity {
    private ImageView iv1;
    private TextView tv1;
    private Button botonForo;
    private Button botonClases;
    private Toolbar tb;
    private Animation myanimFade;
    private Animation myanimMove;
    private static int contador = 0;
    private static int posY = 0;
    private static int posX = 0;
    private static int posZ = 0;
    private static int posT = 0;
    private static ViewGroup.MarginLayoutParams  lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /*CAPTURAR BOTONES*************************************************************************/
        /*Barra inicio*/
        tb = (Toolbar) findViewById(R.id.toolbar);

        /*Texto e imagenes*/
        iv1 = (ImageView) findViewById(R.id.imageView2);
        tv1 = (TextView) findViewById(R.id.textView);

        /*Botones*/
        botonForo = (Button) findViewById(R.id.forum_button);
        botonClases = (Button) findViewById(R.id.class_button);

        /**INICIO ANIMACIONES**********************************************************************/
        if (contador == 0) {
            myanimFade = AnimationUtils.loadAnimation(this, R.anim.mytransition_fade);
            myanimMove = AnimationUtils.loadAnimation(this, R.anim.mytransition_movey);

            iv1.startAnimation(myanimMove);
            tv1.startAnimation(myanimFade);
            tb.startAnimation(myanimFade);
            botonClases.startAnimation(myanimFade);
            botonForo.startAnimation(myanimFade);

            myanimFade = AnimationUtils.loadAnimation(this, R.anim.mytransition_fade);
            myanimMove = AnimationUtils.loadAnimation(this, R.anim.mytransition_movey);

            iv1.startAnimation(myanimMove);
            tv1.startAnimation(myanimFade);
            tb.startAnimation(myanimFade);
            botonClases.startAnimation(myanimFade);
            botonForo.startAnimation(myanimFade);

            lp = (ViewGroup.MarginLayoutParams) iv1.getLayoutParams();
            posY = lp.topMargin;
            posX = lp.bottomMargin;
            posZ = lp.rightMargin;
            posT = lp.leftMargin;

            contador++;
        }
        else {
            lp.setMargins(posT, posY*0, posZ, posX );
            iv1.setLayoutParams(lp);
        }


        /*EJECUCION BOTONES************************************************************************/


        //Boton Localizador Clases
        botonClases.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ar = new Intent(MainMenuActivity.this, PedirClase.class);
                startActivity(ar);
            }
        });


        //Boton Tablon Social
        botonForo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent foro = new Intent(MainMenuActivity.this, Forum.class);
                startActivity(foro);
            }
        });
    }


    /*SALIDA DE LA APP ****************************************************************************/
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir");
        builder.setMessage("¿Desea salir de la aplicación?");

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}

