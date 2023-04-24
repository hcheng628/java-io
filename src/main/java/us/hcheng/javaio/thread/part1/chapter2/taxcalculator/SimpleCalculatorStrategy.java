package us.hcheng.javaio.thread.part1.chapter2.taxcalculator;

public class SimpleCalculatorStrategy implements ICalculatorStrategy {

    private final static double SALARY_TAX_RATE = .3;
    private final static double BONUS_TAX_RATE = .2;

    @Override
    public double calculate(double salary, double bonus) {
        return SALARY_TAX_RATE * salary + BONUS_TAX_RATE * bonus;
    }

}
