package oop1;

class ReturnScooterService {
    void returnScooter(Long clientId, Long scooterId, Position where, int minutes, float batteryLevel, Object[] scooterData,
                       float clientCredit, boolean clientWithImmediatePayment, int immediateTransactionsCounter) {
        //metoda returnScooter ma 4 parametry - clientId, scooterId, where, minutes
        //resztę pobieramy na podstawię clientId i scooterId z bazy
        //(batteryLevel, Object[] scooterData, float clientCredit, boolean clientWithImmediatePayment, int immediateTransactionsCounter)
        //kod celowo nie jest najpiękniejszy

        float unlocking = 0.0f;
        float pricePerMinute = 0.0f;
        if (scooterData[0].equals("not_fast")) {
            unlocking = (float) scooterData[1];
            pricePerMinute = (float) scooterData[2];
        } else {
            unlocking = (float) scooterData[3];
            pricePerMinute = (float) scooterData[4];
        }

        float chargeAmount;
        float priceAmountClientMultiplicationFactor = 0.9f;
        if (clientWithImmediatePayment) {
            priceAmountClientMultiplicationFactor = 0.9f;
        } else {
            priceAmountClientMultiplicationFactor = 1;
        }
        float price = unlocking + pricePerMinute * minutes * priceAmountClientMultiplicationFactor;
        chargeAmount = Math.max(price - clientCredit, 0);
        chargeClient(clientId, chargeAmount);
        boolean needsToChargeBattery = false;
        if (clientWithImmediatePayment) {
            immediateTransactionsCounter++;
        }
        if (batteryLevel < 0.07) {
            needsToChargeBattery = true;
        }
        int loyaltyPoints = 0;
        if (minutes > 15 && minutes < 50) {
            loyaltyPoints = 4;
            if (priceAmountClientMultiplicationFactor < 1) {
                loyaltyPoints = 2;
            }
        }

        if (minutes >= 50 && chargeAmount > 30) {
            loyaltyPoints = 20;
        }
        saveInDatabase(loyaltyPoints, chargeAmount, needsToChargeBattery, immediateTransactionsCounter);
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
