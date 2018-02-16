package hr.math.kviz;

import java.util.ArrayList;
import java.util.List;

import hr.math.kviz.models.Kategorija;
import hr.math.kviz.models.Pitanje;
import hr.math.kviz.models.Razina;

public class ImportHandler {

    private static String[] KATEGORIJE = new String[]{"Sport", "Umjetnost", "Povijest", "Geografija", "Književnost", "Prirodne znanosti", "Kršćanstvo"};

    public static void dodajKategorije(DatabaseHandler dbHandler){
        dbHandler.brisiSveKategorije();
        List<Kategorija> kategorije = new ArrayList<>();
        for(int i = 0; i < KATEGORIJE.length; i++){
            Kategorija kategorija = new Kategorija();
            kategorija.ime = KATEGORIJE[i];
            kategorije.add(kategorija);
        }
        dbHandler.unesiKategorije(kategorije);
    }

    public static void dodajPitanja(DatabaseHandler dbHandler){
        dbHandler.brisiSvaPitanja();
        List<Pitanje> pitanja = new ArrayList<>();

        Pitanje pitanje1 = new Pitanje();
        pitanje1.idCat = 3;
        pitanje1.text = "Ovo je prvo pitanje iz povijesti";
        pitanje1.odgA = "Ovo je odgovor pod A";
        pitanje1.odgB = "Ovo je odgovor pod B";
        pitanje1.odgC = "Ovo je TOCAN odgovor pod C";
        pitanje1.odgD = "Ovo je odgovor pod D";
        pitanje1.odgTocan = 3;
        pitanje1.brPojavljivanja = 0;
        pitanja.add(pitanje1);

        Pitanje pitanje2 = new Pitanje();
        pitanje2.idCat = 1;
        pitanje2.text = "Ovo je drugo pitanje iz sporta";
        pitanje2.odgA = "Ovo je TOCAN odgovor pod A";
        pitanje2.odgB = "Ovo je odgovor pod B";
        pitanje2.odgC = "Ovo je odgovor pod C";
        pitanje2.odgD = "Ovo je odgovor pod D";
        pitanje2.odgTocan = 1;
        pitanje2.brPojavljivanja = 0;
        pitanja.add(pitanje2);

        Pitanje pitanje3 = new Pitanje();
        pitanje3.idCat = 2;
        pitanje3.text = "Ovo je trece pitanje iz sporta";
        pitanje3.odgA = "Ovo je odgovor pod A";
        pitanje3.odgB = "Ovo je odgovor pod B";
        pitanje3.odgC = "Ovo je odgovor pod C";
        pitanje3.odgD = "Ovo je TOCAN odgovor pod D";
        pitanje3.odgTocan = 4;
        pitanje3.brPojavljivanja = 0;
        pitanja.add(pitanje3);

        Pitanje pitanje4 = new Pitanje();
        pitanje4.idCat = 3;
        pitanje4.text = "Ovo je cetvrto pitanje iz kulture";
        pitanje4.odgA = "Ovo je odgovor pod A";
        pitanje4.odgB = "Ovo je odgovor pod B";
        pitanje4.odgC = "Ovo je odgovor pod C";
        pitanje4.odgD = "Ovo je TOCAN odgovor pod D";
        pitanje4.odgTocan = 4;
        pitanje4.brPojavljivanja = 0;
        pitanja.add(pitanje4);

        Pitanje pitanje5 = new Pitanje();
        pitanje5.idCat = 3;
        pitanje5.text = "Ovo je peto pitanje iz kulture";
        pitanje5.odgA = "Ovo je odgovor pod A";
        pitanje5.odgB = "Ovo je odgovor pod B";
        pitanje5.odgC = "Ovo je odgovor pod C";
        pitanje5.odgD = "Ovo je TOCAN odgovor pod D";
        pitanje5.odgTocan = 4;
        pitanje5.brPojavljivanja = 0;
        pitanja.add(pitanje5);

        Pitanje pitanje6 = new Pitanje();
        pitanje6.idCat = 3;
        pitanje6.text = "Ovo je sesto pitanje iz kulture";
        pitanje6.odgA = "Ovo je odgovor pod A";
        pitanje6.odgB = "Ovo je odgovor pod B";
        pitanje6.odgC = "Ovo je odgovor pod C";
        pitanje6.odgD = "Ovo je TOCAN odgovor pod D";
        pitanje6.odgTocan = 4;
        pitanje6.brPojavljivanja = 0;
        pitanja.add(pitanje6);

        Pitanje pitanje7 = new Pitanje();
        pitanje7.idCat = 3;
        pitanje7.text = "Ovo je sedmo pitanje iz kulture";
        pitanje7.odgA = "Ovo je odgovor pod A";
        pitanje7.odgB = "Ovo je odgovor pod B";
        pitanje7.odgC = "Ovo je odgovor pod C";
        pitanje7.odgD = "Ovo je TOCAN odgovor pod D";
        pitanje7.odgTocan = 4;
        pitanje7.brPojavljivanja = 0;
        pitanja.add(pitanje7);

        Pitanje pitanje8 = new Pitanje();
        pitanje8.idCat = 4;
        pitanje8.text = "Ovo je osmo pitanje iz zemljopisa";
        pitanje8.odgA = "ISTINA - TOCNO";
        pitanje8.odgB = "LAZ";
        pitanje8.odgC = "";
        pitanje8.odgD = "";
        pitanje8.odgTocan = 1;
        pitanje8.brPojavljivanja = 0;
        pitanja.add(pitanje8);

        Pitanje pitanje9 = new Pitanje();
        pitanje9.idCat = 2;
        pitanje9.text = "Ovo je deveto pitanje iz ______";
        pitanje9.odgA = "Ovo je odgovor pod A";
        pitanje9.odgB = "Ovo je odgovor pod B";
        pitanje9.odgC = "Ovo je odgovor pod C";
        pitanje9.odgD = "Ovo je TOCAN odgovor pod D";
        pitanje9.odgTocan = 4;
        pitanje9.brPojavljivanja = 0;
        pitanja.add(pitanje9);

        Pitanje pitanje10 = new Pitanje();
        pitanje10.idCat = 3;
        pitanje10.text = "Ovo je deseto pitanje iz poviesti";
        pitanje10.odgA = "Ovo je odgovor pod A";
        pitanje10.odgB = "Ovo je TOCAN odgovor pod B";
        pitanje10.odgC = "Ovo je odgovor pod C";
        pitanje10.odgD = "Ovo je odgovor pod D";
        pitanje10.odgTocan = 2;
        pitanje10.brPojavljivanja = 0;
        pitanja.add(pitanje10);

        Pitanje pitanje11 = new Pitanje();
        pitanje11.idCat = 1;
        pitanje11.text = "PITANJE";
        pitanje11.odgA = "Ovo je odgovor pod A";
        pitanje11.odgB = "Ovo je odgovor pod B";
        pitanje11.odgC = "Ovo je TOCAN odgovor pod C";
        pitanje11.odgD = "Ovo je odgovor pod D";
        pitanje11.odgTocan = 3;
        pitanje11.brPojavljivanja = 0;
        pitanja.add(pitanje11);

        Pitanje pitanje12 = new Pitanje();
        pitanje12.idCat = 2;
        pitanje12.text = "PITANJE";
        pitanje12.odgA = "Ovo je TOCAN odgovor pod A";
        pitanje12.odgB = "Ovo je odgovor pod B";
        pitanje12.odgC = "Ovo je odgovor pod C";
        pitanje12.odgD = "Ovo je odgovor pod D";
        pitanje12.odgTocan = 1;
        pitanje12.brPojavljivanja = 0;
        pitanja.add(pitanje12);

        Pitanje pitanje13 = new Pitanje();
        pitanje13.idCat = 2;
        pitanje13.text = "PITANJE";
        pitanje13.odgA = "Ovo je odgovor pod A";
        pitanje13.odgB = "Ovo je odgovor pod B";
        pitanje13.odgC = "Ovo je odgovor pod C";
        pitanje13.odgD = "Ovo je TOCAN odgovor pod D";
        pitanje13.odgTocan = 4;
        pitanje13.brPojavljivanja = 0;
        pitanja.add(pitanje13);

        dbHandler.unesiPitanja(pitanja);
    }

