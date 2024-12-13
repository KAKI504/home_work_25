package payment;

public interface PaymentProcessor {
    boolean processPayment(int amount);

    void addBalance(int amount);

    int getBalance();
}
