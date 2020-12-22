package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.AnyRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO add check
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String ZERO = "0", ONE = "1", TWO = "2", THREE = "3", FOUR = "4", FIVE = "5", SIX = "6",
            SEVEN = "7", EIGHT = "8", NINE = "9", PLUS = "+", MINUS = "-", MULTIPLY = "*",
            DIVIDE = "/", EQUAL = "=", DOT = ".", isDivideNull = "На нуль делить нельзя";

    private Pattern pattern;

    String operation = null;
    TextView textInput, textHistory;
    Button buttonNull, buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix,
            buttonSeven, buttonEight, buttonNine, commaButton, removeButton, clearButton,
            plusButton, minusButton, multiplyButton, divideButton, equalButton;
    Double number1 = null, number2 = null;
    Boolean isEqualResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pattern = Pattern.compile("(\\d*(\\.?)\\d*)?.(\\d*(\\.?)\\d*)");

        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        textInput = findViewById(R.id.textInput);
        textHistory = findViewById(R.id.textHistory);

        buttonNull = findViewById(R.id.buttonNull);
        buttonOne = findViewById(R.id.buttonOne);
        buttonTwo = findViewById(R.id.buttonTwo);
        buttonThree = findViewById(R.id.buttonThree);
        buttonFour = findViewById(R.id.buttonFour);
        buttonFive = findViewById(R.id.buttonFive);
        buttonSix = findViewById(R.id.buttonSix);
        buttonSeven = findViewById(R.id.buttonSeven);
        buttonEight = findViewById(R.id.buttonEight);
        buttonNine = findViewById(R.id.buttonNine);
        commaButton = findViewById(R.id.commaButton);
        removeButton = findViewById(R.id.removeButton);
        clearButton = findViewById(R.id.clearButton);
        plusButton = findViewById(R.id.plusButton);
        minusButton = findViewById(R.id.minusButton);
        multiplyButton = findViewById(R.id.multiplyButton);
        divideButton = findViewById(R.id.divideButton);
        equalButton = findViewById(R.id.equalButton);


        buttonNull.setOnClickListener(this);
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);
        commaButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);
        equalButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNull: putSymbol(ZERO); break;
            case R.id.buttonOne: putSymbol(ONE); break;
            case R.id.buttonTwo: putSymbol(TWO); break;
            case R.id.buttonThree: putSymbol(THREE); break;
            case R.id.buttonFour: putSymbol(FOUR); break;
            case R.id.buttonFive: putSymbol(FIVE); break;
            case R.id.buttonSix: putSymbol(SIX); break;
            case R.id.buttonSeven: putSymbol(SEVEN); break;
            case R.id.buttonEight: putSymbol(EIGHT); break;
            case R.id.buttonNine: putSymbol(NINE); break;
            case R.id.commaButton: putDot(); break;

            case R.id.removeButton: removeSymbol(); break;
            case R.id.clearButton: clearAll(); break;

            case R.id.plusButton: addOperation(PLUS); break;
            case R.id.minusButton: addOperation(MINUS); break;
            case R.id.multiplyButton: addOperation(MULTIPLY); break;
            case R.id.divideButton: addOperation(DIVIDE); break;
            case R.id.equalButton: equalResult(); break;
            default: break;
        }
    }

    private void addOperation(String operationType) {
        if (
                operation != null
                || textInput.getText().toString().equals(isDivideNull)
                || textHistory.getText().toString().equals("")
        ) return;

        if (textInput.getText().toString() != "") {
            Double saveRes = Double.parseDouble(textInput.getText().toString());
            clearAll();
            number1 = saveRes;
            String textToHistory = number1.toString();
            textHistory.setText(textToHistory);
        } else {
            number1 = Double.parseDouble(textHistory.getText().toString());
        }

        operation = operationType;

        putSymbol(operationType);
    }

    private void equalResult() {
        String result = null;

        number2 = Double.parseDouble(getSplitArrayFromHistory(operation));

        switch (operation) {
            case PLUS: result = addValue(number1, number2).toString(); break;
            case MINUS: result = minusValue(number1, number2).toString(); break;
            case MULTIPLY: result = multiplyValue(number1, number2).toString(); break;
            case DIVIDE:
                if (number2 == 0) { result = isDivideNull; }
                else {result = divideValue(number1, number2).toString();}
                break;
        }

        operation = null;
        isEqualResult = true;
        textInput.setText(result);
    }

    private Double addValue(Double x, Double y) {
        return x + y;
    }

    private Double minusValue(Double x, Double y) {
        return x - y;
    }

    private Double multiplyValue(Double x, Double y) {
        return x * y;
    }

    private Double divideValue(Double x, Double y) { return x / y; }

    private String getSplitArrayFromHistory(String regex) {
        String value = textHistory.getText().toString();
        Matcher mather = pattern.matcher(value);

        if (mather.find()) {
            return mather.group(3);
        } else {
            throw new ArithmeticException("Bad split");
        }
    }

    private void putSymbol(String symbol) {
        String result = textHistory.getText().toString() + symbol;
        textHistory.setText(result);
    }

    private void removeSymbol() {
        if(textHistory.getText().toString().equals("")) return;

        StringBuffer value = new StringBuffer(textHistory.getText().toString());
        Integer length = value.length();
        StringBuffer result = value.delete(length - 1, length);

        textHistory.setText(result.toString());
    }

//    TODO fix
    private void putDot() {
        Matcher m = Pattern.compile("(\\d*(\\.?)\\d*)?.(\\d*(\\.?)\\d*)").matcher(textHistory.getText().toString()); //.? - exclude no DOT and char

        if (m.find()) {
            String first = m.group(1);
            String second = m.group(3);

            String fDot = m.group(2);
            String sDot = m.group(4);

            if ((fDot.equals("") && operation == null) || (sDot.equals("") && operation != null)) {
                textHistory.setText(textHistory.getText() + ".");
            }
        }
    }

    private void clearInput(TextView input) {
        input.setText("");
    }

    private void clearAll() {
        clearInput(textInput);
        clearInput(textHistory);
        number1 = null;
        number2 = null;
        operation = null;
        isEqualResult = false;
    }
}
