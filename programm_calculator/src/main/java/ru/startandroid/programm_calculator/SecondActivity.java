package ru.startandroid.programm_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.math.BigInteger;
import java.util.LinkedList;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    EditText et;
    boolean isResult = false;
    int arrayBIN[] = { R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix,
            R.id.btnSeven, R.id.btnEight, R.id.btnNine, R.id.btnA, R.id.btnB,
            R.id.btnC, R.id.btnD, R.id.btnE, R.id.btnF};

    int arrayOCT[] = { R.id.btnEight, R.id.btnNine, R.id.btnA, R.id.btnB, R.id.btnC, R.id.btnD,
            R.id.btnE, R.id.btnF};

    int arrayDEC[] = {R.id.btnA, R.id.btnB, R.id.btnC, R.id.btnD, R.id.btnE, R.id.btnF};

    private int previousBase = 10;
    BigInteger resultInDec;
    boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);


        int buttons[] = {R.id.btnCancel, R.id.btnOpenBracket, R.id.btnCloseBracket, R.id.btnDelete,
                R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive,
                R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine, R.id.btnEqual, R.id.btnDivision,
                R.id.btnMultiplication, R.id.btnPlus, R.id.btnMinus, R.id.btnA, R.id.btnB, R.id.btnC,
                R.id.btnD, R.id.btnE, R.id.btnF};


        for (int i = 0; i < buttons.length; i++) {
            Button btn = (Button) findViewById(buttons[i]);
            btn.setOnClickListener(this);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et = (EditText) findViewById(R.id.editText1);
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.rGroup);
            rGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        String inputText = et.getText().toString();
        switch (v.getId()) {
            case R.id.btnZero:
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
            case R.id.btnPlus:
                if (!isOperator(inputText.charAt(inputText.length() - 1))) {
                    inputSymbol("+");
                }
                break;
            case R.id.btnMinus:
                if (!isOperator(inputText.charAt(inputText.length() - 1))) {
                    inputSymbol("-");
                }
                break;
            case R.id.btnMultiplication:
                if (!isOperator(inputText.charAt(inputText.length() - 1))) {
                    inputSymbol("*");
                }
                break;
            case R.id.btnDivision:
                if (!isOperator(inputText.charAt(inputText.length() - 1))) {
                    inputSymbol("/");
                }
                break;
            case R.id.btnOpenBracket:
                if (inputText.equals("0")){
                    et.setText("");
                    inputSymbol("(");
                }
                else if(isOperator(inputText.charAt(inputText.length() - 1))){
                    inputSymbol("(");
                }
                else
                    inputSymbol("*(");

                break;
            case R.id.btnCloseBracket:
                if(!isOperator(inputText.charAt(inputText.length() - 1)) && checkCloseBrackets(inputText))
                    inputSymbol(")");
                break;

            case R.id.btnEqual:

                if(isOperator(inputText.charAt(inputText.length() - 1)) || checkBrackets(inputText)) {
                    Toast.makeText(this, "Неверное выражение", Toast.LENGTH_LONG).show();
                    return;
                }
                if(checkMinus(inputText)){
                    Toast.makeText(this, "Отрицательное число", Toast.LENGTH_LONG).show();
                    et.setText("0");
                    return;
                }

                resultInDec = getResultInDec(inputText, previousBase);

                if(resultInDec.intValue()== 0 && isError){

                    Toast.makeText(this, "Деление на ноль", Toast.LENGTH_LONG).show();
                isError = false;}
                else{
                resultInDec.toString(previousBase);
                et.setText(resultInDec.toString(previousBase).toUpperCase());
                }
                isResult = true;
                et.setSelection(et.getText().length());
                break;
            case R.id.btnDelete:

                if(!isOperator(inputText.charAt(inputText.length() - 1))&& inputText.length() == 1
                        || inputText.equals("(")){
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
            case R.id.btnA:
                inputNumber("A");
                break;
            case R.id.btnB:
                inputNumber("B");
                break;
            case R.id.btnC:
                inputNumber("C");
                break;
            case R.id.btnD:
                inputNumber("D");
                break;
            case R.id.btnE:
                inputNumber("E");
                break;
            case R.id.btnF:
                inputNumber("F");
                break;
        }
    }
    public void inputSymbol(String s){
        if(isResult) {
            isResult = false;
        }
        et.setText( et.getText().toString() + s);
        et.setSelection(et.getText().length());
    }

    public void inputNumber(String s){
        if(isResult) {
            et.setText("0");
            isResult = false;
        }

        if (et.getText().toString().equals("0")){
            et.setText("");
            et.setText(et.getText().toString() + s);
            et.setSelection(et.getText().length());
        }
        else if (checkZero(et.getText().toString()) && et.getText().toString().length() > 1){
            int length = et.getText().toString().length();
            et.getText().delete(length - 1, length);
            et.setText(et.getText().toString() + s);
            et.setSelection(et.getText().length());
        }
        else if(et.getText().toString().charAt(et.getText().toString().length() - 1)== ')'){
        et.setText( et.getText().toString() + "*"+ s);
        et.setSelection(et.getText().length());
    }
        else{
            et.setText( et.getText().toString() + s);
            et.setSelection(et.getText().length());
        }
    }
    boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(';
    }

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
            else if(countOpenBrackets == 0 && countCloseBrackets == 0){
                bool = false;
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

    public boolean checkMinus(String str) {
        boolean bool = true;
        char[] arr = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {

            if (arr[0] == '-'){
                bool = true;
            }
            else
                bool = false;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    int levelOfOperation(char operation) {
        // Если символ * или / - приоритет 1
        if (operation == '*' || operation == '/') {
            return 1;
        }
        // Если символ + или - — приоритет 0
        else if (operation == '+' || operation == '-') {
            return 0;
        }
        // Если ни то, ни другое - приоритет -1
        else {
            return -1;
        }
    }


    boolean calculate(LinkedList<BigInteger> list, char operation) {
        // Инициализируем и объявляем две переменные
        // Первая берет последнее значение из переданного
        // связанного листа в параметре, запоминает и удаляет
        // его из списка
        BigInteger first = list.removeLast();

        BigInteger second = list.removeLast();

        switch (operation) {
            case '+':
                list.add(second.add(first));
                return true;
            case '-':
                list.add(second.subtract(first));
                return true;
            case '*':
                list.add(second.multiply(first));
                return true;
            case '/':
                try{
                    list.add(second.divide(first));
                    return true;
                }catch(ArithmeticException ex){
                    isError = true;
                    return false;
                }

        }
        return true;
    }
    BigInteger getResultInDec(String s, int baseFrom) {

        // Создаем два контейнера типа LinkedList
        // Один для чисел, другой для символов
        LinkedList<BigInteger> numbers = new LinkedList<>();
        LinkedList<Character> operations = new LinkedList<>();

        // Пишем цикл, который бегает по нашей строке
        for (int i = 0; i < s.length(); i++) {

            // Создаем локальную переменную типа символ,
            // чтобы было с чем делать сравнения и работать.
            // Присваиваем ей текущее положение i в строке
            char c = s.charAt(i);

            // Если натыкаемся на открывающуюся скобку
            if (c == '(') {

                // Добавляем открывающуюся скобку в контейнер
                // символов
                operations.add('(');

            }

            // Если натыкаемся на закрывающуюся скобку
           else if (c == ')') {

                // Смотрим - пока последний символ контейнера
                // символов не открывающаяся скобка -
                // Выполняем метод, который учит считать
                // программу, передавая ему в параметрах
                // наш контейнер с числами и последний
                // символ в контейнере символов, причем
                // удаляя его опосля
                while (operations.getLast() != '(') {
                    boolean isGood = calculate(numbers, operations.removeLast());
                    if (!isGood) {
                        return new BigInteger("0");
                    }
                }

                // После while - удаляем последний символ
                // из Символьного Контейнера. Если смотреть
                // пример - это открывающаяся скобка
                operations.removeLast();
            }

            // Так же, во время цикла мы проверяем каждый символ
            // на предмет - а не оператор ли он часом?
            // Если же да, то
            // ПОКА массив символов непустой и приоритет
            // последнего символа в контейнере символов
            // больше или равен приоритету текущего -
            // "учим" программу считать, передавая в параметрах
            // контейнер с числами и последний символ из
            // контейнера символов, удаляя его опосля
         else if (isOperator(c)) {
                while (!operations.isEmpty() &&
                        levelOfOperation(operations.getLast()) >= levelOfOperation(c)) {

                    boolean normalCalculate = calculate(numbers, operations.removeLast());
                    if (!normalCalculate) {
                        return new BigInteger("0");
                    }
                }

                // Если while не выполняется - добавляем
                // символ в контейнер символов
                operations.add(c);
            }

            // Если же ничего из вышеперечисленного не произошло,
            // то мы ожидаем число
            else if (Character.isDigit(c) || Character.isLetter(s.charAt(i))) {
                String operand = "";

                // После чего, ПОКА
                // текущее i меньше размера строки и
                // позиция от i в строке - число, -
                // мы составляем строку числа из символов,
                // увеличивая i на 1 каждый раз, когда символ
                // записался, чтобы проверять строку дальше

                while ((i < s.length() && Character.isDigit(s.charAt(i))) || Character.isLetter(s.charAt(i))) {
                    operand += s.charAt(i++);
                    if (i == s.length()) {
                        break;
                    }
                }

                // Если while не выполнился или закончился -
                // отнимаем у i единицу (т.к. i++ отработала
                // лишний раз, и добавляем нашу
                // распарсенную в числовой манер строку,
                // которую мы составили из чисел в
                // Числовой Контейнер
                --i;
            //    Log.d("operand", "" + operand);
                numbers.add(new BigInteger(operand, baseFrom));
         //       Log.d("BigInteger10", "" + new BigInteger(operand, baseFrom));
            }
        }

        // После цикла,
        // ПОКА контейнер символов НЕ пустой, -
        // "учим" считать программу, передавая ей наш контейнер
        // чисел и контейнер символов.
        while (!operations.isEmpty()) {
            boolean normalCalculate = calculate(numbers, operations.removeLast());
            if (!normalCalculate) {
                return new BigInteger("0");
            }
        }

        return numbers.get(0);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String inputText = et.getText().toString();

        switch (checkedId){
            case R.id.rbBIN:
                for (int i = 0; i <arrayBIN.length ; i++) {
                    Button btn = (Button) findViewById(arrayBIN[i]);
                    btn.setEnabled(false);
                }
                if(checkMinus(inputText)){
                    et.setText("0");
                    Toast.makeText(this, "Отрицательное число", Toast.LENGTH_LONG).show();
                    return;
                }

                if(isOperator(inputText.charAt(inputText.length() - 1)) || checkBrackets(inputText)) {
                    et.setText("0");
                    Toast.makeText(this, "Неверное выражение", Toast.LENGTH_LONG).show();
                    return;
                }
                if(isOperator(inputText.charAt(inputText.length() - 1))) {
                    et.setText(inputText.substring(0, inputText.length() - 1));
                }
                inputText = et.getText().toString();
                resultInDec = getResultInDec(inputText, previousBase);

                String resInBin = resultInDec.toString(2).toUpperCase();
                et.setText(resInBin);
                previousBase = 2;
                et.setSelection(et.getText().length());
                break;
            case R.id.rbOCT:
                for (int i = 0; i <arrayBIN.length ; i++) {
                    Button btn = (Button) findViewById(arrayBIN[i]);
                    btn.setEnabled(true);}
                for (int j = 0; j <arrayOCT.length ; j++) {
                    Button btn1 = (Button) findViewById(arrayOCT[j]);
                    btn1.setEnabled(false);
                }

                if(checkMinus(inputText)){
                    et.setText("0");
                    Toast.makeText(this, "Отрицательное число", Toast.LENGTH_LONG).show();
                    return;
                }

                if(isOperator(inputText.charAt(inputText.length() - 1)) || checkBrackets(inputText)) {
                    et.setText("0");
                    Toast.makeText(this, "Неверное выражение", Toast.LENGTH_LONG).show();
                    return;
                }

             if(isOperator(inputText.charAt(inputText.length() - 1))) {
             et.setText(inputText.substring(0, inputText.length() - 1));
                 }
                inputText = et.getText().toString();
                resultInDec = getResultInDec(inputText, previousBase);

                String resInOct = resultInDec.toString(8).toUpperCase();
                et.setText(resInOct);
                previousBase = 8;
                et.setSelection(et.getText().length());
                break;
            case R.id.rbDEC:

                for (int i = 0; i <arrayBIN.length ; i++) {
                    Button btn = (Button) findViewById(arrayBIN[i]);
                    btn.setEnabled(true);}
                for (int j = 0; j <arrayOCT.length ; j++) {
                    Button btn1 = (Button) findViewById(arrayOCT[j]);
                    btn1.setEnabled(true);
                }
                for (int k = 0; k <arrayDEC.length ; k++) {
                    Button btn3 = (Button) findViewById(arrayDEC[k]);
                    btn3.setEnabled(false);
                }

                if(checkMinus(inputText)){
                    et.setText("0");
                    Toast.makeText(this, "Отрицательное число", Toast.LENGTH_LONG).show();
                    return;
                }
                if(isOperator(inputText.charAt(inputText.length() - 1)) || checkBrackets(inputText)) {
                    et.setText("0");
                    Toast.makeText(this, "Неверное выражение", Toast.LENGTH_LONG).show();
                    return;
                }

                if(isOperator(inputText.charAt(inputText.length() - 1))) {
                    et.setText(inputText.substring(0, inputText.length() - 1));
                }
                inputText = et.getText().toString();
                resultInDec = getResultInDec(inputText, previousBase);

                String resultInDec1 = resultInDec.toString(10).toUpperCase();
                et.setText(resultInDec1);
                previousBase = 10;
                et.setSelection(et.getText().length());

                break;
            case R.id.rbHEX:

                for (int i = 0; i <arrayBIN.length ; i++) {
                    Button btn1 = (Button) findViewById(arrayBIN[i]);
                    btn1.setEnabled(true);}
                for (int j = 0; j <arrayOCT.length ; j++) {
                    Button btn2 = (Button) findViewById(arrayOCT[j]);
                    btn2.setEnabled(true);
                }
                for (int k = 0; k <arrayDEC.length ; k++) {
                    Button btn3 = (Button) findViewById(arrayDEC[k]);
                    btn3.setEnabled(true);
                }
                if(checkMinus(inputText)){
                    et.setText("0");
                    Toast.makeText(this, "Отрицательное число", Toast.LENGTH_LONG).show();
                    return;
                }

                if(isOperator(inputText.charAt(inputText.length() - 1)) || checkBrackets(inputText)) {
                    et.setText("0");
                    Toast.makeText(this, "Неверное выражение", Toast.LENGTH_LONG).show();
                    return;
                }

                if(isOperator(inputText.charAt(inputText.length() - 1))) {
                    et.setText(inputText.substring(0, inputText.length() - 1));
                }
                inputText = et.getText().toString();
                resultInDec = getResultInDec(inputText, previousBase);

                String resInHex = resultInDec.toString(16).toUpperCase();
                et.setText(resInHex);
                previousBase = 16;
                et.setSelection(et.getText().length());
                break;

        }

    }

}