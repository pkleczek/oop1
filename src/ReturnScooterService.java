class ReturnScooterService {

    void returnScooter(Long clientId, Long scooterId, Position where, int minutes) {
        Scooter scooter = loadScooter(scooterId);
        Payer payer = loadPayer(clientId);
        LoyaltyPoints points = loadPoints(clientId);

        float price = scooter.price(minutes);
        float chargedAmount = payer.charge(price, scooter.description());
        if (payer.isImmediate()) {
            chargedAmount = price * 0.9f;
        }
        chargePayer(clientId, chargedAmount);
        points.calculate(minutes, chargedAmount);
        scooter.scheduleForMaintenance(where);

        saveInDatabase(payer, points, scooter);
    }


    private Payer loadPayer(Long clientId) {
        //znajdz przypisanego do clientId Payera
        return null;
    }

    private LoyaltyPoints loadPoints(Long clientId) {
        //znajdz przypisany do clientId algorytm LoyaltyPoints
        return null;
    }
    private void chargePayer(Long payerId, float chargeAmount) {
        //obciążenie karty kredytowej (od razu lub po miesiącu)
    }

    private Scooter loadScooter(Long scooterId) {
        //ładowanie z bazy danych po scooterID
        return null;
    }

    private void saveInDatabase(Payer payer, LoyaltyPoints points, Scooter scooter) {
        //zapis do bazy danych
    }

}

class Position {
    private float latitude;
    private float longitude;
}