    public static void dodajRazine(DatabaseHandler dbHandler){
        dbHandler.brisiSveRazine();
        List<Razina> razine = new ArrayList<>();

        Razina razina1 = new Razina();
        razina1.razina = 1;
        razina1.brPokusaja = 0;
        razina1.brPotrebnihTocnih = 5;
        razina1.vrijemeRjesenja = 0;
        razina1.vrijemeMax = 60;
        razina1.oduzimanjeSekundaPoPogreski = 2;
        razine.add(razina1);

        Razina razina2 = new Razina();
        razina2.razina = 2;
        razina2.brPokusaja = 0;
        razina2.brPotrebnihTocnih = 5;
        razina2.vrijemeRjesenja = 0;
        razina2.vrijemeMax = 40;
        razina2.oduzimanjeSekundaPoPogreski = 5;
        razine.add(razina2);

        Razina razina3 = new Razina();
        razina3.razina = 3;
        razina3.brPokusaja = 0;
        razina3.brPotrebnihTocnih = 10;
        razina3.vrijemeRjesenja = 0;
        razina3.vrijemeMax = 60;
        razina3.oduzimanjeSekundaPoPogreski = 2;
        razine.add(razina3);

        Razina razina4 = new Razina();
        razina4.razina = 4;
        razina4.brPokusaja = 0;
        razina4.brPotrebnihTocnih = 10;
        razina4.vrijemeRjesenja = 0;
        razina4.vrijemeMax = 30;
        razina4.oduzimanjeSekundaPoPogreski = 5;
        razine.add(razina4);

        Razina razina5 = new Razina();
        razina5.razina = 5;
        razina5.brPokusaja = 0;
        razina5.brPotrebnihTocnih = 15;
        razina5.vrijemeRjesenja = 0;
        razina5.vrijemeMax = 60;
        razina5.oduzimanjeSekundaPoPogreski = 2;
        razine.add(razina5);

        dbHandler.unesiRazine(razine);
    }

}
