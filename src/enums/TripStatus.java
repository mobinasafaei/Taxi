package enums;

public enum TripStatus {
    ONGOING("ONGOING"),
    FINISHED("FINISHED");
    private String tripStatus;

    TripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getTripStatus() {
        return tripStatus;
    }
}
