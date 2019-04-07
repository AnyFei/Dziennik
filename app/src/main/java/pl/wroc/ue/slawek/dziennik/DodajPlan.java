package pl.wroc.ue.slawek.dziennik;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DodajPlan extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ArrayList<String> listaKlasWBazie = new ArrayList<>();
    ArrayList<String> listaPrzedmiotowWBazie = new ArrayList<>();
    List<String> listaDni = Arrays.asList("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek");
DatabaseHelper dbHelper;
Spinner wyborKlasy, wyborPrzedmiotu, wyborDnia;
String klasaS, dzienS, przedmiotS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_plan);
        dbHelper = new DatabaseHelper(this);
        pobierzKlasyIPrzedmioty(); //zapełniam liste klas i przedmiotów danymi z bazy danych

        //tutaj analogicznie jak w EkranGlowny tylko tym razame mamy trzy spinnery
        wyborKlasy = (Spinner)findViewById(R.id.klasyPlanSpnr);
        ArrayAdapter<String> adapterKlasy = new ArrayAdapter<>(DodajPlan.this,
                android.R.layout.simple_spinner_item, listaKlasWBazie);
        adapterKlasy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wyborKlasy.setAdapter(adapterKlasy);
        wyborKlasy.setOnItemSelectedListener(this);

        wyborPrzedmiotu = (Spinner)findViewById(R.id.przedmiotyPlanSpnr);
        ArrayAdapter<String> adapterPrzedmioty = new ArrayAdapter<>(DodajPlan.this,
                android.R.layout.simple_spinner_item, listaPrzedmiotowWBazie);
        adapterPrzedmioty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wyborPrzedmiotu.setAdapter(adapterPrzedmioty);
        wyborPrzedmiotu.setOnItemSelectedListener(this);

        wyborDnia = (Spinner)findViewById(R.id.dniPlanSpnr);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DodajPlan.this,
                android.R.layout.simple_spinner_item, listaDni);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wyborDnia.setAdapter(adapter);
        wyborDnia.setOnItemSelectedListener(this);


        Button dodaj = (Button)findViewById(R.id.dodajPlanBtn);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.dodajPlan(klasaS, dzienS, przedmiotS);
                Toast.makeText(getBaseContext(), "Dodano: "+przedmiotS +
                        " dla klasy "+klasaS + " w "+dzienS, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //w zaleznosci od tego jaki spinner został wybrany przypisuje jego wartosc do odpowiedniej zmiennej
        switch (parent.getId()) {
            case R.id.klasyPlanSpnr:
                klasaS = parent.getItemAtPosition(pos).toString();
                break;
            case R.id.przedmiotyPlanSpnr:
                przedmiotS = parent.getItemAtPosition(pos).toString();
                break;
            case R.id.dniPlanSpnr:
                dzienS = parent.getItemAtPosition(pos).toString();
                break;
            default:
                break;
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void pobierzKlasyIPrzedmioty(){ //pobieram klasy
        Cursor klasyData = dbHelper.pobierzKlasy();
        while(klasyData.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listaKlasWBazie.add(klasyData.getString(0));

        }
        Log.v("info ", String.valueOf(listaKlasWBazie));
        if (listaKlasWBazie.isEmpty()) {
            // ktoraKlasaSpnr.setEnabled(false);
            listaKlasWBazie.add("brak klas");
        }

        Cursor przedmiotyData = dbHelper.pobierzPrzedmioty();
        while(przedmiotyData.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listaPrzedmiotowWBazie.add(przedmiotyData.getString(0));

        }
        Log.v("info ", String.valueOf(listaPrzedmiotowWBazie));
        if (listaPrzedmiotowWBazie.isEmpty()) {
            // ktoraKlasaSpnr.setEnabled(false);
            listaPrzedmiotowWBazie.add("brak przedmiotów");
        }
    }
}
