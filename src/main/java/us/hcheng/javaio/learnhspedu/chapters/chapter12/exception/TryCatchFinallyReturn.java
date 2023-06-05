package us.hcheng.javaio.learnhspedu.chapters.chapter12.exception;

public class TryCatchFinallyReturn {

    public static void main(String[] args) {
        System.out.println(tcfReturn(0).equals("FinallyReturn"));
        System.out.println(tcfReturn(1).equals("FinallyReturn"));

        System.out.println(tcf2Return(0).equals("NormalReturn"));
        System.out.println(tcf2Return(1).equals("SuccessReturn"));

        System.out.println(tcReturn(0).equals("ExceptionReturn"));
        System.out.println(tcReturn(1).equals("SuccessReturn"));

        System.out.println(tc2Return(0).equals("NormalReturn"));
        System.out.println(tc2Return(1).equals("SuccessReturn"));

        System.out.println(Throwable.class);
    }

    private static String tcfReturn(int n) {
        try {
            int res = 4 / n;
            return "SuccessReturn";
        } catch (ArithmeticException ex) {
            return "ExceptionReturn";
        } finally {
            return "FinallyReturn";
        }
    }

    private static String tcf2Return(int n) {
        try {
            int res = 4 / n;
            System.out.println("tcf2Return try and SuccessReturn");
            return "SuccessReturn";
        } catch (ArithmeticException ex) {
            System.out.println("tcf2Return ex");
        } finally {
            System.out.println("tcf2Return finally");
        }

        System.out.println("tcf2Return NormalReturn");
        return "NormalReturn";
    }

    private static String tcReturn(int n) {
        try {
            int res = 4 / n;
            return "SuccessReturn";
        } catch (ArithmeticException ex) {
            return "ExceptionReturn";
        }
    }

    private static String tc2Return(int n) {
        try {
            int res = 4 / n;
            return "SuccessReturn";
        } catch (ArithmeticException ex) {
            //System.out.println(ex.getMessage());
        }

        return "NormalReturn";
    }

}
