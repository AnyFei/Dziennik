package pl.wroc.ue.slawek.dziennik;

import android.provider.BaseColumns;

public class BazaDanych implements BaseColumns {

//    Ta klasa to zwykły SQL, tutaj tworze polecenia tworzace baze danych
//            Poszczególny nazwy kolumn daje w zmiennych statycznych żeby ich poznie
//            uzywac w innych klasach

    public static final String BAZA_DANYCH = "slawek.db";

    //#####################################################
    //###############      TABELA PRZEDMIOTY     ##########
    //#####################################################

    public static final String NAZWA_PRZEDMIOTU = "nazwa_przedmiotu";
    public static final String TABELA_PRZEDMIOTY = "przedmioty";

    public static final String STWORZ_PRZEDMIOTY = "create table " + TABELA_PRZEDMIOTY
            +     " ("+       NAZWA_PRZEDMIOTU + " TEXT PRIMARY KEY)";

    //#####################################################
    //###############      TABELA KLASY     ###############
    //#####################################################

    public static final String NAZWA_KLASY = "klasa";
    public static final String TABELA_KLASY = "klasy";

    public static final String STWORZ_KLASY = "create table " + TABELA_KLASY
 + " ("+           NAZWA_KLASY + " TEXT PRIMARY KEY)";

    //#####################################################
    //###############      TABELA PLAN     ###############
    //#####################################################

    public static final String PLAN_KLASA = "klasa";
    public static final String PLAN_DZIEN = "dzien";
    public static final String PLAN_PRZEDMIOT= "przedmiot";
    public static final String TABELA_PLAN= "plan_zajec";


    public static final String STWORZ_PLAN = "create table " + TABELA_PLAN +
            " (ID_PLAN INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PLAN_KLASA + " TEXT, " +
            PLAN_DZIEN + " TEXT,  " +
            PLAN_PRZEDMIOT + " TEXT, " +
            "FOREIGN KEY("+PLAN_PRZEDMIOT+") REFERENCES "+TABELA_PRZEDMIOTY+"("+NAZWA_PRZEDMIOTU+"))" ;


}