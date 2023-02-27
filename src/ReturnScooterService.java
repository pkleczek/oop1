class ReturnScooterService {

    void returnScooter(Long clientId, Long scooterId, Position where, int minutes) {
        Scooter scooter = loadScooter(scooterId);
        Client client = loadClient(clientId);

        float price = scooter.price(minutes);
        float chargedAmount = client.charge(price, scooter.description());
        if (client.isImmediate()) {
            chargedAmount = price * 0.9f;
        }
        chargeClient(clientId, chargedAmount);
        client.addLoyaltyPoints(minutes, chargedAmount);
        scooter.scheduleForMaintenance(where);

        saveInDatabase(client, scooter);
    }

    private Client loadClient(Long clientId) {
        //ładowanie z bazy danych po clientId
        return null;
    }

    private void chargeClient(Long clientId, float chargeAmount) {
        //obciążenie karty kredytowej
    }

    private Scooter loadScooter(Long scooterId) {
        //ładowanie z bazy danych po scooterID
        return null;
    }

    private void saveInDatabase(Client client, Scooter scooter) {
        //zapis do bazy danych
    }

}

class Position {
    private float latitude;
    private float longitude;
}

