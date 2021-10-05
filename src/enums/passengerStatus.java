package enums;

public enum passengerStatus {
    NOT_BOARD("not_board"),
    ON_TRIP("on_trip"),
    ARRIVED("arrived");
    private String passengerStatus;

    passengerStatus(String passengerStatus) {
        this.passengerStatus = passengerStatus;
    }

    public String getPassengerStatus() {
        return passengerStatus;
    }
}
