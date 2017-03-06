package ru.startandroid.programm_calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    boolean isResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int buttons[] = {R.id.btnCancel, R.id.btnOpenBracket, R.id.btnCloseBracket, R.id.btnDelete, R.id.btnZero,
                R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven,
                R.id.btnEight, R.id.btnNine, R.id.btnPoint, R.id.btnEqual, R.id.btnDivision, R.id.btnMultiplication,
                R.id.btnPlus, R.id.btnMinus};


        for (int i = 0; i < buttons.length; i++) {
            Button btn = (Button) findViewById(buttons[i]);
            btn.setOnClickListener(this);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et = (EditText) findViewById(R.id.editText1);
    }

    @Override
    public void onClick(View v) {
        String inputText = et.getText().toString();
        switch (v.getId()) {

            case R.id.btnZero:
                if(inputText.equals("0"))
                    return;
                else
                inputNumber("0");
                break;
            case R.id.btnOne:
                inputNumber("1");
                break;
            case R.id.btnTwo:
                inputNumber("2");
                break;
            case R.id.btnThree:
                inputNumber("3");
                break;
            case R.id.btnFour:
                inputNumber("4");
                break;
            case R.id.btnFive:
                inputNumber("5");
                break;
            case R.id.btnSix:
                inputNumber("6");
                break;
            case R.id.btnSeven:
                inputNumber("7");
                break;
            case R.id.btnEight:
                inputNumber("8");
                break;
            case R.id.btnNine:
                inputNumber("9");
                break;
            case R.id.btnOpenBracket:
                if (inputText.equals("0")){
                    et.setText("");
                    inputSymbol("(");
                }
                else if(isOperator(inputText.charAt(inputText.length() - 1)) || isTextDivZero(inputText)){
                    inputSymbol("(");
                }
                else
                    inputSymbol("*(");

                break;
            case R.id.btnCloseBracket:
                if(!isOperator(inputText.charAt(inputText.length() - 1)) && checkCloseBrackets(inputText))
                    inputSymbol(")");
                break;
            case R.id.btnPlus:
                if(isTextDivZero(inputText)){
                return;
                }
                if (!isOperator(inputText.charAt(inputText.length() - 1))) {
                    inputSymbol("+");
                }
                break;
            case R.id.btnMinus:
                if(isTextDivZero(inputText)){
                return;
                 }
                if (!isOperator(inputText.charAt(inputText.length() - 1)) || inputText.charAt(inputText.length() - 1) == '(') {
                    inputSymbol("-");
                }
                else if (inputText.equals("0")){
                    et.setText("-");
                }
                break;
            case R.id.btnMultiplication:
                if(isTextDivZero(inputText)){
                return;
                }
                if (!isOperator(inputText.charAt(inputText.length() - 1))) {
                    inputSymbol("*");
                }
                break;
            case R.id.btnDivision:
                if(isTextDivZero(inputText)){
                return;
                 }
                if (!isOperator(inputText.charAt(inputText.length() - 1))) {
                    inputSymbol("/");
                }
                break;
            case R.id.btnPoint:
                if(isTextDivZero(inputText)){
                return;
                }
                if (isOperator(inputText.charAt(inputText.length() - 1))) {
                    return;
                } else if (checkDote(inputText)) {
                    inputSymbol(".");
                }
                break;
            case R.id.btnEqual:

                if(isTextDivZero(inputText)){
                    et.setText("0");
                    return;
                }
                if(isOperator(inputText.charAt(inputText.length() - 1)) || checkBrackets(inputText)) {
                    Toast.makeText(this, "Неверное выражение", Toast.LENGTH_LONG).show();
                    return;
                }

                if(isOperator(inputText.charAt(inputText.length() - 1))) {
                    et.setText(inputText.substring(0, inputText.length() - 1));
                }
                inputText = et.getText().toString();
                boolean isVisible = false;
                Expression expression = new Expression(inputText);
                String result = String.valueOf(expression.calculate());
              //  et.selectAll();
                isResult = true;

                if(result.equals("NaN")){
                    et.setText("Деление на ноль");
                    return;
                }


                String stringAfterPoint = "";
                int positionPoint;
                for(positionPoint = 1; positionPoint < result.length(); positionPoint++){
                    if(result.charAt(positionPoint) == '.'){
                        stringAfterPoint = result.substring(positionPoint + 1);
                        break;
                    }
                }

                for(int positionNotZero = 0; positionNotZero < stringAfterPoint.length(); positionNotZero++){
                    if(stringAfterPoint.charAt(positionNotZero) != '0'){
                        et.setText(result);
                        isVisible = true;

                        break;
                    }
                }

                if (!isVisible) {
                    et.setText(result.substring(0, positionPoint));
                }


                break;
            case R.id.btnDelete:
                if(!isOperator(inputText.charAt(inputText.length() - 1))&& inputText.length() == 1 ||
                        isTextDivZero(inputText) || inputText.equals("(") || inputText.equals("-")){
                    et.setText("0");
                }
                else if( inputText.equals("0")){
                    return;
                }
                else
                    et.setText(inputText.substring(0, inputText.length() - 1));
                break;
            case R.id.btnCancel:
                et.setText("0");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
        return true;
    }
    public void inputNumber(String s){
        if(isResult) {
            et.setText("0");
            isResult = false;
        }
        if (et.getText().toString().equals("0") || isTextDivZero(et.getText().toString())){
            et.setText("");
            et.setText(et.getText().toString() + s);
            et.setSelection(et.getText().length());
        } else if (checkZero(et.getText().toString()) && et.getText().toString().length() > 1){
            int length = et.getText().toString().length();
            et.getText().delete(length - 1, length);
            et.setText(et.getText().toString() + s);
            et.setSelection(et.getText().length());
        }
        else if(et.getText().toString().charAt(et.getText().toString().length() - 1)== ')'){
            et.setText(et.getText().toString() + "*"+ s);
            et.setSelection(et.getText().length());
        } else {
            et.setText(et.getText().toString() + s);
            et.setSelection(et.getText().length());}

    }
    public void inputSymbol(String s){
        if(isResult) {
            isResult = false;
        }
       if( isTextDivZero(et.getText().toString())){
           et.setText("");
           et.setText(et.getText().toString() + s);
           et.setSelection(et.getText().length());
       }
        else{
        et.setText(et.getText().toString() + s);
        et.setSelection(et.getText().length());
    }}
    public boolean checkBrackets(String str) {
        boolean bool = true;
        char[] arr = str.toCharArray();
        int countOpenBrackets = 0;
        int countCloseBrackets = 0;
        for (int i = 0; i < str.length(); i++) {

            if (arr[i] == '(') {
                countOpenBrackets += 1;
            }
            if (arr[i] == ')') {
                countCloseBrackets += 1;
            }
            if (countOpenBrackets != countCloseBrackets){
                bool = true;
            }

            else
                bool = false;
        }
        return bool;
    }
    public boolean checkCloseBrackets(String str) {
        boolean bool = true;
        char[] arr = str.toCharArray();
        int countOpenBrackets = 0;
        int countCloseBrackets = 0;
        for (int i = 0; i < str.length(); i++) {

            if (arr[i] == '(') {
                countOpenBrackets += 1;
            }
            if (arr[i] == ')') {
                countCloseBrackets += 1;
            }
            if (countOpenBrackets > countCloseBrackets){
                bool = true;
            }
            else
                bool = false;
        }
        return bool;
    }


    boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/'|| c == '.' || c == '(';
    }

    public boolean checkDote(String str) {
        boolean bool = true;
        char[] arr = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (arr[i] == '/' || arr[i] == '*' || arr[i] == '-' || arr[i] == '+') {
                bool = true;
            }
            if (arr[i] == '.') {

                bool = false;
            }
        }
        return bool;
    }
    public static boolean checkZero(String str) {
        boolean bool = true;
        char[] arr = str.toCharArray();
        for (int i = 0; i < str.length()-1; i++) {
            if ((arr[i] == '/' || arr[i] == '*' || arr[i] == '-' || arr[i] == '+' || arr[i] == '(') && arr[i+1] == '0'){
                bool = true;
            }

            else bool = false;
        }
        return bool;
    }

    boolean isTextDivZero(String s) {
        if (s.equals("Деление на ноль")) {
            return true;
        }
        else
            return false;
    }


}
