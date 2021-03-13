package ru.andr.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private Button mBtn0;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;
    private Button mBtn7;
    private Button mBtn8;
    private Button mBtn9;
    private Button mBtnClear;
    private Button mBtnBackSpace;
    private Button mBtnDivide;
    private Button mBtnMultiply;
    private Button mBtnMinus;
    private Button mBtnPlus;
    private Button mBtnEquel;
    private Button mBtnPt;
    private Button mBtnSettings;

    private TextView mTextViewResult;
    private TextView mTextViewExample;

    private StringBuilder mCurrentTextResult;
    private StringBuilder mExample;
    private ArrayList<String> mExampleArrayList;

    private boolean mIsNightMode;

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

        mBtnSettings = (Button) findViewById(R.id.btnSettings);

        mBtn0 = (Button) findViewById(R.id.button0);
        mBtn1 = (Button) findViewById(R.id.button1);
        mBtn2 = (Button) findViewById(R.id.button2);
        mBtn3 = (Button) findViewById(R.id.button3);
        mBtn4 = (Button) findViewById(R.id.button4);
        mBtn5 = (Button) findViewById(R.id.button5);
        mBtn6 = (Button) findViewById(R.id.button6);
        mBtn7 = (Button) findViewById(R.id.button7);
        mBtn8 = (Button) findViewById(R.id.button8);
        mBtn9 = (Button) findViewById(R.id.button9);
        mBtnClear = (Button) findViewById(R.id.buttonClear);
        mBtnBackSpace = (Button) findViewById(R.id.buttonBackSpace);
        mBtnDivide = (Button) findViewById(R.id.buttonDivide);
        mBtnMultiply = (Button) findViewById(R.id.buttonMultiply);
        mBtnMinus = (Button) findViewById(R.id.buttonMinus);
        mBtnPlus = (Button) findViewById(R.id.buttonPlus);
        mBtnEquel = (Button) findViewById(R.id.buttonEquel);
        mBtnPt = (Button) findViewById(R.id.buttonPt);

        mTextViewResult = (TextView) findViewById(R.id.textViewResult);
        mTextViewExample = (TextView) findViewById(R.id.textViewExample);

        mBtnClear.setOnClickListener(view -> {
            mCurrentTextResult.setLength(0);
            mCurrentTextResult.append("");
            mTextViewResult.setText("");

            mExample.setLength(0);
            mExample.append("");
            mTextViewExample.setText("");

            mExampleArrayList.clear();
        });

        mBtnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);

            //  ActivityInfo activityInfo = intent.resolveActivityInfo(getPackageManager(), intent.getFlags());
            //  if(activityInfo != null){
            startActivity(intent);
            // }
        });

        mBtn0.setOnClickListener(view -> {
            if (mCurrentTextResult.length() == 0) {
                return;
            }

            mCurrentTextResult.append("0");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn1.setOnClickListener(view -> {
            mCurrentTextResult.append("1");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn2.setOnClickListener(view -> {
            mCurrentTextResult.append("2");
            ;
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn3.setOnClickListener(view -> {
            mCurrentTextResult.append("3");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn4.setOnClickListener(view -> {
            mCurrentTextResult.append("4");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn5.setOnClickListener(view -> {
            mCurrentTextResult.append("5");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn6.setOnClickListener(view -> {
            mCurrentTextResult.append("6");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn7.setOnClickListener(view -> {
            mCurrentTextResult.append("7");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn8.setOnClickListener(view -> {
            mCurrentTextResult.append("8");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtn9.setOnClickListener(view -> {
            mCurrentTextResult.append("9");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtnBackSpace.setOnClickListener(view -> {
            if (mCurrentTextResult.length() == 0)
                return;
            mCurrentTextResult.deleteCharAt(mCurrentTextResult.length() - 1);
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtnPt.setOnClickListener(view -> {

            if (mCurrentTextResult.toString().contains("."))
                return;
            if (mCurrentTextResult.length() == 0) {
                mCurrentTextResult.append("0");
            }

            if (isLastSymbolOperand())
                return;

            mCurrentTextResult.append(".");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });


        mBtnDivide.setOnClickListener(view -> {
            if (mCurrentTextResult.length() == 0)
                return;

            if (isLastSymbolOperand())
                return;

            setTextExamle("/");
        });

        mBtnMultiply.setOnClickListener(view -> {
            if (mCurrentTextResult.length() == 0)
                return;

            if (isLastSymbolOperand())
                return;

            setTextExamle("*");

        });

        mBtnMinus.setOnClickListener(view -> {
            if (mCurrentTextResult.length() == 0)
                return;

            if (isLastSymbolOperand())
                return;

            setTextExamle("-");

        });

        mBtnPlus.setOnClickListener(view -> {
            if (mCurrentTextResult.length() == 0)
                return;

            if (isLastSymbolOperand())
                return;

            setTextExamle("+");

        });

        mBtnEquel.setOnClickListener(view -> {
            if (mCurrentTextResult.length() == 0)
                return;

            if (isLastSymbolOperand())
                return;

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

            float num1;
            float num2;

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

        });

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
            //  String as = customStack.pop();

            sbString.append(customStack.pop() + ";"); // Записать в выходную строку
            //  sbString.append(";"); // Записать в выходную строку
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

    private Object parseToNumber(String num) {
        if (num.contains(".")) {
            return Float.parseFloat(num);
        } else {
            return Integer.parseInt(num);
        }
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
//        if (!num1.contains(".") && !num2.contains(".")) {
//            return Integer.toString(Integer.parseInt(num1) / Integer.parseInt(num2));
//        }else{
        return Float.toString(Float.parseFloat(num1) / Float.parseFloat(num2));
//        }
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