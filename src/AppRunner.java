import enums.ActionLetter;
import model.*;
import payment.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private PaymentProcessor paymentProcessor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });

        choosePaymentMethod();
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void choosePaymentMethod() {
        while (true) {
            try {
                System.out.println("Выберите способ оплаты:");
                System.out.println("1. Монетоприемник");
                System.out.println("2. Оплата картой");
                System.out.println("3. Оплата через QR-код");

                int choice = Integer.parseInt(new Scanner(System.in).nextLine());
                switch (choice) {
                    case 1 -> paymentProcessor = new CoinPaymentProcessor();
                    case 2 -> paymentProcessor = new CardPaymentProcessor();
                    case 3 -> paymentProcessor = new QrCodePaymentProcessor();
                    default -> throw new IllegalArgumentException("Допустимые значения: 1, 2 или 3.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число.");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Баланс: " + paymentProcessor.getBalance() + " сом");

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentProcessor.getBalance() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс на 10 сом");
        print(" b - Пополнить баланс на 50 сом");
        print(" q - Выбрать способ оплаты");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            paymentProcessor.addBalance(10);
            print("Вы пополнили баланс на 10 сом");
            return;
        }
        if ("b".equalsIgnoreCase(action)) {
            paymentProcessor.addBalance(50);
            print("Вы пополнили баланс на 50 сом");
            return;
        }
        if ("q".equalsIgnoreCase(action)) {
            choosePaymentMethod();
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    if (paymentProcessor.processPayment(products.get(i).getPrice())) {
                        print("Вы купили " + products.get(i).getName());
                    } else {
                        print("Недостаточно средств!");
                    }
                    return;
                }
            }
            throw new IllegalArgumentException("Недопустимая буква. Попробуйте еще раз.");
        } catch (IllegalArgumentException e) {
            print(e.getMessage());
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}