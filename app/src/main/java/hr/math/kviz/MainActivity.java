package hr.math.kviz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        provjeriZaDodavanjePitanja();

        ImageButton igraj = findViewById(R.id.igrajButton);
        igraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BirajRazinuActivity.class);
                startActivity(intent);
            }
        });

        ImageButton postavke = findViewById(R.id.postavkeButton);
        postavke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostavkeActivity.class);
                startActivity(intent);
            }
        });

    }

    private DatabaseHandler getDb(){
        if(db == null){
            db = new DatabaseHandler(MainActivity.this);
        }
        return db;
    }

    private void provjeriZaDodavanjePitanja(){
        if(!SharedPreferenceHandler.getBoolean(MainActivity.this, "PITANJA_SU_DODANA")){

            final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "","Pitanja se ucitavaju, molimo pričekajte", true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ImportHandler.dodajKategorije(getDb());
                    ImportHandler.dodajPitanja(getDb());
                    ImportHandler.dodajRazine(getDb());

                    SharedPreferenceHandler.saveBoolean(MainActivity.this, "PITANJA_SU_DODANA", true);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.hide();
                        }
                    });

                }
            }).start();

        }
    }

}
