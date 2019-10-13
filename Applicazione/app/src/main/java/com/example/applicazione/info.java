package com.example.applicazione;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Pianta pianta = Pianta.getPianta();
        Button irrigazione = findViewById(R.id.button3);

        irrigazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpGetRequest request = new HttpGetRequest();
                String url=""; //Insert server's url

                try {
                    request.execute(url + "RichiestaIrrigazione").get();
                    Toast.makeText(info.this, "Richiesta di irrigazione", Toast.LENGTH_LONG).show();

                }catch(ExecutionException e){
                    e.printStackTrace();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        ScrollView scroll = findViewById(R.id.scroll);
        TextView text = new TextView(this);
        text.setText(pianta.getDescrizione());
        TextView nome=findViewById(R.id.nome);
        nome.setText(pianta.getNome());

        scroll.addView(text);

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {

            @Override
            public void run(){

                TextView umid = findViewById(R.id.umidi);

                    String url=""; //Insert server's url
                    HttpGetRequest request1 = new HttpGetRequest();
                    HttpGetRequest request2 = new HttpGetRequest();

                    try {
                        request1.execute(url + "RichiestaMoisture").get();

                        String result;
                        result = request2.execute(url + "GradoEffettivo").get();

                        String t = 100 -(Integer.parseInt(result)*100)/1024 + "%";

                        umid.setText(t);

                    }catch(ExecutionException e){
                        e.printStackTrace();
                    }
                    catch(InterruptedException e) {
                        e.printStackTrace();
                    }

                handler.postDelayed(this, 2000);
            }
        };
        runnable.run();
        handler.post(runnable);

    }
}
