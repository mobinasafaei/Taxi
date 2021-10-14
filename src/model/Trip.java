package model;

public class Trip {
    private String origin;
    private String destination;
    private String tripStatus;
    private Double cost;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public void calculateCost() {
        String[] originCoordinates = origin.split(",");
        double x1 = Integer.parseInt(originCoordinates[0]);
        double y1 = Integer.parseInt(originCoordinates[1]);

        String[] destinationCoordinates =destination.split(",");
        double x2 = Integer.parseInt(destinationCoordinates[0]);
        double y2 = Integer.parseInt(destinationCoordinates[1]);

        cost = Math.floor(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2))) * 1000;
    }

    public Double getCost() {
        return cost;
    }
}

