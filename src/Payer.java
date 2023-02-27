import java.util.List;

interface Payer {

    float charge(float price, String description);

    boolean isImmediate();
}


class Immediate implements Payer {

    private Long payerId;
    private float clientCredit;
    private int immediateTransactionsCounter;

    Immediate(Long payerId,
              float clientCredit,
              int immediateTransactionsCounter) {
        this.payerId = payerId;
        this.clientCredit = clientCredit;
        this.immediateTransactionsCounter = immediateTransactionsCounter;
    }

    @Override
    public float charge(float price, String description) {
        float chargeAmount = Math.max(price - clientCredit, 0);
        this.immediateTransactionsCounter++;
        return chargeAmount;
    }

    @Override
    public boolean isImmediate() {
        return true;
    }
}

class MonthlyBilled implements Payer {

    private Long payerId;
    private float clientCredit;
    private List<String> monthlyPaymentDescriptions;

    MonthlyBilled(Long payerId, float clientCredit, List<String> monthlyPaymentDescriptions) {
        this.payerId = payerId;
        this.clientCredit = clientCredit;
        this.monthlyPaymentDescriptions = monthlyPaymentDescriptions;
    }

    @Override
    public float charge(float price, String description) {
        float chargeAmount = Math.max(price - clientCredit, 0);
        monthlyPaymentDescriptions.add(description);
        return chargeAmount;
    }

    @Override
    public boolean isImmediate() {
        return false;
    }
}
