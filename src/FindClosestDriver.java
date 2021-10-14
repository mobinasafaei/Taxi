import databasesAccess.DriverDatabaseAccess;
import model.Trip;

import java.util.ArrayList;

public class FindClosestDriver {
    DriverDatabaseAccess driverDatabaseAccess = new DriverDatabaseAccess();
    ArrayList<String> locations = driverDatabaseAccess.getDriverLocations();
    double[] distances = new double[locations.size()];
    private int driverId;
    boolean driverFound=true;

    FindClosestDriver(Trip trip) {
        if (locations.size() == 0) {
            driverFound=false;
        } else {
            for (int i = 0; i < locations.size(); i++) {
                distances[i] = calculateDistance(trip, i);
            }
            int minIndex = findClosestDriver(locations.size() - 1, Integer.MAX_VALUE, 0);
            String closestDriverLocation = locations.get(minIndex);
            driverId = driverDatabaseAccess.findDriverByLocation(closestDriverLocation);
        }
    }

    public boolean isDriverFound() {
        return driverFound;
    }

    public int getDriverId() {
        return driverId;
    }

    public double calculateDistance(Trip trip, int n) {
        String origin = trip.getOrigin();
        String destination = locations.get(n);


        String[] originCoordinates = origin.split(",");
        double x1 = Integer.parseInt(originCoordinates[0]);
        double y1 = Integer.parseInt(originCoordinates[1]);

        String[] destinationCoordinates = destination.split(",");
        double x2 = Integer.parseInt(destinationCoordinates[0]);
        double y2 = Integer.parseInt(destinationCoordinates[1]);
        return Math.floor(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));

    }

    public int findClosestDriver(int n, double min, int minIndex) {
        if (n < 0) {
            return minIndex;
        }
        if (min > distances[n]) {
            min = distances[n];
            minIndex = n;
        }
        return findClosestDriver(n - 1, min, minIndex);
    }
}
