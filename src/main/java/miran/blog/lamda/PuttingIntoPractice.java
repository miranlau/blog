package miran.blog.lamda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PuttingIntoPractice {
    public static void main(String ...args) {
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


        // 找出2011年发生的所有交易，并按交易额排序
        List<Transaction> result =
                transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                        .sorted(Comparator.comparing(Transaction::getValue))
                        .collect(Collectors.toList());
        result.forEach(System.out::println);

        // 交易员在哪些不同的城市工作过
        List<String> cities = transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct().collect(Collectors.toList());
        cities.forEach(System.out::println);

        // 查找所有来自剑桥的交易员，并按姓名排序
        List<Trader> traders = transactions.stream().map(Transaction::getTrader).filter(trader -> "Cambridge".equals(trader.getCity()))
                .sorted(Comparator.comparing(Trader::getName)).distinct().collect(Collectors.toList());
        traders.forEach(System.out::println);

        // 返回所有交易员的姓名字符串，并按字母顺序排序
        String names = transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct().sorted().reduce("", (n1, n2) -> n1 + n2);
        System.out.println(names);

        // 有没有交易员在米兰工作的？
        boolean milanBased =
                transactions.stream()
                        .anyMatch(transaction -> transaction.getTrader()
                                .getCity()
                                .equals("Milan")
                        );
        System.out.println(milanBased);

        // 打印生活在剑桥的交易员的所有交易额
        Integer tradeMount = transactions.stream().filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(transaction -> transaction.getValue()).reduce(0, Integer::sum);
        System.out.println(tradeMount);

        // 所有交易中，最高的交易额是多少
        int highestValue =
                transactions.stream()
                        .map(Transaction::getValue)
                        .reduce(0, Integer::max);
        System.out.println(highestValue);

        new Thread(() -> {
            System.out.println("");
        }).start();
    }
}
