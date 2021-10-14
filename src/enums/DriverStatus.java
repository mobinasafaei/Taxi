package enums;

public enum DriverStatus {
    WAIT_FOR_TRIP("WAIT_FOR_TRIP"),
    ON_TRIP("ON_TRIP");

    private String driverStatus;

    DriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getDriverStatus() {
        return driverStatus;
    }
}
