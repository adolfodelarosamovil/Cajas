package com.example.cajas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int numero_toques;
    private int n_cajas_tocadas;
    private static int color_negro;
    long tinicial = 0;
    int sumaTotal = 0;
    long tfinal;
    long tiempo;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        color_negro = getResources().getColor(R.color.BLACK);

        //TODO obtener las TextView de mi Layout
        FrameLayout elemento_padre = findViewById(R.id.caja_raiz);
        List<View> l_vistas = findViewsByType(elemento_padre, TextView.class);
        Log.d("MIAPP", "El número de TextView es " + l_vistas.size());

        //TODO cojo todas las cajas de texto, las recorro y a cada una le doy un valor numérico aleatorio

/*
        for (int i=0; i<l_vistas.size(); i++)
        {
            int numero = (int)(Math.random()*100+1);
            Log.d("MIAPP", "Numero " + numero);
            TextView vista = (TextView) l_vistas.get(i);
            vista.setText(String.valueOf(numero));
        }
*/

        TextView textView;
        int numero;

        for(View vista: l_vistas){
            if (!(vista instanceof Button)&& !(vista instanceof EditText)){
            //if (vista instanceof  ViewGroup) {
            //if(vista instanceof ViewGroup){
            numero = (int)(Math.random()*10+1);
            sumaTotal += numero;
            Log.d("MIAPP", "Numero " + numero);

                textView = (TextView) vista;

                textView.setText(String.valueOf(numero));

            }
        }
    }

    public void tocarCaja(View view) {
        Log.d("MIAPP", "Caja Tocada");

        //TODO cambiar el color a negro
        LinearLayout linearLayout = (LinearLayout) view;
        int negro = getResources().getColor(R.color.BLACK); //Obtengo el color y lo asigno
        linearLayout.setBackgroundColor(negro);
        //fin finish();
    }

    public void cajaTocada(View view) {
        Log.d("MIAPP", "Caja Tocada");
        numero_toques = numero_toques + 1;
        //numero_toques++;
        Log.d("MIAPP", "N toques = "+  numero_toques);
        LinearLayout linearLayout = (LinearLayout)view;//casting
        Drawable drawable = linearLayout.getBackground();//obtengo el fondo
        ColorDrawable colorDrawable = (ColorDrawable)drawable;//obtengo el color
        int color_negro = getResources().getColor(R.color.BLACK);
        int color = colorDrawable.getColor();//obtengo el valor color numérico
        if (color != color_negro)
        {
            Log.d("MIAPP", "Tocada por primera vez");
            linearLayout.setBackgroundColor(color_negro);//pongo negra la caja
            n_cajas_tocadas++;
            if (n_cajas_tocadas==8)
            {
                Log.d("MIAPP", "Se han tocado todas");
                finish();
            }

        } else {
            Log.d("MIAPP", "Ya se ha tocado la caja, no hago ná");
        }

    }


    public void tocarCaja2(View view) {

        //casting
        MiLinearLayout miLinearLayout = (MiLinearLayout)view;

        if(! miLinearLayout.isTocado()){
            miLinearLayout.setTocado(true);
            miLinearLayout.setBackgroundColor(color_negro);
            n_cajas_tocadas++;
            Log.d("MIAPP", "n_cajas_tocadas " + n_cajas_tocadas);
            if (n_cajas_tocadas==8)
            {
                Log.d("MIAPP", "Se han tocado todas");
                finish();
            }
        } else {
            Log.d("MIAPP", "Ya se ha tocado la caja, no hago ná");
        }
    }



    public void tocarCaja3(View view) {

        //casting
        MiLinearLayout miLinearLayout = (MiLinearLayout)view;

        if( miLinearLayout.getTag() == null){
            miLinearLayout.setTag(true);
            miLinearLayout.setBackgroundColor(color_negro);
            n_cajas_tocadas++;
            Log.d("MIAPP", "n_cajas_tocadas " + n_cajas_tocadas);
            if (n_cajas_tocadas==8)
            {
                Log.d("MIAPP", "Se han tocado todas");
                //tfinal = System.currentTimeMillis();

                //tiempo =  (tfinal - tinicial) / 1000 ;

                Toast toast = Toast.makeText(this, " Tardaste " + tiempo + " Segundos.", Toast.LENGTH_LONG);
                toast.show();

                finish();
            }
        } else {
            Log.d("MIAPP", "Ya se ha tocado la caja, no hago ná");
        }
    }

    public void pulsarButon(View view) {
        view.setVisibility(view.GONE);
        Toast toast = Toast.makeText(this, "JUEGO EMPEZADO", Toast.LENGTH_LONG);
        toast.show();

        tinicial = System.currentTimeMillis();
    }

    private List<View> findViewsByType (@NonNull ViewGroup vista_raiz, Class tipo_buscado)
    {
        List<View> lvistas = null;
        int nhijos = 0;
        ViewGroup vactual = null;
        View vistahija = null;

        List<ViewGroup> lista_vistas = new ArrayList<ViewGroup>();
        lista_vistas.add(vista_raiz);
        lvistas = new ArrayList<View>();

        for (int i = 0; i<lista_vistas.size(); i++)
        {
            vactual = lista_vistas.get(i);
            Log.d("MIAPP", "Mostrando " + vactual.getClass().getCanonicalName());
            nhijos = vactual.getChildCount();
            for (int j = 0;j<nhijos;j++ )
            {
                vistahija = vactual.getChildAt(j);
                if (tipo_buscado.isAssignableFrom(vistahija.getClass()))
                {
                    lvistas.add(vistahija);
                }
                if (vistahija instanceof  ViewGroup)
                {
                    lista_vistas.add((ViewGroup)vistahija);
                }
                else
                {
                    Log.d("MIAPP", "Mostrando " + vistahija.getClass().getCanonicalName());
                }
            }
        }
        return lvistas;
    }

    public void calcularSuma(View view) {
        Log.d("MIAPP", "EN CALCULAR SUMA");
        EditText resultado = findViewById(R.id.resultado);

        int resultadoInt = Integer.parseInt(String.valueOf(resultado.getText()));
        Log.d("MIAPP", "Suma Total " + sumaTotal);
        Log.d("MIAPP", "Resultado " + resultado);
        String mensaje;



        if( resultadoInt == sumaTotal){
            tfinal = System.currentTimeMillis();
            tiempo =  (tfinal - tinicial) / 1000 ;

            mensaje = "CORRECTO!!!! " + " Tardaste " + tiempo + " Segundos.";
            mediaPlayer = MediaPlayer.create(this, R.raw.correcto2);
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(1300, 1300);
            mediaPlayer.start();
            Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, VictoriaActivity.class);
            startActivity(intent);
            //finish();
        }else{
            mensaje = "Respuesta Incorrecta.";
            mediaPlayer = MediaPlayer.create(this, R.raw.incorrecto);
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(1300, 1300);
            mediaPlayer.start();
            Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
            toast.show();
        }
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.show();


    }


}
