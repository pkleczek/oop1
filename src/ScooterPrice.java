interface ScooterPrice {

    float price(int minutes);
}

class RegularPrice implements ScooterPrice {

    private Long scooterId;
    private float unlocking;
    private float pricePerMinute;

    RegularPrice(Long scooterId, Object[] scooterData) {
        this.scooterId = scooterId;
        this.unlocking = (float) scooterData[1];
        this.pricePerMinute = (float) scooterData[2];
    }

    @Override
    public float price(int minutes) {
        return minutes * pricePerMinute + unlocking;
    }
}


class FastChargerPrice implements ScooterPrice {

    private Long scooterId;
    private float unlocking;
    private float pricePerMinute;
    private float chargingPrice;

    FastChargerPrice(Long scooterId, Object[] scooterData) {
        this.scooterId = scooterId;
        this.unlocking = (float) scooterData[1];
        this.pricePerMinute = (float) scooterData[2];
        //dodana dodatkowa opłata za szybkie ładowanie
        this.chargingPrice = (float) scooterData[16];
    }

    @Override
    public float price(int minutes) {
        return minutes * pricePerMinute + unlocking + chargingPrice;
    }
}