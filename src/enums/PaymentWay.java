package enums;

public enum PaymentWay {
    PAY_BY_CASH("PAY_BY_CASH"),
    PAY_BY_ACCOUNT_BALANCE("PAY_BY_ACCOUNT_BALANCE");
    private String paymentWay;

    PaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getPaymentWay() {
        return paymentWay;
    }
}
