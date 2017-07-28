package com.example.calculatortest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    StringBuffer sb = new StringBuffer();
    private TextView text = null;   // 用于显示输入和输出
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_1);
        text = (TextView) findViewById(R.id.text_edit);
        // 数字按钮
        Button button0 = (Button)findViewById(R.id.button_0);
        Button button1 = (Button)findViewById(R.id.button_1);
        Button button2 = (Button)findViewById(R.id.button_2);
        Button button3 = (Button)findViewById(R.id.button_3);
        Button button4 = (Button)findViewById(R.id.button_4);
        Button button5 = (Button)findViewById(R.id.button_5);
        Button button6 = (Button)findViewById(R.id.button_6);
        Button button7 = (Button)findViewById(R.id.button_7);
        Button button8 = (Button)findViewById(R.id.button_8);
        Button button9 = (Button)findViewById(R.id.button_9);

        // 功能按钮
        // 四则运算
        Button button_div = (Button)findViewById(R.id.button_div);
        Button button_mul = (Button)findViewById(R.id.button_mul);
        Button button_add = (Button)findViewById(R.id.button_add);
        Button button_minus = (Button) findViewById(R.id.button_minus);

        Button button_back  = (Button)findViewById(R.id.button_back);
        Button button_clear = (Button) findViewById(R.id.button_clear);

        Button button_point = (Button) findViewById(R.id.button_point);
        Button button_equals = (Button) findViewById(R.id.button_equals);

        // 左右括号
        Button button_left = (Button) findViewById(R.id.button_left);
        Button button_right = (Button) findViewById(R.id.button_right);


        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        button_add.setOnClickListener(this);
        button_minus.setOnClickListener(this);
        button_mul.setOnClickListener(this);
        button_div.setOnClickListener(this);

        button_back.setOnClickListener(this);
        button_clear.setOnClickListener(this);

        button_point.setOnClickListener(this);
        button_equals.setOnClickListener(this);

        button_left.setOnClickListener(this);
        button_right.setOnClickListener(this);
    }

    private String sign = "+-*/";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                sb.append("1");
                text.setText(sb);
                break;
            case R.id.button_2:
                sb.append("2");
                text.setText(sb);
                break;
            case R.id.button_3:
                sb.append("3");
                text.setText(sb);
                break;
            case R.id.button_4:
                sb.append("4");
                text.setText(sb);
                break;
            case R.id.button_5:
                sb.append("5");
                text.setText(sb);
                break;
            case R.id.button_6:
                sb.append("6");
                text.setText(sb);
                break;
            case R.id.button_7:
                sb.append("7");
                text.setText(sb);
                break;
            case R.id.button_8:
                sb.append("8");
                text.setText(sb.toString());
                break;
            case R.id.button_9:
                sb.append("9");
                text.setText(sb);
                break;
            case R.id.button_0:
                sb.append("0");
                text.setText(sb);
                break;
            case R.id.button_back:
                if(sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                    text.setText(sb);
                }
                break;
            case R.id.button_clear:
                sb.setLength(0);
                text.setText(sb);
                break;
            case R.id.button_add:
                if(sb.length() > 0 && sign.indexOf(sb.charAt(sb.length() - 1)) == -1) {
                    sb.append("+");
                }
                text.setText(sb);
                break;
            case R.id.button_minus:
                if(sb.length() == 0 || sb.length() > 0 && sign.indexOf(sb.charAt(sb.length() - 1)) == -1) {
                    sb.append("-");
                }
                text.setText(sb);
                break;
            case R.id.button_mul:
                if(sb.length() > 0 && sign.indexOf(sb.charAt(sb.length() - 1)) == -1) {
                    sb.append("×");
                }
                text.setText(sb);
                break;
            case R.id.button_div:
                if(sb.length() > 0 && sign.indexOf(sb.charAt(sb.length() - 1)) == -1) {
                    sb.append("÷");
                }
                text.setText(sb);
                break;
            case R.id.button_point:
                if(sb.length() == 0 ||!Character.isDigit(sb.charAt(sb.length() - 1))) {
                    sb.append("0.");
                }
                else {
                    sb.append(".");
                }
                text.setText(sb);
                break;
            case R.id.button_left:
                sb.append("(");
                text.setText(sb);
                break;
            case R.id.button_right:
                if (sb.indexOf("(") != -1) {
                    sb.append(")");
                }
                text.setText(sb);
                break;
            case R.id.button_equals:
                String s = sb.toString().replace("×", "*").replace("÷", "/");
                try {
                    s = Calculator.toSuffix(s);
                } catch (Exception ex) {
                    s  = "出错";
                }
                text.setText(sb + "\n"+"=" + s);

                if (s != "出错") {
                    sb = new StringBuffer(s);
                }else {
                    sb.delete(0, sb.length());
                }
                break;
        }
    }
}







