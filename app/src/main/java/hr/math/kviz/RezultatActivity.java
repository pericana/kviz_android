package hr.math.kviz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import hr.math.kviz.models.Razina;

public class RezultatActivity extends AppCompatActivity {

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat);

        final Razina razina = (Razina) getIntent().getExtras().getSerializable("RAZINA");
        final int vrijeme = getIntent().getIntExtra("VRIJEME_RJESEVANJA", 0);

        final ImageButton pokusajPonovno = findViewById(R.id.ponovnoButton);
        final ImageButton dalje = findViewById(R.id.daljeButton);
        final ImageButton razine = findViewById(R.id.razineButton);

        int brojZvjezdica = 0;
        if(vrijeme > 0){
            ImageView prvaZvjezdicaOn = findViewById(R.id.prvaZvjezdicaOn);
            final ImageView drugaZvjezdicaOn = findViewById(R.id.drugaZvjezdicaOn);
            final ImageView trecaZvjezdicaOn = findViewById(R.id.trecaZvjezdicaOn);

            prvaZvjezdicaOn.setVisibility(View.VISIBLE);
            Animacije.povecaj(prvaZvjezdicaOn, 0f, 1f, 500);

            if(vrijeme != 0){
                int timeLeft = razina.vrijemeMax - vrijeme;
                if(timeLeft >= 0.3 * razina.vrijemeMax){
                    brojZvjezdica = 3;
                }else if(timeLeft < 0.3 * razina.vrijemeMax && timeLeft >= 0.15 * razina.vrijemeMax){
                    brojZvjezdica = 2;
                }else{
                    brojZvjezdica = 1;
                }
            }

            if(brojZvjezdica > 1){ //druga zvjezdica
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drugaZvjezdicaOn.setVisibility(View.VISIBLE);
                        Animacije.povecaj(drugaZvjezdicaOn, 0f, 1f, 500);
                    }
                }, 500);
            }

            if(brojZvjezdica > 2){ //treca zvjezdica
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trecaZvjezdicaOn.setVisibility(View.VISIBLE);
                        Animacije.povecaj(trecaZvjezdicaOn, 0f, 1f, 500);
                    }
                }, 1000);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                razine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                razine.setVisibility(View.VISIBLE);

                if(vrijeme == 0){
                    pokusajPonovno.setVisibility(View.VISIBLE);
                    pokusajPonovno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(RezultatActivity.this, IgrajKvizActivity.class);
                            intent.putExtra("RAZINA", razina);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else{
                    dalje.setVisibility(View.VISIBLE);
                    dalje.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Razina novaRazina = getDb().dohvatiSljedecuRazinu(razina);
                            if(novaRazina == null){
                                Toast.makeText(RezultatActivity.this, "Ovo je zadnja razina", Toast.LENGTH_SHORT).show();
                            }else{
                                AlertDialog.Builder builder;
                                builder = new AlertDialog.Builder(RezultatActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                                builder.setTitle("Razina" + novaRazina.razina);
                                builder.setMessage("Iduća razina je Razina" + novaRazina.razina + ". Da biste prešli razinu morate odgovoriti točno na " +
                                        novaRazina.brPotrebnihTocnih + " pitanja u vremenu od " + novaRazina.vrijemeMax + " sekundi.");
                                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RezultatActivity.this, IgrajKvizActivity.class);
                                        intent.putExtra("RAZINA", novaRazina);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {}
                                });
                                builder.show();
                            }
                        }
                    });
                }
            }
        }, brojZvjezdica * 500 + 500);

    }

    private DatabaseHandler getDb(){
        if(db == null){
            db = new DatabaseHandler(RezultatActivity.this);
        }
        return db;
    }

}
