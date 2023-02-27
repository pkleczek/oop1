public class Scooter {

    private Long scooterId;
    private Object[] scooterData;
    private float batteryLevel;
    private boolean scheduleForMaintenance;

    Scooter(Long scooterId, Object[] scooterData, float batteryLevel) {
        this.scooterId = scooterId;
        this.scooterData = scooterData;
        this.batteryLevel = batteryLevel;
    }

    float price(int minutes) {
        float unlocking;
        float pricePerMinute;
        if (scooterData[0].equals("not_fast")) {
            unlocking = (float) scooterData[1];
            pricePerMinute = (float) scooterData[2];
        } else {
            unlocking = (float) scooterData[3];
            pricePerMinute = (float) scooterData[4];
        }
        return minutes * pricePerMinute + unlocking;
    }

    boolean scheduleForMaintenance(Position where) {
        if (scooterData[0].equals("fast")) {
            scheduleForMaintenance = true;
            return scheduleForMaintenance;
        }
        if (batteryLevel < 0.07) {
            scheduleForMaintenance = true;
        }
        return scheduleForMaintenance;
    }

    String description() {
        return scooterId + " " + (String) scooterData[0];
    }
}


