class ReturnScooterService {
    void returnScooter(Long clientId, Long scooterId, Position where, int minutes, float batteryLevel, Object[] scooterData,
                       float clientCredit, boolean clientWithImmediatePayment, int immediateTransactionsCounter) {
        //metoda returnScooter ma 4 parametry - clientId, scooterId, where, minutes
        //resztę pobieramy na podstawię clientId i scooterId z bazy
        //(batteryLevel, Object[] scooterData, float clientCredit, boolean clientWithImmediatePayment, int immediateTransactionsCounter)
        //kod celowo nie jest najpiękniejszy

        Pricing pricing = getPricing(scooterData);
        float priceFactor = priceFactor(clientWithImmediatePayment);
        float price = pricing.unlocking() + pricing.pricePerMinute() * minutes * priceFactor;
        float chargeAmount = Math.max(price - clientCredit, 0);
        boolean needsToChargeBattery = batteryRechargeNeeded(batteryLevel);
        int loyaltyPoints = getLoyaltyPoints(minutes, priceFactor, chargeAmount);
        if (clientWithImmediatePayment) {
            immediateTransactionsCounter++;
        }

        chargeClient(clientId, chargeAmount);
        saveInDatabase(loyaltyPoints, chargeAmount, needsToChargeBattery, immediateTransactionsCounter);
    }

    private static int getLoyaltyPoints(int minutes, float priceFactor, float chargeAmount) {
        if (minutes > 15 && minutes < 50) {
            if (priceFactor < 1) {
                return 2;
            } else {
                return 4;
            }
        }

        if (minutes >= 50 && chargeAmount > 30) {
            return 20;
        }

        return 0;
    }

    private static boolean batteryRechargeNeeded(float batteryLevel) {
        return batteryLevel < 0.07;
    }

    private static float priceFactor(boolean clientWithImmediatePayment) {
        if (clientWithImmediatePayment) {
            return 0.9f;
        } else {
            return 1;
        }
    }

    private static Pricing getPricing(Object[] scooterData) {
        if (scooterData[0].equals("not_fast")) {
            return new Pricing((float) scooterData[1], (float) scooterData[2]);
        } else {
            return new Pricing((float) scooterData[3], (float) scooterData[4]);
        }
    }

    private record Pricing(float unlocking, float pricePerMinute) {
    }

    private void saveInDatabase(int loyaltyPoints, float chargeAmount, boolean needsToChargeBattery, int immediateTransactionsCounter) {
        //zapis wszystkigo do bazy danych
    }

    private void chargeClient(Long clientId, float chargeAmount) {
        //obciążenie karty kredytowej
    }

}

class Position {
    private float latitude;
    private float longitude;
}
