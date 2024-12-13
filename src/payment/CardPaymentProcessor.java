package payment;

import java.util.Scanner;

public class CardPaymentProcessor implements PaymentProcessor {
    private int balance;
    private String savedCardNumber;
    private String savedOtp;

    @Override
    public boolean processPayment(int amount) {
        Scanner scanner = new Scanner(System.in);

        if (savedCardNumber == null || savedOtp == null) {
            System.out.println("Введите номер карты:");
            savedCardNumber = scanner.nextLine();
            System.out.println("Введите одноразовый пароль:");
            savedOtp = scanner.nextLine();
        }

        System.out.println("Подтвердите оплату. Введите номер карты:");
        String cardNumber = scanner.nextLine();
        System.out.println("Введите одноразовый пароль:");
        String otp = scanner.nextLine();

        if (cardNumber.equals(savedCardNumber) && otp.equals(savedOtp)) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Оплата прошла успешно!");
                return true;
            } else {
                System.out.println("Недостаточно средств!");
            }
        } else {
            System.out.println("Ошибка в данных карты или пароле!");
        }
        return false;
    }

    @Override
    public void addBalance(int amount) {
        balance += amount;
    }

    @Override
    public int getBalance() {
        return balance;
    }
}