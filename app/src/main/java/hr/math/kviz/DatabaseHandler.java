package hr.math.kviz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hr.math.kviz.models.Kategorija;
import hr.math.kviz.models.Pitanje;
import hr.math.kviz.models.Razina;

public class DatabaseHandler {

    static final String DATABASE_NAME = "DB_KVIZ_20.db";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE_PITANJA = "PITANJA";
    static final String DATABASE_TABLE_RAZINE = "RAZINE";
    static final String DATABASE_TABLE_KATEGORIJE = "KATEGORIJE";

    static final String TABLE_PITANJA_KEY_ID = "ID";
    static final String TABLE_PITANJA_KEY_ID_CAT = "ID_CAT";
    static final String TABLE_PITANJA_KEY_TEXT = "TEXT";
    static final String TABLE_PITANJA_KEY_ODG_A = "ODG_A";
    static final String TABLE_PITANJA_KEY_ODG_B = "ODG_B";
    static final String TABLE_PITANJA_KEY_ODG_C = "ODG_C";
    static final String TABLE_PITANJA_KEY_ODG_D = "ODG_D";
    static final String TABLE_PITANJA_KEY_ODG_TOCAN = "ODG_TOCAN";
    static final String TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA = "BROJ_POJAVLJIVANJA";

    static final String TABLE_RAZINE_KEY_ID = "ID";
    static final String TABLE_RAZINE_KEY_RAZINA = "RAZINA";
    static final String TABLE_RAZINE_KEY_BR_POKUSAJA = "BR_POKUSAJA";
    static final String TABLE_RAZINE_KEY_VRIJEME_MAX = "VRIJEME_MAX";
    static final String TABLE_RAZINE_KEY_BR_POTREBNIH_TOCNIH = "BR_POTREBNIH_TOCNIH";
    static final String TABLE_RAZINE_KEY_ODUZIMANJE_SEKUNDA_PO_POGRESKI = "ODUZIMANJE_SEKUNDA_PO_POGRESKI";
    static final String TABLE_RAZINE_KEY_VRIJEME_RJESAVANJA = "VRIJEME_RJESAVANJA";

    static final String TABLE_KATEGORIJE_KEY_ID = "ID";
    static final String TABLE_KATEGORIJE_KEY_IME = "IME";

    static final String CREATE_PITANJA_TABLE = "CREATE TABLE " + DATABASE_TABLE_PITANJA + " (" +
            TABLE_PITANJA_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_PITANJA_KEY_ID_CAT + " INTEGER, " +
            TABLE_PITANJA_KEY_TEXT + " TEXT, " +
            TABLE_PITANJA_KEY_ODG_A + " TEXT, " +
            TABLE_PITANJA_KEY_ODG_B + " TEXT, " +
            TABLE_PITANJA_KEY_ODG_C + " TEXT, " +
            TABLE_PITANJA_KEY_ODG_D + " TEXT, " +
            TABLE_PITANJA_KEY_ODG_TOCAN + " INTEGER, " +
            TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA + " INTEGER);";

    static final String CREATE_RAZINE_TABLE = "CREATE TABLE " + DATABASE_TABLE_RAZINE + " (" +
            TABLE_RAZINE_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_RAZINE_KEY_RAZINA + " INTEGER, " +
            TABLE_RAZINE_KEY_BR_POKUSAJA + " INTEGER, " +
            TABLE_RAZINE_KEY_VRIJEME_MAX + " INTEGER, " +
            TABLE_RAZINE_KEY_BR_POTREBNIH_TOCNIH + " INTEGER, " +
            TABLE_RAZINE_KEY_ODUZIMANJE_SEKUNDA_PO_POGRESKI + " INTEGER, " +
            TABLE_RAZINE_KEY_VRIJEME_RJESAVANJA + " INTEGER);";

    static final String CREATE_KATEGORIJE_TABLE = "CREATE TABLE " + DATABASE_TABLE_KATEGORIJE + " (" +
            TABLE_KATEGORIJE_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_KATEGORIJE_KEY_IME + " TEXT);";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DatabaseHandler(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_PITANJA_TABLE);
                db.execSQL(CREATE_RAZINE_TABLE);
                db.execSQL(CREATE_KATEGORIJE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

    }

