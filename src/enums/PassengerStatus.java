package enums;

public enum PassengerStatus {
    NOT_BOARD("not_board"),
    ON_TRIP("on_trip"),
    ARRIVED("arrived");
    private String passengerStatus;

    PassengerStatus(String passengerStatus) {
        this.passengerStatus = passengerStatus;
    }

    public String getPassengerStatus() {
        return passengerStatus;
    }
}
