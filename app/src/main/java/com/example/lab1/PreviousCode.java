package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class PreviousCode extends AppCompatActivity implements View.OnClickListener {
    String operation, ZERO = "0", ONE = "1", TWO = "2", THREE = "3", FOUR = "4", FIVE = "5", SIX = "6",
            SEVEN = "7", EIGHT = "8", NINE = "9", PLUS = "+", MINUS = "-", MULTIPLY = "*",
            DIVIDE = "/", EQUAL = "=", COMMA = ",";
    TextView textInput, textHistory;
    Double number1 = null, number2 = null;
    long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInput = findViewById(R.id.textInput);
        textHistory = findViewById(R.id.textHistory);
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
            case R.id.commaButton: putSymbol(COMMA); break;

            case R.id.removeButton: clearInput(textInput); break;
            case R.id.clearButton: clearAll(); break;

            case R.id.plusButton: addAction(PLUS); break;
            case R.id.minusButton: addAction(MINUS); break;
            case R.id.multiplyButton: addAction(MULTIPLY); break;
            case R.id.divideButton: addAction(DIVIDE); break;
            case R.id.equalButton: equalValue(); break;
            default: break;
        }
    }

    private void addAction(String operationType) {
        Integer operationNumber = null;

        operation = operationType;

        switch (operation) {
            case "+": operationNumber = 1; break;
            case "-": operationNumber = 2; break;
            case "*": operationNumber = 3; break;
            case "/": operationNumber = 4; break;
        }

        if(number1 == null) { number1 = getNumber(); }
        else if(number2 == null) { number2 = getNumber(); }
        else { number1 = getNumber(); }

        addToHistory(operation);
        setResult(operationNumber);
    }

    private void equalValue() {
        String result;
        Boolean isInputEmpty = textInput.length() == 0;
        addToHistory(EQUAL);

        if (number1 == null && !isInputEmpty) {
            number1 = getNumber();
            result = number1.toString();
            textInput.setText(result);
        }
        else if(number2 == null && !isInputEmpty) {
            number2 = getNumber();
            addAction(operation);
        }
    }

    private Double getNumber() { return Double.parseDouble(textInput.getText().toString()); }

    private void setResult(Integer operationNumber){
        Double resultNumber = null;

        if (number2 != null) {
            switch (operationNumber) {
                case 1: resultNumber = number1 + number2; break;
                case 2: resultNumber = number1 - number2; break;
                case 3: resultNumber = number1 * number2; break;
                case 4: resultNumber = number1 / number2; break;
            }

            if (resultNumber != null) {
                String result = resultNumber.toString();
                textInput.setText(result);
            } else {
                clearInput(textInput);
            }
        } else {
            clearInput(textInput);
        }
    }

    private void addToHistory(String text) {
        String result = textHistory.getText().toString() + textInput.getText().toString() + text;
        textHistory.setText(result);
    }

    private void putSymbol(String symbol) {
        String result = textInput.getText().toString() + symbol;
        textInput.setText(result);
    }

    private boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_CANCEL:
                long totalTime = System.currentTimeMillis() - startTime;
                if (totalTime >= 3000) { return false; }
        }

        return true;
    }

    private void clearInput(TextView input) { input.setText(""); }

    private void clearAll() {
        clearInput(textInput);
        clearInput(textHistory);
        number1 = null;
        number2 = null;
        startTime = 0;
    }
}

