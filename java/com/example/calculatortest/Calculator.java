package com.example.calculatortest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayDeque;



public class Calculator {

    public static String toSuffix(String input)
            throws IllegalArgumentException, NumberFormatException {
        int len = input.length();
        char c, tempChar;
        ArrayDeque<Character> s1 = new ArrayDeque<Character>();
        ArrayDeque<BigDecimal> s2 = new ArrayDeque<>();
        int lastIndex = -1;
        for (int i = 0; i < len; ++i) {
            c = input.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                lastIndex = readDouble(input, i);
                if (lastIndex == -10)
                    return "出错";
//                number = Double.parseDouble(input.substring(i, lastIndex));
                BigDecimal number = new BigDecimal(input.substring(i, lastIndex));
                s2.push(number);
                i = lastIndex - 1;

            }else if (c == '-' && i == 0) {
                lastIndex = readDouble(input, i + 1);
                if (lastIndex == -10)
                    return "出错";
//                number = Double.parseDouble(input.substring(i, lastIndex));
                BigDecimal number = new BigDecimal(input.substring(i, lastIndex));
                s2.push(number);
                i = lastIndex - 1;

            }else if (i > 0 && c == '-' &&
                    (input.charAt(i-1) == '('
                            || isOperator(input.charAt(i-1)))) {
                lastIndex = readDouble(input, i + 1);
                if (lastIndex == -10)
                    return "出错";
//                number = Double.parseDouble(input.substring(i, lastIndex));
                BigDecimal number = new BigDecimal(input.substring(i, lastIndex));
                s2.push(number);
                i = lastIndex - 1;

            }
            else if (isOperator(c)) {
                while (!s1.isEmpty() && s1.peek() != '('
                        && priorityCompare(c, s1.peek()) <= 0) {
//                    double num1 = s2.pop();
//                    double num2 = s2.pop();
                    BigDecimal num1 = s2.pop();
                    BigDecimal num2 = s2.pop();

                    s2.push(calc(num2, num1, s1.pop()));
                }
                s1.push(c);
            } else if (c == '(') {
                s1.push(c);
            } else if (c == ')') {
                while ((tempChar = s1.pop()) != '(') {
//                    double num1 = s2.pop();
//                    double num2 = s2.pop();
                    BigDecimal num1 = s2.pop();
                    BigDecimal num2 = s2.pop();
                    s2.push(calc(num2, num1, tempChar));
                    if (s1.isEmpty()) {
                        return "出错";
                    }
                }
            } else if (c == ' ') {
                // ignore
            } else {
                return "出错";
            }
        }
        while (!s1.isEmpty()) {
            tempChar = s1.pop();
//            double num1 = s2.pop();
//            double num2 = s2.pop();
            BigDecimal num1 = s2.pop();
            BigDecimal num2 = s2.pop();
            s2.push(calc(num2, num1, tempChar));
        }
        BigDecimal result = s2.pop();
        if (!s2.isEmpty())
            return "出错";
        if (result.toString().length() < 20)
            return result.stripTrailingZeros().toPlainString();
        else
            return result.round(new MathContext(6, RoundingMode.HALF_UP)).toEngineeringString();
    }

    /**
     * 获取是double值得最后一位索引
     */
    public static int readDouble(String str, int start)
            throws IllegalArgumentException{
        int len = str.length();
        int dotIndex = -1;
        char ch;
        for (int i = start; i < len ; i++) {
            ch = str.charAt(i);
            if(ch == '.') {
                if(dotIndex != -1)
                    return -10;
                else if(i == len - 1)
                    return -10;
                else
                    dotIndex = i;
            } else if(!Character.isDigit(ch)) {
                if (dotIndex == -1 || i - dotIndex > 1)
                    return i;
                else
                    throw new IllegalArgumentException("不是数字，不能作为数字的一部分");
            }
            else if(i == len - 1) {
                return len;
            }
        }
        return -10;
    }



    /**
     * 判断优先级
     */

    private static int priorityCompare (char op1, char op2) {
        if(op1 == '+' || op1 == '-')
            return (op2 == '*' || op2 == '/' ? -1 : 0);
        if (op1 == '*' || op1 == '/') {
            return (op2 == '+' || op2 == '-' ? 1 : 0);
        }
        return 1;
    }


    /**
     * 这个函数能够判断是否是操作运算符
     */

    private static boolean isOperator (char ch) {
        return (ch == '+' || ch == '-' || ch == '*' || ch == '/');
    }

    /**
     * 计算两个数的结果
     * 并返回
     */
    private static BigDecimal calc (BigDecimal num1, BigDecimal num2, char op)
            throws IllegalArgumentException{
        switch (op) {
            case '+':
                return num1.add(num2);
            case '-':
                return num1.subtract(num2);
            case '*':
                return num1.multiply(num2);
            case '/':
                if (num2.equals(0))
                    throw new IllegalArgumentException("除数不能为0");
                return num1.divide(num2, 8, RoundingMode.HALF_UP);
            default:
                return new BigDecimal(0);
        }

    }
}