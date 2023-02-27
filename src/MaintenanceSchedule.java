import java.time.Duration;
import java.time.Instant;

interface MaintenanceSchedule {

    boolean scheduleForMaintenance(Position position);
}


class BasedOnBattery implements MaintenanceSchedule {

    private Long vehicleId;
    private float batteryLevel;
    private boolean scheduledForMaintenance;

    BasedOnBattery(float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public boolean scheduleForMaintenance(Position where) {
        if (batteryLevel < 0.07) {
            scheduledForMaintenance = true;
        }
        return scheduledForMaintenance;
    }
}

class AlwaysCheck implements MaintenanceSchedule {

    private Long vehicleId;
    private boolean scheduledForMaintenance;

    AlwaysCheck(Long vehicleId, boolean scheduledForMaintenance) {
        this.vehicleId = vehicleId;
        this.scheduledForMaintenance = scheduledForMaintenance;
    }

    @Override
    public boolean scheduleForMaintenance(Position where) {
        this.scheduledForMaintenance = true;
        return true;
    }
}

class BasedOnTime implements MaintenanceSchedule {

    private Long vehicleId;
    private Instant lastCheck;
    private boolean scheduledForMaintenance;

    BasedOnTime(Long vehicleId, Instant lastCheck, boolean scheduledForMaintenance) {
        this.vehicleId = vehicleId;
        this.lastCheck = lastCheck;
        this.scheduledForMaintenance = scheduledForMaintenance;
    }

    @Override
    public boolean scheduleForMaintenance(Position where) {
        //wypadałoby użyć implementacji czasu (now(clock)), zamiast now();
        Instant now = Instant.now();
        if (Duration.between(lastCheck, now).toDays() > 10) {
            scheduledForMaintenance = true;
            lastCheck = now;
        }
        return scheduledForMaintenance;
    }
}