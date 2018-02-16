package hr.math.kviz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.math.kviz.models.Razina;

public class BirajRazinuActivity extends AppCompatActivity {

    DatabaseHandler db;
    List<Razina> razine = new ArrayList<>();

    int trenutnaRazina = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biraj_razinu);

        Button resetiraj = findViewById(R.id.resetirajButton);
        resetiraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(BirajRazinuActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Upozorenje");
                builder.setMessage("Ova akcija će izbrisati sav do sad postignuti rezultat. Želite li nastaviti?");
                builder.setPositiveButton("Nastavi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferenceHandler.saveInt(BirajRazinuActivity.this, "TRENUTNA_RAZINA", 0);
                        getDb().resetirajRezultate();
                        pokaziRezultate();
                    }
                });
                builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                });
                builder.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        pokaziRezultate();
    }

    private void pokaziRezultate(){
        razine.clear();
        razine.addAll(getDb().dohvatiSveRazine());
        trenutnaRazina = SharedPreferenceHandler.getInt(BirajRazinuActivity.this, "TRENUTNA_RAZINA");
        LinearLayout razineLL = findViewById(R.id.linearRazine);
        razineLL.removeAllViews();
        for(Razina item : razine){
            dodajViewZaRazinu(item, razineLL);
        }
    }

    private void dodajViewZaRazinu(final Razina razina, LinearLayout razineLL){
        View parentView = getLayoutInflater().inflate(R.layout.include_razina, razineLL, false);
        ImageView prvaZvjedica = parentView.findViewById(R.id.prvaZvjezdica);
        ImageView drugaZvjedica = parentView.findViewById(R.id.drugaZvjezdica);
        ImageView trecaZvjedica = parentView.findViewById(R.id.trecaZvjezdica);
        TextView razinaTv = parentView.findViewById(R.id.razinaLabel);

        int brojZvjezdica = 0;
        if(razina.vrijemeRjesenja != 0){
            int timeLeft = razina.vrijemeMax - razina.vrijemeRjesenja;
            if(timeLeft >= 0.3 * razina.vrijemeMax){
                brojZvjezdica = 3;
            }else if(timeLeft < 0.3 * razina.vrijemeMax && timeLeft >= 0.15 * razina.vrijemeMax){
                brojZvjezdica = 2;
            }else{
                brojZvjezdica = 1;
            }
        }

        ImageButton igraj = parentView.findViewById(R.id.buttonIgraj);
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klikNaRazinu(razina);
            }
        });
        if(razina.razina > trenutnaRazina + 1){
            parentView.setClickable(false);
            parentView.setBackgroundColor(Color.parseColor("#999E9E9E"));
            parentView.setAlpha(0.7f);
            razinaTv.setTextColor(Color.parseColor("#9E9E9E"));
            igraj.setVisibility(View.INVISIBLE);
        }else{
            if(brojZvjezdica >= 1){
                prvaZvjedica.setImageResource(R.drawable.zvjezdica_on);
            }
            if(brojZvjezdica >= 2) {
                drugaZvjedica.setImageResource(R.drawable.zvjezdica_on);
            }
            if(brojZvjezdica == 3){
                trecaZvjedica.setImageResource(R.drawable.zvjezdica_on);
            }
        }

        razinaTv.setText("Razina" + razina.razina);

        razineLL.addView(parentView);
    }

    private void klikNaRazinu(final Razina razina){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(BirajRazinuActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("Razina" + razina.razina);
        builder.setMessage("Odabrali ste razinu" + razina.razina + ". Da biste prešli razinu morate odgovoriti točno na " +
                        razina.brPotrebnihTocnih + " pitanja u vremenu od " + razina.vrijemeMax + " sekundi.");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(BirajRazinuActivity.this, IgrajKvizActivity.class);
                        intent.putExtra("RAZINA", razina);
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                });
        builder.show();
    }

    private DatabaseHandler getDb(){
        if(db == null){
            db = new DatabaseHandler(BirajRazinuActivity.this);
        }
        return db;
    }

}
