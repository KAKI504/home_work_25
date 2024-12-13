package payment;

public class CoinPaymentProcessor implements PaymentProcessor {
    private int balance;

    @Override
    public boolean processPayment(int amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
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
