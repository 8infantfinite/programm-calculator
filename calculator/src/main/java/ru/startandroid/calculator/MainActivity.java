package ru.startandroid.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnCancel, btnSkobka1, btnSkobka2, btnDelete, btnZero, btnOne, btnTwo, btnThree, btnFour,
            btnFive, btnSix, btnSeven, btnEight, btnNine, btnPoint, btnEqual, btnDelenie, btnUmnozhenie, btnPlus, btnMinus;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int button[] = {R.id.btnCancel, R.id.btnSkobka1, R.id.btnSkobka2, R.id.btnDelete, R.id.btnZero,
        R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven,
        R.id.btnEight, R.id.Nine, R.id.btnPoint, R.id.btnEqual, R.id.Delenie, R.id.btnUmnozhenie,
        R.id.btnPlus, R.id.btnMinus, R.id.btnA, R.id.btnB, R.id.btnC, R.id.btnD, R.id.bntE, R.id.btnF};

        for (int i = 0; i <button.length; i++) {
            findViewById(button[i]).setOnClickListener(this);
        }


        et = (EditText) findViewById(R.id.editText);
    }

}
