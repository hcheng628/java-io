package us.hcheng.javaio.thread.part1.chapter2.taxcalculator;

public class TaxCalculator {

    private final double salary;

    private final double bonus;

    private ICalculatorStrategy strategy;

    public TaxCalculator(double salary, double bonus) {
        this.bonus = bonus;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }

    public TaxCalculator(double salary, double bonus, ICalculatorStrategy strategy) {
        this(salary, bonus);
        this.strategy = strategy;
    }

    protected double calculateTax() {
        return strategy.calculate(this.salary, this.bonus);
    }

    public double calculate() {
        return this.calculateTax();
    }

}
