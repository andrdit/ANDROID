package ru.andr.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
    private Button mBtnProcents;
    private Button mBtnDivide;
    private Button mBtnMultiply;
    private Button mBtnMinus;
    private Button mBtnPlus;
    private Button mBtnEquel;
    private Button mBtnPt;

    private TextView mTextViewResult;
    private TextView mTextViewExample;

    private StringBuilder mCurrentTextResult;
    private StringBuilder mExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_board_layout);

        mCurrentTextResult = new StringBuilder();
        mCurrentTextResult.append("");

        mExample = new StringBuilder();
        mExample.append("");

        init();
    }

    private void init() {

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
        mBtnProcents = (Button) findViewById(R.id.buttonProcents);
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
        });

        mBtn0.setOnClickListener(view -> {
            if(mCurrentTextResult.length() == 0){
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
            mCurrentTextResult.append("2");;
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
            if(mCurrentTextResult.length() == 0)
                return;
            mCurrentTextResult.deleteCharAt(mCurrentTextResult.length() - 1);
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtnPt.setOnClickListener(view -> {

            if(mCurrentTextResult.toString().contains("."))
                return;
            if(mCurrentTextResult.length() == 0){
                mCurrentTextResult.append("0");
            }

            if(isLastSymbolOperand())
                return;

            mCurrentTextResult.append(".");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

        mBtnProcents.setOnClickListener(view -> {
            if(mCurrentTextResult.length() == 0)
                return;

            if(isLastSymbolOperand())
                return;

            setTextExamle('/');

        });

        mBtnDivide.setOnClickListener(view -> {
            if(mCurrentTextResult.length() == 0)
                return;

            if(isLastSymbolOperand())
                return;

            setTextExamle('/');
        });

        mBtnMultiply.setOnClickListener(view -> {
            if(mCurrentTextResult.length() == 0)
                return;

            if(isLastSymbolOperand())
                return;

            setTextExamle('*');

        });

        mBtnMinus.setOnClickListener(view -> {
            if(mCurrentTextResult.length() == 0)
                return;

            if(isLastSymbolOperand())
                return;

            setTextExamle('-');

        });

        mBtnPlus.setOnClickListener(view -> {
            if(mCurrentTextResult.length() == 0)
                return;

            if(isLastSymbolOperand())
                return;

            setTextExamle('+');

        });

        mBtnEquel.setOnClickListener(view -> {
            if(mCurrentTextResult.length() == 0)
                return;

            if(isLastSymbolOperand())
                return;
            mCurrentTextResult.append("=");
            mTextViewResult.setText(mCurrentTextResult.toString());
        });

    }

    private void setTextExamle(char operand) {
        mExample.append(mCurrentTextResult.toString());
        mExample.append(operand);
        mTextViewExample.setText(mExample.toString());
        mCurrentTextResult.setLength(0);
        mCurrentTextResult.append("");
        mTextViewResult.setText("");
    }

    private boolean isLastSymbolOperand(){
        char lastSymbol = mCurrentTextResult.charAt(mCurrentTextResult.length() - 1);
        if(lastSymbol == '%' || lastSymbol == '.' || lastSymbol == '/' || lastSymbol == '*' || lastSymbol == '-' || lastSymbol == '+')
            return true;
        return false;
    }
}