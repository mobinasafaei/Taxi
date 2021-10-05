package enums;

public enum VehicleColor {
    BLUE("blue"),
    WHITE("white"),
    BLACK("black");

    private String vehicleColor;

    VehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }
}
