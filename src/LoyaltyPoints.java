interface LoyaltyPoints {

    int calculate(int minutes, float chargedAmount);
}


class IndividualAccount implements LoyaltyPoints {

    private Long pointsAccountId;
    private int newLoyaltyPoints;
    private String clientType;

    IndividualAccount(int loyaltyPoints, String clientType, Long pointsAccountId) {
        this.pointsAccountId = pointsAccountId;
        this.newLoyaltyPoints = loyaltyPoints;
        this.clientType = clientType;
    }

    @Override
    public int calculate(int minutes, float chargedAmount) {
        if (minutes > 15 && minutes < 50) {
            newLoyaltyPoints = 4;
            if (clientType.equals("IMMEDIATE")) {
                newLoyaltyPoints = 2;
            }
        }
        if (minutes >= 50 && chargedAmount > 30) {
            newLoyaltyPoints = 20;
        }
        this.newLoyaltyPoints += newLoyaltyPoints;
        return newLoyaltyPoints;
    }

}

class GroupAccount implements LoyaltyPoints {

    private Long pointsAccountId;
    private int loyaltyPoints;

    public GroupAccount(Long pointsAccountId, int loyaltyPoints) {
        this.pointsAccountId = pointsAccountId;
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public int calculate(int minutes, float chargedAmount) {
        int newLoyaltyPoints = 0;
        if (minutes > 200) {
            newLoyaltyPoints = 15;
        } else {
            newLoyaltyPoints = 5;
        }
        this.loyaltyPoints += newLoyaltyPoints;
        return newLoyaltyPoints;
    }

}