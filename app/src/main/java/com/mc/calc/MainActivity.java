package com.mc.calc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {

    Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, decimalBtn,
            plusBtn, minusBtn, mulBtn, divBtn,
            commaBtn, leftBracketBtn, rightBracketBtn,
            degreeBtn, sqrtBtn, rootBtn, logBtn,
            clearBtn, backspaceBtn, calcBtn;
    EditText exprEtd;
    TextView resultTv;

    String expression = "";
    String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b0 = findViewById(R.id.b0);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        decimalBtn = findViewById(R.id.decimalBtn);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        mulBtn = findViewById(R.id.mulBtn);
        divBtn = findViewById(R.id.divBtn);
        commaBtn = findViewById(R.id.commaBtn);
        leftBracketBtn = findViewById(R.id.leftBracketBtn);
        rightBracketBtn = findViewById(R.id.rightBracketBtn);
        degreeBtn = findViewById(R.id.degreeBtn);
        sqrtBtn = findViewById(R.id.sqrtBtn);
        rootBtn = findViewById(R.id.rootBtn);
        logBtn = findViewById(R.id.logBtn);
        clearBtn = findViewById(R.id.clearBtn);
        backspaceBtn = findViewById(R.id.backspaceBtn);
        calcBtn = findViewById(R.id.calcBtn);

        exprEtd = findViewById(R.id.exprEdt);
        resultTv = findViewById(R.id.resultTv);

        exprEtd.setShowSoftInputOnFocus(false);

        Button[] pBtn = new Button[]{b0, b1, b2, b3, b4, b5, b6, b7, b8, b9,
                decimalBtn, plusBtn, minusBtn, mulBtn, divBtn, commaBtn, leftBracketBtn, rightBracketBtn};
        for (Button btn : pBtn) {
            if (btn != null)
                btn.setOnClickListener(view -> addText(btn.getText().toString()));
        }

        if (degreeBtn != null) degreeBtn.setOnClickListener(view -> addText("^"));
        if (sqrtBtn != null) sqrtBtn.setOnClickListener(view -> addText("sqrt("));
        if (rootBtn != null) rootBtn.setOnClickListener(view -> addText("root("));
        if (logBtn != null) logBtn.setOnClickListener(view -> addText("log("));

        clearBtn.setOnClickListener(view -> {
            expression = result = "";
            exprEtd.setText("");
            resultTv.setText("");
        });

        backspaceBtn.setOnClickListener(view -> {
            int cursorPos = exprEtd.getSelectionStart();
            int textLen = expression.length();

            if (cursorPos != 0 && textLen != 0) {
                expression = expression.substring(0, cursorPos - 1) + expression.substring(cursorPos);
                exprEtd.setText(expression);
                exprEtd.setSelection(cursorPos - 1);
            }
        });

        calcBtn.setOnClickListener(view -> {
            Expression expr = new Expression(expression);
            result = expr.checkSyntax() ? String.valueOf(expr.calculate()) : "Ошибка";
            resultTv.setText(result);
        });
    }

    void addText(String text) {
        int cursorPos = exprEtd.getSelectionStart();
        String leftStr = expression.substring(0, cursorPos);
        String rightStr = expression.substring(cursorPos);
        expression = leftStr + text + rightStr;
        exprEtd.setText(expression);
        exprEtd.setSelection(cursorPos + text.length());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("expr", expression);
        outState.putString("result", result);
        outState.putInt("cursorPos", exprEtd.getSelectionStart());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        expression = savedInstanceState.getString("expr", "");
        result = savedInstanceState.getString("result", "");
        exprEtd.setText(expression);
        resultTv.setText(result);
        exprEtd.setSelection(savedInstanceState.getInt("cursorPos", 0));
    }
}