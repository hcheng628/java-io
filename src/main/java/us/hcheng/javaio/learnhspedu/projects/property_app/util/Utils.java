package us.hcheng.javaio.learnhspedu.projects.property_app.util;

import us.hcheng.javaio.learnhspedu.projects.property_app.entity.Validation;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Utils {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static <T> T read(Supplier<T> supplier) {
        return supplier.get();
    }

    public static String readString() {
        String s =  SCANNER.nextLine();
        return s == null ? "" : s.trim();
    }

    public static Integer readInt() {
        return Integer.parseInt(SCANNER.nextLine());
    }

    public static <T> T getInput(String info, Consumer<String> display,
                            Supplier<T> readFunc,
                            Validation rule, BiFunction<T, Validation, Boolean> func) {
        for (; ; ) {
            display.accept(info);
            T t = null;

            try {
                t = readFunc.get();
            } catch (Exception ex) {
                System.err.println("Invalid input...");
                continue;
            }

            if (func.apply(t, rule))
                return t;
            else
                System.err.println(String.join(":", "Invalid input", String.valueOf(t)));
        }
    }

    public static <T> boolean isValidInput(T val, Validation rule) {
        if (val instanceof Integer) {
            int intVal = (int) val;
            return (rule.isAllowEmpty() && intVal == -1) || intVal > -1;
        }

        String s = val.toString();
        if (s.length() == 0)
            return rule.isAllowEmpty();
        return s.length() <= rule.getMaxLen();
    }

    public static boolean isValidInput(String val, String[] vals) {
        return Arrays.asList(vals).contains(val);
    }

    public static boolean readConfirmSelection() {
        System.out.println("请输入你的选择(Y/N): 请小心选择");

        for (; ; ) {
            String str = readString().toUpperCase();
            if (str.isEmpty())
                System.err.print("选择错误，请重新输入：");

            char c = str.charAt(0);
            if (c != 'Y' && c != 'N') {
                System.err.print("选择错误，请重新输入：");
                continue;
            }

            return c == 'Y';
        }
    }

    public static void main(String[] args) {
        //System.out.println(validateInput(null, new Character[]{'1', '2'}));
    }

}
