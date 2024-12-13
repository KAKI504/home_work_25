package payment;

public class QrCodePaymentProcessor implements PaymentProcessor {
    private int balance;

    @Override
    public boolean processPayment(int amount) {
        System.out.println("Отсканируйте QR-код для оплаты...");
        System.out.println("Оплата через QR-код успешно завершена!");
        balance -= amount;
        return true;
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
