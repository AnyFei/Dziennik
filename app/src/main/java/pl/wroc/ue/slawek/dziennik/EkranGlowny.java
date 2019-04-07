package pl.wroc.ue.slawek.dziennik;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EkranGlowny extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button dodaj, pokaz,usun;
    EditText dodajET;
    DatabaseHelper dbHelper;
    String ktoraKlasaWybor;
    Spinner ktoraKlasaSpnr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekran_glowny);
        dbHelper = new DatabaseHelper(this);
        dodaj = (Button)findViewById(R.id.dodajBtn);
        pokaz = (Button)findViewById(R.id.pokazBtn);
        usun = (Button)findViewById(R.id.usunWszystko);
        //tutaj mowie, ze mam cos takiego jak spinner - czyli ta rozwijana lista

        //tutaj wpisujemy co ma sie wyswietlac w rozwijanej liscie, metoda ustawKlasy()
        //pobiera wszystkie klasy jakie sa w bazie
        //tutaj juz takie pierdoly co sie zawsze daje
         ktoraKlasaSpnr = (Spinner)findViewById(R.id.klasaSpnr);
        ArrayAdapter<String>adapter = new ArrayAdapter<>(EkranGlowny.this,
                android.R.layout.simple_spinner_item, ustawKlasy());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ktoraKlasaSpnr.setAdapter(adapter);
        ktoraKlasaSpnr.setOnItemSelectedListener(this);

        final RadioButton przedmiot = (RadioButton)findViewById(R.id.przedmiotRB);
        final RadioButton klasa = (RadioButton)findViewById(R.id.klasaRB);
        final RadioButton plan = (RadioButton)findViewById(R.id.planRB);
        final Intent[] intent = new Intent[1];
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sprawdzam ktora opcja jest zaznaczona, zaleznie od tego
                //przechodze do odpowiedniej aktywnosci
                if(przedmiot.isChecked()) {
                    intent[0] = new Intent(getBaseContext(), DodajPrzedmiot.class);
                    startActivity(intent[0]);
                }
                else if (klasa.isChecked()) {
                    intent[0] = new Intent(getBaseContext(), DodajKlase.class);
                    startActivity(intent[0]);
                }
                else if (plan.isChecked()) {
                    intent[0] = new Intent(getBaseContext(), DodajPlan.class);
                    startActivity(intent[0]);
                }
                else
                    //jak nic nie jest wybrane to wyswietla sie chmurka ze nic
                    Toast.makeText(getBaseContext(), "Nic", Toast.LENGTH_SHORT).show();

            }
        });
        pokaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //otwieram nowa aktywnosc i przekuje jest informacje, ktora klasa zostala wybrana
                Intent intent = new Intent(getBaseContext(), PokazPlan.class);
                intent.putExtra("WYBRANA_KLASA", ktoraKlasaWybor);
                startActivity(intent);
            }
        });
        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.usunWszystko(getBaseContext());
            }
        });

    }

    //te dwie metody musza byc ze wzgledu implementowany interfejs
    //obsluguja spinnera
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            //obslugujemy wybrana wartosc
            ktoraKlasaWybor = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
        public ArrayList<String> ustawKlasy(){ //pobieram klasy
            ArrayList<String> listaKlasWBazie = new ArrayList<>();
            Cursor data = dbHelper.pobierzKlasy();
            Log.v("kursor ", String.valueOf(data));
            while(data.moveToNext()){
                //data to wszystki pobrane dane
                //moveToNext przechodzi to nastepnego wiersza
                //wykonuje sie dopoki sa jakies wiersza
                //indeksy od getString to po prostu nr kolumn liczac od zera
                listaKlasWBazie.add(data.getString(0));


            }
            Log.v("info ", String.valueOf(listaKlasWBazie));
            if (listaKlasWBazie.isEmpty()) {
                //jak nie ma zadnych klas to dodaje wartosc brak klas i wylaczam mozliwosc klikania na to
                //jakbym nie dodal tego tekstu to wywalala by sie apka bo lista jest pusta a nie moze
                ktoraKlasaSpnr.setEnabled(false);
                listaKlasWBazie.add("brak klas");
            }
            return listaKlasWBazie; //zwracam liste wszystkich klas
        }
}

