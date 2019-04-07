package pl.wroc.ue.slawek.dziennik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DodajKlase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_klase);

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        Button btn = (Button) findViewById(R.id.dodajKlaseBtn);
        final EditText et = (EditText)findViewById(R.id.nazwaKlasyET);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //używam motody dodaj klasy z DataBaseHelper
                dbHelper.dodajKlase(et.getText().toString());
                et.setText(""); //po dodaniu pole robi sie puste, tak imo lepiej wyglada
            }
        });

        }
}
