class ReturnScooterService {

    void returnScooter(Long clientId, Long scooterId, Position where, int minutes) {
        //ladowanie scooterPrice, payer, points, schedule, description to nie musi być
        //5 zawołan bazy danych - mogą być joiny, wspólne tabele, etc
        ScooterPrice scooterPrice = loadScooterPrice(scooterId); //albo scooterPrice.description();
        Payer payer = loadPayer(clientId);
        LoyaltyPoints points = loadPoints(clientId);
        MaintenanceSchedule schedule = loadSchedule(scooterId);

        float price = scooterPrice.price(minutes);
        float chargedAmount = payer.charge(price, loadDescription(scooterId));
        chargedAmount = payer.applyDiscount(chargedAmount);
        chargePayer(clientId, chargedAmount);
        points.calculate(minutes, chargedAmount);
        schedule.scheduleForMaintenance(where);

        saveInDatabase(payer, points, scooterPrice, schedule);
    }

    private Payer loadPayer(Long clientId) {
        // |---partyId---|-----payer-----|---points---|
        // |------1------|---immediate---|----flat----|
        // |------2------|---immediate---|----flat----|
        // |------3------|----monthly----|---group----|
        // |------4------|----monthly----|---group----|
        // |------------------------------------------|
        //znajdz przypisanego do clientId Payera
        return null;
    }

    private LoyaltyPoints loadPoints(Long clientId) {
        //znajdz przypisany do clientId algorytm LoyaltyPoints
        return null;
    }

    private MaintenanceSchedule loadSchedule(Long scooterId) {
        //znajdz przypisany do scooterId kalendarz napraw
        return null;
    }

    private ScooterPrice loadScooterPrice(Long scooterId) {
        //znajdz przypisany do scooterId cennik
        return null;
    }

    private String loadDescription(Long scooterId) {
        //znajdz opis hulajnogi
        return null;
    }

    private void chargePayer(Long payerId, float chargeAmount) {
        //obciążenie karty kredytowej (od razu lub po miesiącu)
    }

    private void saveInDatabase(Payer payer, LoyaltyPoints points, ScooterPrice scooterPrice, MaintenanceSchedule schedule) {
        //zapis do bazy danych
    }

}

class Position {
    private float latitude;
    private float longitude;
}

