package enums;

public enum VehicleType {
    CAR("car"),
    MOTORCYCLE("motorcycle"),
    VAN("van");
    private String vehicleType;

    public String getVehicleType() {
        return vehicleType;
    }

    VehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
