import java.util.List;

class Client {

    private Long clientId;
    private float clientCredit;
    private int loyaltyPoints;
    private boolean isImmediate;
    private int immediateTransactionsCounter;
    private float clientPriceMultiplier;
    private List<String> monthlyPaymentDescriptions;

    Client(Long clientId, float clientCredit, int loyaltyPoints, boolean isImmediate, int immediateTransactionsCounter, float clientPriceMultiplier) {
        this.clientId = clientId;
        this.clientCredit = clientCredit;
        this.loyaltyPoints = loyaltyPoints;
        this.isImmediate = isImmediate;
        this.immediateTransactionsCounter = immediateTransactionsCounter;
        this.clientPriceMultiplier = clientPriceMultiplier;
    }

    float charge(float price, String description) {
        float chargeAmount = Math.max(price - clientCredit, 0);
        if (isImmediate) {
            this.immediateTransactionsCounter++;
        } else {
            monthlyPaymentDescriptions.add(description);
        }
        return chargeAmount;
    }

    int addLoyaltyPoints(int minutes, float chargeAmount) {
        int loyaltyPoints = 0;
        if (minutes > 15 && minutes < 50) {
            loyaltyPoints = 4;
            if (isImmediate) {
                loyaltyPoints = 2;
            }
        }

        if (minutes >= 50 && chargeAmount > 30) {
            loyaltyPoints = 20;
        }
        return loyaltyPoints;
    }

    boolean isImmediate() {
        return isImmediate;
    }
}
