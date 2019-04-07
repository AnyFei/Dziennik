package pl.wroc.ue.slawek.dziennik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import pl.wroc.ue.slawek.dziennik.BazaDanych;

/**
 * Created by User on 2/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    //Tutaj dzieje sie cała magia od SQLite

    public DatabaseHelper(Context context) {
        super(context, BazaDanych.BAZA_DANYCH, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tworze tabele na podstawie zapytan z BazaDanych
        //to sie wykonuje tylko jest nie istnieje jeszcze baza danych o nazwie podanej
        //w BazaDanych.BAZA_DANYCH
        db.execSQL(BazaDanych.STWORZ_PRZEDMIOTY);
        Log.v("stworzono", BazaDanych.STWORZ_PRZEDMIOTY);
        db.execSQL(BazaDanych.STWORZ_KLASY);
        Log.v("stworzono", BazaDanych.STWORZ_KLASY);
        db.execSQL(BazaDanych.STWORZ_PLAN);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        onCreate(db);
    }

    public boolean dodajPrzedmiot(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BazaDanych.NAZWA_PRZEDMIOTU, item); //dodaje item do tabeli NAZWA_PRZEDMIOTU

        Log.d(TAG, "addData: Adding " + item + " to " + BazaDanych.TABELA_PRZEDMIOTY);

        long result = db.insert(BazaDanych.TABELA_PRZEDMIOTY, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean dodajKlase(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BazaDanych.NAZWA_KLASY, item);

        Log.d(TAG, "addData: Adding " + item + " to " + BazaDanych.TABELA_KLASY);

        long result = db.insert(BazaDanych.TABELA_KLASY, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean dodajPlan(String klasa, String dzien, String przedmiot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BazaDanych.PLAN_KLASA, klasa);
        contentValues.put(BazaDanych.PLAN_DZIEN, dzien);
        contentValues.put(BazaDanych.PLAN_PRZEDMIOT, przedmiot);
        //tutaj dodaje kilka wartosci
        //przy .put pierwsza to zawsze nazwa tabeli a druga to wartosc

        Log.d(TAG, "addData: Adding " + klasa+" "+dzien+" "+przedmiot + " to " + BazaDanych.TABELA_PLAN);


        //tutaj wszystkie .put się wykonują
        long result = db.insert(BazaDanych.TABELA_PLAN, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Returns all the data from database
     * @return
     */
    public Cursor pobierzPlan(String wybranaKlasa){
        //zwykły SELECT SQLowy
        //dane pobierane sa do obiektu typu cursor
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + BazaDanych.TABELA_PLAN + " WHERE " + BazaDanych.PLAN_KLASA + " = '"+ wybranaKlasa+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor pobierzKlasy(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + BazaDanych.TABELA_KLASY;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor pobierzPrzedmioty(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + BazaDanych.TABELA_PRZEDMIOTY;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //a to usuwamy cala baze danych
    public void usunWszystko(Context context){
        context.deleteDatabase(BazaDanych.BAZA_DANYCH);
    }
}























