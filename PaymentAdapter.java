interface PaymentProcessor {
    void processPayment(String customerId, double amountInDollars);
}
class OldPayGateway {
    public void chargeInCents(int cents, String accountRef) {
        System.out.println("Processing via OldPayGateway...");
        System.out.println("Account: " + accountRef + " | Charged: " + cents + " cents");
    }
}
public class PaymentAdapter implements PaymentProcessor {
    private OldPayGateway oldGateway;

    public PaymentAdapter(OldPayGateway oldGateway) {
        this.oldGateway = oldGateway;
    }
    public static void main(String[] args) {
        String studentId = "240103008"; 
        System.out.println("Student ID: " + studentId);

        OldPayGateway legacyGateway = new OldPayGateway();
        PaymentProcessor adapter = new PaymentAdapter(legacyGateway);

        adapter.processPayment(studentId, 142.75);
    }
}