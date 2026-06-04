abstract class DeliveryDrone {
    protected String droneId;

    public DeliveryDrone(String droneId) {
        this.droneId = droneId;
    }

    public abstract void deliverPackage();
}

interface Airborne {

    void flyToDestination();

    default void requestAirTrafficClearance() {
        System.out.println("Air traffic clearance granted.");
    }
}

interface GroundBased {

    void navigateSidewalks();
}

class Quadcopter extends DeliveryDrone implements Airborne {

    public Quadcopter(String droneId) {
        super(droneId);
    }

    @Override
    public void flyToDestination() {
        System.out.println("Quadcopter flying to destination.");
    }

    @Override
    public void deliverPackage() {
        requestAirTrafficClearance();
        flyToDestination();
        System.out.println("Quadcopter delivered the package.");
    }
}

class CityRover extends DeliveryDrone implements GroundBased {

    public CityRover(String droneId) {
        super(droneId);
    }

    @Override
    public void navigateSidewalks() {
        System.out.println("CityRover navigating sidewalks.");
    }

    @Override
    public void deliverPackage() {
        navigateSidewalks();
        System.out.println("CityRover delivered the package.");
    }
}

class HybridVTOL extends DeliveryDrone
        implements Airborne, GroundBased {

    public HybridVTOL(String droneId) {
        super(droneId);
    }

    @Override
    public void flyToDestination() {
        System.out.println("HybridVTOL flying to destination.");
    }

    @Override
    public void navigateSidewalks() {
        System.out.println("HybridVTOL navigating sidewalks.");
    }

    @Override
    public void deliverPackage() {
        requestAirTrafficClearance();
        flyToDestination();
        navigateSidewalks();
        System.out.println("HybridVTOL delivered the package.");
    }
}

public class AeroLogixSystem {

    public static void main(String[] args) {

        DeliveryDrone drone1 = new Quadcopter("Q101");
        DeliveryDrone drone2 = new CityRover("C201");
        DeliveryDrone drone3 = new HybridVTOL("H301");

        drone1.deliverPackage();
        System.out.println();

        drone2.deliverPackage();
        System.out.println();

        drone3.deliverPackage();
    }
}