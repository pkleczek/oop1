import java.util.List;

class Client {

    private Long clientId;
    private float clientCredit;
    private int loyaltyPoints;
    private String clientType;
    private int immediateTransactionsCounter;
    private List<String> monthlyPaymentDescriptions;
    private String pointsType;

    Client(Long clientId,
           float clientCredit,
           int loyaltyPoints,
           String clientType,
           int immediateTransactionsCounter,
           List<String> monthlyPaymentDescriptions,
           String pointsType) {
        this.clientId = clientId;
        this.clientCredit = clientCredit;
        this.loyaltyPoints = loyaltyPoints;
        this.clientType = clientType;
        this.immediateTransactionsCounter = immediateTransactionsCounter;
        this.monthlyPaymentDescriptions = monthlyPaymentDescriptions;
        this.pointsType = pointsType;
    }

    float charge(float price, String description) {
        float chargeAmount = Math.max(price - clientCredit, 0);
        if (clientType.equals("IMMEDIATE")) {
            this.immediateTransactionsCounter++;
        } else if (clientType.equals("MONTHLY_BILLED")) {
            monthlyPaymentDescriptions.add(description);
        }
        return chargeAmount;
    }

    int addLoyaltyPoints(int minutes, float chargeAmount) {
        int loyaltyPoints = 0;
        if (minutes > 200 && pointsType.equals("GROUP")) {
            loyaltyPoints = 15;
        } else if (pointsType.equals("GROUP")) {
            loyaltyPoints = 5;
        } else if (pointsType.equals("INDIVIDUAL")) {
            if (minutes > 15 && minutes < 50) {
                loyaltyPoints = 4;
                if (isImmediate()) {
                    loyaltyPoints = 2;
                }
            }
            if (minutes >= 50 && chargeAmount > 30) {
                loyaltyPoints = 20;
            }
        }
        return loyaltyPoints;
    }

    boolean isImmediate() {
        return clientType.equals("IMMEDIATE");
    }
}