    //---opens the database---
    public DatabaseHandler open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    public void brisiSvaPitanja(){
        open();
        db.beginTransaction();
        db.delete(DATABASE_TABLE_PITANJA, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    public void brisiSveRazine(){
        open();
        db.beginTransaction();
        db.delete(DATABASE_TABLE_RAZINE, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    public void brisiSveKategorije(){
        open();
        db.beginTransaction();
        db.delete(DATABASE_TABLE_KATEGORIJE, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    public void unesiPitanja(List<Pitanje> pitanja){
        open();
        db.beginTransaction();
        for(Pitanje item : pitanja){
            unesiPitanje(item);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    private void unesiPitanje(Pitanje pitanje){
        ContentValues values = new ContentValues();
        values.put(TABLE_PITANJA_KEY_ID_CAT, pitanje.idCat);
        values.put(TABLE_PITANJA_KEY_TEXT, pitanje.text);
        values.put(TABLE_PITANJA_KEY_ODG_A, pitanje.odgA);
        values.put(TABLE_PITANJA_KEY_ODG_B, pitanje.odgB);
        values.put(TABLE_PITANJA_KEY_ODG_C, pitanje.odgC);
        values.put(TABLE_PITANJA_KEY_ODG_D, pitanje.odgD);
        values.put(TABLE_PITANJA_KEY_ODG_TOCAN, pitanje.odgTocan);
        values.put(TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA, pitanje.brPojavljivanja);

        db.insert(DATABASE_TABLE_PITANJA, null, values);
    }

    public void unesiRazine(List<Razina> razine){
        open();
        db.beginTransaction();
        for(Razina item : razine){
            unesiRazinu(item);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    private void unesiRazinu(Razina razina){
        ContentValues values = new ContentValues();
        values.put(TABLE_RAZINE_KEY_RAZINA, razina.razina);
        values.put(TABLE_RAZINE_KEY_BR_POKUSAJA, razina.brPokusaja);
        values.put(TABLE_RAZINE_KEY_VRIJEME_MAX, razina.vrijemeMax);
        values.put(TABLE_RAZINE_KEY_BR_POTREBNIH_TOCNIH, razina.brPotrebnihTocnih);
        values.put(TABLE_RAZINE_KEY_ODUZIMANJE_SEKUNDA_PO_POGRESKI, razina.oduzimanjeSekundaPoPogreski);
        values.put(TABLE_RAZINE_KEY_VRIJEME_RJESAVANJA, razina.vrijemeRjesenja);

        db.insert(DATABASE_TABLE_RAZINE, null, values);
    }

    public void unesiKategorije(List<Kategorija> kategorije){
        open();
        db.beginTransaction();
        for(Kategorija item : kategorije){
            unesiKategorija(item);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    private void unesiKategorija(Kategorija kategorija){
        ContentValues values = new ContentValues();
        values.put(TABLE_KATEGORIJE_KEY_IME, kategorija.ime);

        db.insert(DATABASE_TABLE_KATEGORIJE, null, values);
    }

    public List<Pitanje> dohvatiSvaPitanja(){
        List<Pitanje> lista = new ArrayList<>();
        open();
        Cursor cursor = db.query(DATABASE_TABLE_PITANJA, null, null, null, null, null, TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA);
        if (cursor.moveToFirst()){
            do {
                Pitanje pitanje = new Pitanje();
                pitanje.id = cursor.getLong(cursor.getColumnIndex(TABLE_PITANJA_KEY_ID));
                pitanje.idCat = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_ID_CAT));
                pitanje.text = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_TEXT));
                pitanje.odgA = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_A));
                pitanje.odgB = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_B));
                pitanje.odgC = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_C));
                pitanje.odgD = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_D));
                pitanje.odgTocan = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_TOCAN));
                pitanje.brPojavljivanja = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA));

                lista.add(pitanje);

            }while (cursor.moveToNext());
        }
        close();
        return lista;
    }

    public List<Pitanje> dohvatiPetPitanjaSNajmanjimBrojemPrikazivanja(){
        List<Pitanje> lista = new ArrayList<>();
        open();
        Cursor cursor = db.query(DATABASE_TABLE_PITANJA, null, null, null, null, null, TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA, "5");
        if (cursor.moveToFirst()){
            do {
                Pitanje pitanje = new Pitanje();
                pitanje.id = cursor.getLong(cursor.getColumnIndex(TABLE_PITANJA_KEY_ID));
                pitanje.idCat = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_ID_CAT));
                pitanje.text = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_TEXT));
                pitanje.odgA = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_A));
                pitanje.odgB = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_B));
                pitanje.odgC = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_C));
                pitanje.odgD = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_D));
                pitanje.odgTocan = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_TOCAN));
                pitanje.brPojavljivanja = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA));

                lista.add(pitanje);

            }while (cursor.moveToNext());
        }
        close();
        return lista;
    }

    public List<Pitanje> dohvatiPitanjaZaBrojPrikazivanja(int brojPrikazivanja){
        List<Pitanje> lista = new ArrayList<>();
        open();
        Cursor cursor = db.query(DATABASE_TABLE_PITANJA, null, TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA + " = ?", new String[]{String.valueOf(brojPrikazivanja)},
                null, null, null, "20");
        if (cursor.moveToFirst()){
            do {
                Pitanje pitanje = new Pitanje();
                pitanje.id = cursor.getLong(cursor.getColumnIndex(TABLE_PITANJA_KEY_ID));
                pitanje.idCat = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_ID_CAT));
                pitanje.text = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_TEXT));
                pitanje.odgA = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_A));
                pitanje.odgB = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_B));
                pitanje.odgC = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_C));
                pitanje.odgD = cursor.getString(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_D));
                pitanje.odgTocan = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_ODG_TOCAN));
                pitanje.brPojavljivanja = cursor.getInt(cursor.getColumnIndex(TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA));

                lista.add(pitanje);

            }while (cursor.moveToNext());
        }
        close();
        return lista;
    }

    public List<Razina> dohvatiSveRazine(){
        List<Razina> lista = new ArrayList<>();
        open();
        Cursor cursor = db.query(DATABASE_TABLE_RAZINE, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Razina razina = new Razina();
                razina.id = cursor.getLong(cursor.getColumnIndex(TABLE_RAZINE_KEY_ID));
                razina.razina = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_RAZINA));
                razina.brPokusaja = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_BR_POKUSAJA));
                razina.vrijemeMax = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_VRIJEME_MAX));
                razina.brPotrebnihTocnih = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_BR_POTREBNIH_TOCNIH));
                razina.oduzimanjeSekundaPoPogreski = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_ODUZIMANJE_SEKUNDA_PO_POGRESKI));
                razina.vrijemeRjesenja = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_VRIJEME_RJESAVANJA));

                lista.add(razina);

            }while (cursor.moveToNext());
        }
        close();
        return lista;
    }

    public List<Kategorija> dohvatiSveKategorije(){
        List<Kategorija> lista = new ArrayList<>();
        open();
        Cursor cursor = db.query(DATABASE_TABLE_KATEGORIJE, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Kategorija kategorija = new Kategorija();
                kategorija.id = cursor.getLong(cursor.getColumnIndex(TABLE_KATEGORIJE_KEY_ID));
                kategorija.ime = cursor.getString(cursor.getColumnIndex(TABLE_KATEGORIJE_KEY_IME));

                lista.add(kategorija);

            }while (cursor.moveToNext());
        }

        close();
        return lista;
    }

    public void promjeniRezultatRazine(Razina razina){
        open();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(TABLE_RAZINE_KEY_BR_POKUSAJA, razina.brPokusaja);
        values.put(TABLE_RAZINE_KEY_VRIJEME_RJESAVANJA, razina.vrijemeRjesenja);

        db.update(DATABASE_TABLE_RAZINE, values, TABLE_RAZINE_KEY_ID + " = ?", new String[]{String.valueOf(razina.id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    public void povecajBrojPojavljivanjaPitanja(List<Pitanje> pitanja){
        open();
        db.beginTransaction();
        for(Pitanje item : pitanja){
            item.brPojavljivanja++;
            ContentValues values = new ContentValues();
            values.put(TABLE_PITANJA_KEY_BROJ_POJAVLJIVANJA, item.brPojavljivanja);
            db.update(DATABASE_TABLE_PITANJA, values, TABLE_PITANJA_KEY_ID + " = ?", new String[]{String.valueOf(item.id)});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    public Razina dohvatiSljedecuRazinu(Razina proslaRazina){
        Razina razina = null;
        open();
        int novaRazina = proslaRazina.razina + 1;
        Cursor cursor = db.query(DATABASE_TABLE_RAZINE, null, TABLE_RAZINE_KEY_RAZINA + " = ?", new String[]{String.valueOf(novaRazina)},
                null, null, null, "1");
        if (cursor.moveToFirst()){
            razina = new Razina();
            razina.id = cursor.getLong(cursor.getColumnIndex(TABLE_RAZINE_KEY_ID));
            razina.razina = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_RAZINA));
            razina.brPokusaja = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_BR_POKUSAJA));
            razina.vrijemeMax = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_VRIJEME_MAX));
            razina.brPotrebnihTocnih = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_BR_POTREBNIH_TOCNIH));
            razina.oduzimanjeSekundaPoPogreski = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_ODUZIMANJE_SEKUNDA_PO_POGRESKI));
            razina.vrijemeRjesenja = cursor.getInt(cursor.getColumnIndex(TABLE_RAZINE_KEY_VRIJEME_RJESAVANJA));
        }
        close();
        return razina;
    }

    public void resetirajRezultate(){
        open();
        db.beginTransaction();
        db.execSQL("UPDATE " + DATABASE_TABLE_RAZINE + " SET " + TABLE_RAZINE_KEY_BR_POKUSAJA + " = 0, " + TABLE_RAZINE_KEY_VRIJEME_RJESAVANJA + " = 0");
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

}
