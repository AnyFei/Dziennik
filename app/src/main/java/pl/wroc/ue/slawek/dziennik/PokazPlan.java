package pl.wroc.ue.slawek.dziennik;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PokazPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokaz_plan);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        LinearLayout lrPon = (LinearLayout)findViewById(R.id.linear_pon);
        LinearLayout lrWt = (LinearLayout)findViewById(R.id.linear_wt);
        LinearLayout lrSr = (LinearLayout)findViewById(R.id.linear_sr);
        LinearLayout lrCzw = (LinearLayout)findViewById(R.id.linear_czw);
        LinearLayout lrPt = (LinearLayout)findViewById(R.id.linear_pt);

        //w pobierzPlan() musi byc podane do jakies klasy ma zostac pobrany
        //wartosc te przekazuje z poprzedniej aktywnosci poprzez intent.putExtra()
        Cursor data = dbHelper.pobierzPlan(getIntent().getStringExtra("WYBRANA_KLASA"));
        TextView tv;
        while(data.moveToNext()){
            //tutaj obslugujemy jeden wiersz pobrany z tabeli


            //dzienTygodnia to nazwa dnia pobrana z bazy
            String dzienTygodnia = data.getString(2).toLowerCase();
            //usuwam spacje na koncu jest byla
            dzienTygodnia = dzienTygodnia.replaceAll(" ", "");
            Log.v("switch", data.getString(2).toLowerCase());
            tv = new TextView(getBaseContext()); //tworze nowe textview
            tv.setText(data.getString(3)); //przypisuje mu tekst - nazwe przedmiotu
            switch (dzienTygodnia) {
                //zaleznie jaka wartosc przyjmuje DzienTygodnia
                //dodajemy tv do odpowiedniego layoutu
                //zobacz w xml - kazdy dzien ma swoj LinearLayout
                //do tego wszystkie zapakowane sa w jeden duzy linearlyout
                //to przez to, ze scrollview moze miec tylko jedno 'dziecko'
                //scrollview tak w ogole pozwala scrollowac, jakby np. wszystkie przedmioty
                //nie miescily sie na ekranie

            case "poniedziałek":
                lrPon.addView(tv);
                break;
            case "wtorek":
                lrWt.addView(tv);
                break;
                case "środa":
                lrSr.addView(tv);
                break;
            case "czwartek":
                lrCzw.addView(tv);
                break;
            case "piątek":
                lrPt.addView(tv);
                break;
                default:


            }


            }


        }

    }

