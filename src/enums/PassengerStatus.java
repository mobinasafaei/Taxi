package enums;

public enum PassengerStatus {
    FREE("FREE"),
    ON_TRIP("ON_TRIP");
    private String passengerStatus;

    PassengerStatus(String passengerStatus) {
        this.passengerStatus = passengerStatus;
    }

    public String getPassengerStatus() {
        return passengerStatus;
    }
}
