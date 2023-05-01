package us.hcheng.javaio.java8.app;

import us.hcheng.javaio.java8.entity.Trader;
import us.hcheng.javaio.java8.entity.Transaction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StreamInAction {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        System.out.println("1. Find all transactions in the year 2011 and sort them by value (small to high).");
        transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .forEach(System.out::println);

        System.out.println("2. What are all the unique cities where the traders work?");
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .forEach(System.out::println);

        System.out.println("3. Find all traders from Cambridge and sort them by name.");
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> "Cambridge".equals(t.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                //.sorted((a, b) -> a.getName().compareTo(b.getName()))
                .forEach(System.out::println);

        System.out.println("4. Return a string of all traders’ names sorted alphabetically");
        String names = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted(String::compareTo)
                .reduce("", (n, n2) -> String.join(", ", n, n2))
                        .substring(2);
        System.out.println(names);

        System.out.println("5. Are any traders based in Milan?");
        boolean anyMilan = transactions.stream().anyMatch(t -> "Milan".equals(t.getTrader().getCity()));
        System.out.println(anyMilan);

        System.out.println("6. Print all transactions’ values from the traders living in Cambridge.");
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        System.out.println("7. What’s the highest value of all the transactions?");
        int max = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Math::max);
        System.out.println(max);

        System.out.println("8. Find the transaction with the smallest value.");
        Transaction t = transactions.stream()
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .limit(1)
                .toList().get(0);
        System.out.println(t);
    }

}
