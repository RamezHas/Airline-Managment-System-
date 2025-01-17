package AirlineManagementSystem;

import java.time.LocalDate;

public class Payment {
    private int pnrNo;
    private String phoneNo;
    private String cardNo;
    private double amountPaid;
    private LocalDate paymentDate;

    public Payment(int pnrNo, String phoneNo, String cardNo, double amountPaid, LocalDate paymentDate) {
        this.pnrNo = pnrNo;
        this.phoneNo = phoneNo;
        this.cardNo = cardNo;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
    }

    public int getPnrNo() {
        return pnrNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
}