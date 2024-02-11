package oop1;

interface DatabaseDelegate {
    void saveInDatabase(int loyaltyPoints, float chargeAmount, boolean needsToChargeBattery, int immediateTransactionsCounter);
}

interface ChargeDelegate {
    void chargeClient(Long clientId, float chargeAmount);
}

class ReturnScooterService {
    private final DatabaseDelegate databaseDelegate;
    private final ChargeDelegate chargeDelegate;

    ReturnScooterService(DatabaseDelegate databaseDelegate, ChargeDelegate chargeDelegate) {
        this.databaseDelegate = databaseDelegate;
        this.chargeDelegate = chargeDelegate;
    }

    void returnScooter(Long clientId, Long scooterId, Position where, int minutes, float batteryLevel, Object[] scooterData,
                       float clientCredit, boolean clientWithImmediatePayment, int immediateTransactionsCounter) {
        Pricing pricing = getPricing(scooterData);
        Client client = getClient(clientId, clientCredit, clientWithImmediatePayment, immediateTransactionsCounter);
        Battery battery = new Battery(batteryLevel, 0.07f);

        returnScooter(scooterId, where, minutes, battery, pricing, client);

    }

    private static Pricing getPricing(Object[] scooterData) {
        if (scooterData[0].equals("not_fast")) {
            return new Pricing((float) scooterData[1], (float) scooterData[2]);
        } else {
            return new Pricing((float) scooterData[3], (float) scooterData[4]);
        }
    }

    private static Client getClient(Long clientId, float clientCredit, boolean clientWithImmediatePayment, int immediateTransactionsCounter) {
        if (clientWithImmediatePayment) {
            return new ClientWithImmediatePayments(clientId, clientCredit, immediateTransactionsCounter);
        }
        return new ClientWithoutImmediatePayments(clientId, clientCredit);
    }


    void returnScooter(Long scooterId, Position where, int minutes, Battery battery, Pricing pricing, Client client) {
        //metoda returnScooter ma 4 parametry - clientId, scooterId, where, minutes
        //resztę pobieramy na podstawię clientId i scooterId z bazy
        //(batteryLevel, Object[] scooterData, float clientCredit, boolean clientWithImmediatePayment, int immediateTransactionsCounter)
        //kod celowo nie jest najpiękniejszy

        float price = pricing.total(client, minutes);

        float leftToCharge = client.charge(price);

        chargeClient(client.clientId, leftToCharge);

        int loyaltyPoints = 0;
        if (minutes > 15 && minutes < 50) {
            loyaltyPoints = 4;
            if (priceAmountClientMultiplicationFactor < 1) {
                loyaltyPoints = 2;
            }
        }

        if (minutes >= 50 && leftToCharge > 30) {
            loyaltyPoints = 20;
        }
        saveInDatabase(loyaltyPoints, leftToCharge, battery.mustRecharge(), immediateTransactionsCounter);
    }

    private void saveInDatabase(int loyaltyPoints, float chargeAmount, boolean needsToChargeBattery, int immediateTransactionsCounter) {
        databaseDelegate.saveInDatabase(loyaltyPoints, chargeAmount, needsToChargeBattery, immediateTransactionsCounter);
    }

    private void chargeClient(Long clientId, float chargeAmount) {
        chargeDelegate.chargeClient(clientId, chargeAmount);
    }

}

record Pricing(float unlocking, float pricePerMinute) {
    float total(Client client, int minutes) {
        float factor = switch (client) {
            case ClientWithImmediatePayments withImmediatePayments -> 0.9f;
            case ClientWithoutImmediatePayments withoutImmediatePayments -> 1.0f;
        };
        return unlocking + pricePerMinute * minutes * factor;
    }
}

record Battery(float currentLevel, float minLevel) {
    boolean mustRecharge() {
        return currentLevel < minLevel;
    }

}

class Position {
    private float latitude;
    private float longitude;
}

sealed abstract class Client permits ClientWithImmediatePayments, ClientWithoutImmediatePayments {
    public final Long clientId;
    private float credit;

    public Client(Long clientId, float credit) {
        this.clientId = clientId;
        this.credit = credit;
    }

    void registerTransaction() {
    }

    float charge(float amount) {
        registerTransaction();
        float toCharge = Math.max(amount - credit, 0);
        credit = Math.max(credit - amount, 0);
        return toCharge;
    }
}

final class ClientWithImmediatePayments extends Client {
    private int immediateTransactionsCounter;

    public ClientWithImmediatePayments(Long clientId, float credit, int immediateTransactionsCounter) {
        super(clientId, credit);
        this.immediateTransactionsCounter = immediateTransactionsCounter;
    }

    @Override
    void registerTransaction() {
        immediateTransactionsCounter += 1;
    }
}

final class ClientWithoutImmediatePayments extends Client {
    public ClientWithoutImmediatePayments(Long clientId, float credit) {
        super(clientId, credit);
    }
}

