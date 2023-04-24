package us.hcheng.javaio.thread.part1.chapter2.taxcalculator;

import org.junit.Test;

public class App {

    @Test
    public void t1() {
        TaxCalculator calculator = new TaxCalculator(10000D, 2000D){
            @Override
            protected double calculateTax() {
                return this.getSalary() * .2 + getBonus() * .15;
            }
        };
        System.out.println(calculator.calculate());
    }

    @Test
    public void t2() {
        TaxCalculator calculator = new TaxCalculator(10000d, 2000d, new SimpleCalculatorStrategy());
        System.out.println(calculator.calculate());
    }

    @Test
    public void t3() {
        System.out.println(
                new TaxCalculator(10000d, 2000d, (s, b) -> s * .1 + b * .2).calculate()
        );
    }

}
