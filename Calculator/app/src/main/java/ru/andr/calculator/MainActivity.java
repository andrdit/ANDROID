package ru.andr.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private TextView mTextViewExample;

    private StringBuilder mCurrentTextResult;
    private StringBuilder mExample;
    private ArrayList<String> mExampleArrayList;

    private boolean mIsNightMode;
    View.OnClickListener buttonsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_board_layout);

        mIsNightMode = getIsNightMode();

        onOffNightMode(mIsNightMode);

        mCurrentTextResult = new StringBuilder();
        mCurrentTextResult.append("");

        mExample = new StringBuilder();
        mExample.append("");

        mExampleArrayList = new ArrayList<>();

        init();
    }

    private void init() {

        mTextViewResult = findViewById(R.id.textViewResult);
        mTextViewExample = findViewById(R.id.textViewExample);

        buttonsListener = (View v) -> {
            switch (v.getId()) {
                case R.id.button0: {
                    if (mCurrentTextResult.length() == 0) {
                        return;
                    }
                    if (mCurrentTextResult.length() == 1 && mCurrentTextResult.toString().contains("0")) {
                        return;
                    }

                    if(isLastSymbolSignEquel()){
                        pressedPoint();
                        return;
                    }
                    mCurrentTextResult.append("0");
                    mTextViewResult.setText(mCurrentTextResult.toString());
                    break;
                }
                case R.id.button1: {
                    pressedNumber("1");
                    break;
                }
                case R.id.button2: {
                    pressedNumber("2");
                    break;
                }
                case R.id.button3: {
                    pressedNumber("3");
                    break;
                }
                case R.id.button4: {
                    pressedNumber("4");
                    break;
                }
                case R.id.button5: {
                    pressedNumber("5");
                    break;
                }
                case R.id.button6: {
                    pressedNumber("6");
                    break;
                }
                case R.id.button7: {
                    pressedNumber("7");
                    break;
                }
                case R.id.button8: {
                    pressedNumber("8");
                    break;
                }
                case R.id.button9: {
                    pressedNumber("9");
                    break;
                }
                case R.id.buttonBackSpace: {
                    if (mCurrentTextResult.length() == 0) {
                        return;
                    }
                    mCurrentTextResult.deleteCharAt(mCurrentTextResult.length() - 1);
                    mTextViewResult.setText(mCurrentTextResult.toString());
                    break;
                }
                case R.id.buttonPt: {
                    pressedPoint();
                    break;
                }
                case R.id.buttonDivide: {
                    pressedOperator("/");
                    break;
                }
                case R.id.buttonMultiply: {
                    pressedOperator("*");
                    break;
                }
                case R.id.buttonMinus: {
                    pressedOperator("-");
                    break;
                }
                case R.id.buttonPlus: {
                    pressedOperator("+");
                    break;
                }
                case R.id.buttonEquel: {
                    if (mCurrentTextResult.length() == 0) {
                        return;
                    }

                    if (isLastSymbolOperand() || isLastSymbolSignEquel()) {
                        return;
                    }

                    setTextExamle("");

                    // get postfix string
                    CustomStack customStack = new CustomStack(mExampleArrayList.size());

                    String postfixString = infixToPostfix(customStack);
                    mTextViewResult.setText(postfixString);

                    //get result
                    String[] operandWithOperators = postfixString.split(";");

                    mTextViewResult.setText(postfixString);

                    Stack<String> resultStack = new Stack<>();

                    String element;
                    String operand1;
                    String operand2;
                    String answer = "";

                    for (int i = 0; i < operandWithOperators.length; i++) {

                        element = operandWithOperators[i];

                        if (!element.equals("%") && !element.equals("/") && !element.equals("*") && !element.equals("-") && !element.equals("+")) {
                            resultStack.push(element);
                        } else // Если это оператор
                        {
                            operand2 = resultStack.pop(); // Извлечение операндов
                            operand1 = resultStack.pop();

                            switch (element) // Выполнение арифметической
                            { // операции
                                case "+":
                                    answer = getSum(operand1, operand2);
                                    break;
                                case "-":
                                    answer = getMinus(operand1, operand2);
                                    break;
                                case "*":
                                    answer = getMultiply(operand1, operand2);
                                    break;
                                case "/":
                                    answer = getDivide(operand1, operand2);
                                    break;
                                default:
                                    answer = "0";
                            }
                            resultStack.push(answer); // Занесение промежуточного
                        }
                    }
                    answer = resultStack.pop();
                    mTextViewResult.setText(answer);
                    mExample.append("=");
                    mTextViewExample.setText(mExample.toString());
                    mCurrentTextResult.append(answer);
                    break;
                }
                case R.id.buttonClear: {
                    prepareForNewCalculation();
                    break;
                }
                case R.id.btnSettings: {
                    Intent intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                    break;
                }

                default: {
                    break;
                }
            }
        };

        findViewById(R.id.button0).setOnClickListener(buttonsListener);
        findViewById(R.id.button1).setOnClickListener(buttonsListener);
        findViewById(R.id.button2).setOnClickListener(buttonsListener);
        findViewById(R.id.button3).setOnClickListener(buttonsListener);
        findViewById(R.id.button4).setOnClickListener(buttonsListener);
        findViewById(R.id.button5).setOnClickListener(buttonsListener);
        findViewById(R.id.button6).setOnClickListener(buttonsListener);
        findViewById(R.id.button7).setOnClickListener(buttonsListener);
        findViewById(R.id.button8).setOnClickListener(buttonsListener);
        findViewById(R.id.button9).setOnClickListener(buttonsListener);
        findViewById(R.id.buttonClear).setOnClickListener(buttonsListener);
        findViewById(R.id.buttonBackSpace).setOnClickListener(buttonsListener);
        findViewById(R.id.buttonDivide).setOnClickListener(buttonsListener);
        findViewById(R.id.buttonMultiply).setOnClickListener(buttonsListener);
        findViewById(R.id.buttonMinus).setOnClickListener(buttonsListener);
        findViewById(R.id.buttonPlus).setOnClickListener(buttonsListener);
        findViewById(R.id.buttonEquel).setOnClickListener(buttonsListener);
        findViewById(R.id.buttonPt).setOnClickListener(buttonsListener);
        findViewById(R.id.btnSettings).setOnClickListener(buttonsListener);

    }

    private void pressedPoint() {
        if(isLastSymbolSignEquel()){
            prepareForNewCalculation();
        }

        if (mCurrentTextResult.toString().contains(".")) {
            return;
        }

        if (mCurrentTextResult.length() == 0) {
            mCurrentTextResult.append("0");
        }

        if (isLastSymbolOperand()) {
            return;
        }

        mCurrentTextResult.append(".");
        mTextViewResult.setText(mCurrentTextResult.toString());
    }

    private void pressedOperator(String operator) {
        if (mCurrentTextResult.length() == 0) {
            return;
        }

        if (isLastSymbolOperand()) {
            return;
        }

        if(isLastSymbolSignEquel()){
            continueCalculation();
            mExample.append(operator);
            mExampleArrayList.add(operator);
            return;
        }

        setTextExamle(operator);
    }

    private void pressedNumber(String number) {
        if(isLastSymbolSignEquel()){
            prepareForNewCalculation();
        }
        mCurrentTextResult.append(number);
        mTextViewResult.setText(mCurrentTextResult.toString());
    }

    private void prepareForNewCalculation() {
        mExample.setLength(0);
        mExample.append("");
        mCurrentTextResult.setLength(0);
        mCurrentTextResult.append("");
        mExampleArrayList.clear();
        mTextViewExample.setText("");
        mTextViewResult.setText("");
    }

    private void continueCalculation() {
        mExample.setLength(0);
        mExample.append(mCurrentTextResult.toString());
        mCurrentTextResult.setLength(0);
        mCurrentTextResult.append("");
        mExampleArrayList.clear();
        mExampleArrayList.add(mExample.toString());
        mTextViewExample.setText(mExample.toString());
    }

    private void setTextExamle(String operand) {
        mExample.append(mCurrentTextResult.toString());
        mExample.append(operand);

        mExampleArrayList.add(mCurrentTextResult.toString());
        if (!operand.equals(""))
            mExampleArrayList.add(operand);

        mTextViewExample.setText(mExample.toString());
        mCurrentTextResult.setLength(0);
        mCurrentTextResult.append("");
        mTextViewResult.setText("");
    }

    private boolean isLastSymbolOperand() {
        char lastSymbol = mCurrentTextResult.charAt(mCurrentTextResult.length() - 1);
        if (lastSymbol == '%' || lastSymbol == '.' || lastSymbol == '/' || lastSymbol == '*' || lastSymbol == '-' || lastSymbol == '+')
            return true;
        return false;
    }

    private boolean isLastSymbolSignEquel() {
        if(mExample.length() == 0){
            return false;
        }
        char lastSymbol = mExample.charAt(mExample.length() - 1);
        if (lastSymbol == '=')
            return true;
        return false;
    }

    private String infixToPostfix(CustomStack customStack) { // Преобразование в постфиксную форму

        StringBuilder sbString = new StringBuilder();
        int nextIndex = 0;

        for (int j = 0; j < mExampleArrayList.size(); j++) {

            String ch = mExampleArrayList.get(j);

            switch (ch) {
                case "+": // + или -
                case "-":
                    getOperator(ch, 1, sbString, customStack); // Извлечение операторов
                    break; // (приоритет 1)
                case "*": // * или /
                case "/":
                    getOperator(ch, 2, sbString, customStack); // Извлечение операторов
                    break; // (приоритет 2)
                case ".":
                    sbString.append(ch); // Записать в выходную строку
                    break;
                default: // Остается операнд
                    nextIndex = j + 1;
                    if (nextIndex == mExampleArrayList.size()) {
                        sbString.append(ch + ";"); // Записать в выходную строку
                        break;
                    }

                    if (mExampleArrayList.get(nextIndex) == ".") {
                        sbString.append(ch); // Записать в выходную строку
                    } else {
                        sbString.append(ch + ";"); // Записать в выходную строку
                    }
                    nextIndex = 0;
                    break;
            }
        }

        while (!customStack.isEmpty()) // Извлечение оставшихся операторов
        {
            sbString.append(customStack.pop() + ";"); // Записать в выходную строку
        }

        return sbString.toString(); // Возвращение постфиксного выражения
    }

    private void getOperator(String opThis, int prec1, StringBuilder sbString, CustomStack customStack) { // Чтение оператора из входной строки
        while (!customStack.isEmpty()) {
            String opTop = (String) customStack.pop();
            int prec2; // Приоритет нового оператора
            if (opTop == "+" || opTop == "-") // Определение приоритета
                prec2 = 1;
            else
                prec2 = 2;
            if (prec2 < prec1) // Если приоритет нового оператора
            { // меньше приоритета старого
                customStack.push(opTop); // Сохранить новый оператор
                break;
            } else // Приоритет нового оператора
                sbString.append(opTop + ";"); // не меньше приоритета старого
        }
        customStack.push(opThis); // Занесение в стек нового оператора
    }

    private String getSum(String num1, String num2) {
        if (!num1.contains(".") && !num2.contains(".")) {
            return Integer.toString(Integer.parseInt(num1) + Integer.parseInt(num2));
        } else {
            return Float.toString(Float.parseFloat(num1) + Float.parseFloat(num2));
        }
    }

    private String getMinus(String num1, String num2) {
        if (!num1.contains(".") && !num2.contains(".")) {
            return Integer.toString(Integer.parseInt(num1) - Integer.parseInt(num2));
        } else {
            return Float.toString(Float.parseFloat(num1) - Float.parseFloat(num2));
        }
    }

    private String getMultiply(String num1, String num2) {
        if (!num1.contains(".") && !num2.contains(".")) {
            return Integer.toString(Integer.parseInt(num1) * Integer.parseInt(num2));
        } else {
            return Float.toString(Float.parseFloat(num1) * Float.parseFloat(num2));
        }
    }

    private String getDivide(String num1, String num2) {
        return Float.toString(Float.parseFloat(num1) / Float.parseFloat(num2));
    }

    private boolean getIsNightMode() {
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(CommonPreferences.NameSharedPreference, MODE_PRIVATE);

        return sharedPref.getBoolean(CommonPreferences.KEY_DAY_NIGHT_MODE, false);
    }

    private void onOffNightMode(boolean isNightMode) {

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);

        instanceState.putString(CommonPreferences.KEY_CURRENT_STATE_TEXT_VIEW_RESULT, mTextViewResult.getText().toString());
        instanceState.putString(CommonPreferences.KEY_CURRENT_STATE_TEXT_VIEW_EXAMPLE, mTextViewExample.getText().toString());
        instanceState.putString(CommonPreferences.KEY_mCurrentTextResult, mCurrentTextResult.toString());
        instanceState.putString(CommonPreferences.KEY_mExample, mExample.toString());
        instanceState.putSerializable(CommonPreferences.KEY_mExampleArrayList, mExampleArrayList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTextViewResult.setText(savedInstanceState.getString(CommonPreferences.KEY_CURRENT_STATE_TEXT_VIEW_RESULT));
        mTextViewExample.setText(savedInstanceState.getString(CommonPreferences.KEY_CURRENT_STATE_TEXT_VIEW_EXAMPLE));
        mCurrentTextResult.append(savedInstanceState.getString(CommonPreferences.KEY_mCurrentTextResult));
        mExample.append(savedInstanceState.getString(CommonPreferences.KEY_mExample));
        mExampleArrayList = (ArrayList<String>) savedInstanceState.getSerializable(CommonPreferences.KEY_mExampleArrayList);
    }
}