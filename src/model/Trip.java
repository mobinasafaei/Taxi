package model;

public class Trip {
    private Double origin;
    private Double destination;
    private Double cost;

    public Double getOrigin() {
        return origin;
    }

    public void setOrigin(Double origin) {
        this.origin = origin;
    }

    public Double getDestination() {
        return destination;
    }

    public void setDestination(Double destination) {
        this.destination = destination;
    }

    public void calculateCost() {
       this.cost= Math.abs(destination-origin)*1000;
    }
}

