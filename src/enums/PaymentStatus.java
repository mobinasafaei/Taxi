package enums;

public enum PaymentStatus {
    PAYED("PAYED"),
    NOT_PAYED("NOT_PAYED");

    private String paymentStatus;

    PaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
