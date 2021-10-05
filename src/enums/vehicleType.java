package enums;

public enum vehicleType {
    CAR("car"),
    MOTORCYCLE("motorcycle"),
    VAN("van");
    private String vehicleType;

    public String getVehicleType() {
        return vehicleType;
    }

    vehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
