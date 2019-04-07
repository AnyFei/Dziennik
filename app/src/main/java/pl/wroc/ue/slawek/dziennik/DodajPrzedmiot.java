package pl.wroc.ue.slawek.dziennik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DodajPrzedmiot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_przedmiot);

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        Button btn = (Button) findViewById(R.id.dodajPrzedmiotBtn);
        final EditText et = (EditText)findViewById(R.id.nazwaPrzedmiotuET);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.dodajPrzedmiot(et.getText().toString());
                et.setText("");
            }
        });
    }
}
