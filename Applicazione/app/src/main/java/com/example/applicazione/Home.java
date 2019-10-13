package com.example.applicazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Home extends AppCompatActivity {

    ListView listP;
    Button button;
    HttpGetRequest request;
    String url;
    String result;
    String []p;
    List<String> list;
    ArrayAdapter ad;
    String plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listP = findViewById(R.id.listPiante);


        button = findViewById(R.id.button2);

        request = new HttpGetRequest();
        url=""; //Insert server's url
        result = "";

        try {
            result = request.execute(url + "configurazione").get();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        result = result.replace(":null", " ");
        result = result.replace("{", "");
        result = result.replace("}", "");
        result = result.replace("\"", "");

        p = result.split(",");

        list = Arrays.asList(p);

        ad = new ArrayAdapter(Home.this, android.R.layout.simple_list_item_1, list);

        listP.setAdapter(ad);



        for(int i=0; i<2; i++) {
            Toast.makeText(Home.this, p[i], Toast.LENGTH_LONG).show();
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                launchMainActivity();
            }
        });

        listP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                plant= (String) adapterView.getItemAtPosition(pos);
                Toast.makeText(Home.this, plant, Toast.LENGTH_LONG).show();

                String r;
                HttpGetRequest req = new HttpGetRequest();

                try {
                    r = req.execute(url + "pianta" + plant).get();

                    r = r.replace("/n", "\n");

                    Pianta pianta = Pianta.getPianta();
                    pianta.setNome(plant);
                    pianta.setDescrizione(r);

                    launchInfoActivity();

                    //Toast.makeText(Home.this, pianta.getDescrizione(), Toast.LENGTH_LONG).show();

                }catch(ExecutionException e){
                    e.printStackTrace();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void launchMainActivity(){
        startActivity(new Intent(this, MainActivity.class));

    }

    private void launchInfoActivity(){
        startActivity(new Intent(this, info.class));

    }
}
