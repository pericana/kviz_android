package hr.math.kviz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.math.kviz.models.Kategorija;
import hr.math.kviz.models.Pitanje;
import hr.math.kviz.models.Razina;

public class IgrajKvizActivity extends AppCompatActivity {

    DatabaseHandler db;
    Razina razina;
    CountDownTimer timer;
    TextView vrijemeTV;
    ProgressBar pbVrijeme;
    LinearLayout llZaKuglice;
    Button odgovorATV;
    Button odgovorBTV;
    Button odgovorCTV;
    Button odgovorDTV;
    ImageView kategorijaPitanja;

    List<Pitanje> pitanja = null;
    List<Pitanje> pitanjaZaPovecatBrojPrikazivanja = new ArrayList<>();
    Pitanje trenutnoPitanje = null;
    int protekloVrijeme = 0;
    boolean blokirajClick = true;
    int tocnihOdgovora = 0;
    int netocnihOdgovora = 0;
    int trenutniBrojPojavljivanja = 0;

    List<Long> zabranjeneKategorije = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igraj_kviz);
        razina = (Razina) getIntent().getExtras().getSerializable("RAZINA");
        setTitle("Razina " + razina.razina);

        vrijemeTV = findViewById(R.id.vrijemeTV);
        pbVrijeme = findViewById(R.id.progressVrijeme);
        llZaKuglice = findViewById(R.id.llZaKuglice);
        pbVrijeme.setMax(razina.vrijemeMax);

        odgovorATV = findViewById(R.id.odgovorA);
        odgovorBTV = findViewById(R.id.odgovorB);
        odgovorCTV = findViewById(R.id.odgovorC);
        odgovorDTV = findViewById(R.id.odgovorD);
        kategorijaPitanja = findViewById(R.id.kategorijaSlika);

        restartajTimer(razina.vrijemeMax);

        List<Kategorija> kategorije = getDb().dohvatiSveKategorije();
        for(Kategorija item : kategorije){
            boolean isDisabled = SharedPreferenceHandler.getBoolean(IgrajKvizActivity.this, "cat_" + item.id);
            if(isDisabled){
                zabranjeneKategorije.add(item.id);
            }
        }

        for(int i = 0; i < razina.brPotrebnihTocnih; i++){
            dodajKuglicu(i+1);
        }

        postaviPitanje();
    }

    private void dodajKuglicu(int pitanjeBroj){
        View parentView = getLayoutInflater().inflate(R.layout.include_kuglica, llZaKuglice, false);
        TextView tvKuglica = parentView.findViewById(R.id.tvKuglica);
        tvKuglica.setText(String.valueOf(pitanjeBroj));
        llZaKuglice.addView(parentView, 0);
    }

    private void restartajTimer(int vrijeme){
        if(timer != null){
            timer.cancel();
        }
        timer = new CountDownTimer(vrijeme * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                protekloVrijeme++;
                long preostaloVrijeme = millisUntilFinished / 1000;
                vrijemeTV.setText(String.valueOf(preostaloVrijeme));
                pbVrijeme.setProgress(protekloVrijeme);
            }

            @Override
            public void onFinish() {
                gotovo(false);
            }
        };
        timer.start();
    }

    private void postaviPitanje(){
        blokirajClick = false;
        trenutnoPitanje = izaberiPitanje();

        postaviSlikuKategorije(trenutnoPitanje.idCat);

        TextView pitanjeTV = findViewById(R.id.pitanjeTV);
        pitanjeTV.setText(trenutnoPitanje.text);

        if(TextUtils.isEmpty(trenutnoPitanje.odgC)){
            odgovorCTV.setVisibility(View.GONE);
        }else{
            odgovorCTV.setVisibility(View.VISIBLE);
        }

        if(TextUtils.isEmpty(trenutnoPitanje.odgD)){
            odgovorDTV.setVisibility(View.GONE);
        }else{
            odgovorDTV.setVisibility(View.VISIBLE);
        }

        odgovorATV.setText(trenutnoPitanje.odgA);
        odgovorBTV.setText(trenutnoPitanje.odgB);
        odgovorCTV.setText(trenutnoPitanje.odgC);
        odgovorDTV.setText(trenutnoPitanje.odgD);

        odgovorATV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klikNaOdgovor(1, v);
            }
        });
        odgovorBTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klikNaOdgovor(2, v);
            }
        });
        odgovorCTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klikNaOdgovor(3, v);
            }
        });
        odgovorDTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klikNaOdgovor(4, v);
            }
        });
    }

    private void postaviSlikuKategorije(int idKategorije){
        if(idKategorije == 1){
            kategorijaPitanja.setImageResource(R.drawable.sport_on);
        }else if(idKategorije == 2){
            kategorijaPitanja.setImageResource(R.drawable.umjetnost_on);
        }else if(idKategorije == 3){
            kategorijaPitanja.setImageResource(R.drawable.povijest_on);
        }else if(idKategorije == 4){
            kategorijaPitanja.setImageResource(R.drawable.geografija_on);
        }else if(idKategorije == 5){
            kategorijaPitanja.setImageResource(R.drawable.knjizevnost_on);
        }else if(idKategorije == 6){
            kategorijaPitanja.setImageResource(R.drawable.znanost_on);
        }else if(idKategorije == 7){
            kategorijaPitanja.setImageResource(R.drawable.krscanstvo_on);
        }
    }

    private void klikNaOdgovor(final int odgovor, final View view){
        if(blokirajClick){
            return;
        }
        blokirajClick = true;

        if(odgovor == trenutnoPitanje.odgTocan){
            tocnihOdgovora++;
            view.setBackgroundResource(R.drawable.odgovori_tocno);
            llZaKuglice.removeViewAt(0);
        }else{
            netocnihOdgovora++;
            view.setBackgroundResource(R.drawable.odgovori_krivo);
            if(trenutnoPitanje.odgTocan == 1){
                odgovorATV.setBackgroundResource(R.drawable.odgovori_tocno);
            }else if(trenutnoPitanje.odgTocan == 2){
                odgovorBTV.setBackgroundResource(R.drawable.odgovori_tocno);
            }else if(trenutnoPitanje.odgTocan == 3){
                odgovorCTV.setBackgroundResource(R.drawable.odgovori_tocno);
            }else if(trenutnoPitanje.odgTocan == 4){
                odgovorDTV.setBackgroundResource(R.drawable.odgovori_tocno);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                odgovorATV.setBackgroundResource(R.drawable.odgovori);
                odgovorBTV.setBackgroundResource(R.drawable.odgovori);
                odgovorCTV.setBackgroundResource(R.drawable.odgovori);
                odgovorDTV.setBackgroundResource(R.drawable.odgovori);
                if(tocnihOdgovora == razina.brPotrebnihTocnih){
                    gotovo(true);
                }else if(odgovor == trenutnoPitanje.odgTocan){
                    postaviPitanje();
                }else{
                    timer.cancel();
                    protekloVrijeme = protekloVrijeme + razina.oduzimanjeSekundaPoPogreski * netocnihOdgovora;
                    restartajTimer(razina.vrijemeMax - protekloVrijeme);
                    postaviPitanje();
                }
            }
        }, 1000);
    }

    private Pitanje izaberiPitanje(){
        if(pitanja == null){
            pitanja = new ArrayList<>();
            List<Pitanje> pitanjaBezFiltra = getDb().dohvatiPetPitanjaSNajmanjimBrojemPrikazivanja();
            trenutniBrojPojavljivanja = pitanjaBezFiltra.get(0).brPojavljivanja;
            pitanja.addAll(filtrirajKategorije(pitanjaBezFiltra));
        }else{
            if(pitanja.size() == 0 && pitanjaZaPovecatBrojPrikazivanja.size() > 0){
                getDb().povecajBrojPojavljivanjaPitanja(pitanjaZaPovecatBrojPrikazivanja);
                pitanjaZaPovecatBrojPrikazivanja.clear();
            }
        }

        if(pitanja.size() == 0){
            while(true){
                List<Pitanje> pitanjaBezFiltra = getDb().dohvatiPitanjaZaBrojPrikazivanja(trenutniBrojPojavljivanja);
                pitanja.addAll(filtrirajKategorije(pitanjaBezFiltra));
                if(pitanja.size() == 0){
                    trenutniBrojPojavljivanja++;
                }else{
                    break;
                }
            }
        }

        int random = new Random().nextInt(pitanja.size());
        Pitanje pitanje = pitanja.get(random);
        pitanja.remove(random);
        pitanjaZaPovecatBrojPrikazivanja.add(pitanje);
        return pitanje;
    }

    private List<Pitanje> filtrirajKategorije(List<Pitanje> pitanjaZaFilter){
        List<Pitanje> filtriranaPitanja = new ArrayList<>();
        for(Pitanje item : pitanjaZaFilter){
            if(!zabranjeneKategorije.contains(Long.valueOf(item.idCat))){
                filtriranaPitanja.add(item);
            }
        }
        return filtriranaPitanja;
    }

    private void gotovo(boolean prelazak){
        if(timer != null){
            timer.cancel();
        }
        getDb().povecajBrojPojavljivanjaPitanja(pitanjaZaPovecatBrojPrikazivanja);
        razina.brPokusaja++;
        Intent intent = new Intent(IgrajKvizActivity.this, RezultatActivity.class);
        if(prelazak){
            if(razina.vrijemeRjesenja == 0 || razina.vrijemeRjesenja > protekloVrijeme){
                razina.vrijemeRjesenja = protekloVrijeme;
            }
            int trenutnaRazina = SharedPreferenceHandler.getInt(IgrajKvizActivity.this, "TRENUTNA_RAZINA");
            if(trenutnaRazina < razina.razina){
                SharedPreferenceHandler.saveInt(IgrajKvizActivity.this, "TRENUTNA_RAZINA", razina.razina);
            }
            intent.putExtra("VRIJEME_RJESEVANJA", protekloVrijeme);
        }
        getDb().promjeniRezultatRazine(razina);
        intent.putExtra("RAZINA", razina);
        startActivity(intent);
        finish();
    }

    private DatabaseHandler getDb(){
        if(db == null){
            db = new DatabaseHandler(IgrajKvizActivity.this);
        }
        return db;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        if(timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }
}
