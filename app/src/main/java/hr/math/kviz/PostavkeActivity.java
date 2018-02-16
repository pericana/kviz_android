package hr.math.kviz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hr.math.kviz.models.Kategorija;

public class PostavkeActivity extends AppCompatActivity {

    DatabaseHandler db;
    ImageView sport;
    ImageView umjetnost;
    ImageView povijest;
    ImageView geografija;
    ImageView knjizevnost;
    ImageView znanosti;
    ImageView krscanstvo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postavke);

        sport = findViewById(R.id.sport);
        umjetnost = findViewById(R.id.umjetnost);
        povijest = findViewById(R.id.povijest);
        geografija = findViewById(R.id.geografija);
        knjizevnost = findViewById(R.id.knjizevnost);
        znanosti = findViewById(R.id.znanosti);
        krscanstvo = findViewById(R.id.krscanstvo);

        LinearLayout llKategorije = findViewById(R.id.llZaKetegorije);

        List<Kategorija> kategorije = getDb().dohvatiSveKategorije();

        for(Kategorija item : kategorije){
            boolean isDisabled = SharedPreferenceHandler.getBoolean(PostavkeActivity.this, "cat_" + item.id);
            if(isDisabled){
                napraviSliku(false, item);
            }
            napraviViewZaKategoriju(llKategorije, item, isDisabled);
        }

    }

    private void napraviViewZaKategoriju(LinearLayout llKategorije, final Kategorija kategorija, boolean isDisabled){
        View parentView = getLayoutInflater().inflate(R.layout.include_kategorija_postavke, llKategorije, false);
        TextView ime = parentView.findViewById(R.id.imeKategorije);
        ime.setText(kategorija.ime);
        SwitchCompat switchKategorije = parentView.findViewById(R.id.switchKategorije);
        if(isDisabled){
            switchKategorije.setChecked(false);
        }else{
            switchKategorije.setChecked(true);
        }
        switchKategorije.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferenceHandler.saveBoolean(PostavkeActivity.this, "cat_" + kategorija.id, !isChecked);
                napraviSliku(isChecked, kategorija);
            }
        });
        llKategorije.addView(parentView);
    }

    private void napraviSliku(boolean isOn, Kategorija kategorija){
        if(kategorija.ime.equals("Sport")){
            if(isOn){
                sport.setImageResource(R.drawable.sport_on);
            }else{
                sport.setImageResource(R.drawable.sport_off);
            }
        }else if(kategorija.ime.equals("Umjetnost")){
            if(isOn){
                umjetnost.setImageResource(R.drawable.umjetnost_on);
            }else{
                umjetnost.setImageResource(R.drawable.umjetnost_off);
            }
        }else if(kategorija.ime.equals("Povijest")){
            if(isOn){
                povijest.setImageResource(R.drawable.povijest_on);
            }else{
                povijest.setImageResource(R.drawable.povijest_off);
            }
        }else if(kategorija.ime.equals("Geografija")){
            if(isOn){
                geografija.setImageResource(R.drawable.geografija_on);
            }else{
                geografija.setImageResource(R.drawable.geografija_off);
            }
        }else if(kategorija.ime.equals("Književnost")){
            if(isOn){
                knjizevnost.setImageResource(R.drawable.knjizevnost_on);
            }else{
                knjizevnost.setImageResource(R.drawable.knjizevnost_off);
            }
        }else if(kategorija.ime.equals("Prirodne znanosti")){
            if(isOn){
                znanosti.setImageResource(R.drawable.znanost_on);
            }else{
                znanosti.setImageResource(R.drawable.znanost_off);
            }
        }else if(kategorija.ime.equals("Kršćanstvo")){
            if(isOn){
                krscanstvo.setImageResource(R.drawable.krscanstvo_on);
            }else{
                krscanstvo.setImageResource(R.drawable.krscanstvo_off);
            }
        }
    }

    private DatabaseHandler getDb(){
        if(db == null){
            db = new DatabaseHandler(PostavkeActivity.this);
        }
        return db;
    }

}
