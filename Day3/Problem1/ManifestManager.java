import java.util.*;

class Passenger {

    private String passportNumber;
    private String fullName;
    private String nationality;

    public Passenger(String passportNumber, String fullName, String nationality) {
        this.passportNumber = passportNumber;
        this.fullName = fullName;
        this.nationality = nationality;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Passenger)) {
            return false;
        }

        Passenger other = (Passenger) obj;

        return Objects.equals(passportNumber, other.passportNumber)
                && Objects.equals(nationality, other.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passportNumber, nationality);
    }

    @Override
    public String toString() {
        return fullName + " (" + passportNumber + ")";
    }
}

public class ManifestManager {

    private Set<Passenger> globalNoFlyList = new HashSet<>();

    private Map<String, List<Passenger>> flightRosters = new HashMap<>();

    private Map<Passenger, String> globalPassengerDirectory = new HashMap<>();

    public void addToNoFlyList(Passenger passenger) {
        globalNoFlyList.add(passenger);
    }

    public boolean processCheckIn(String flightNumber, Passenger passenger) {

        if (globalNoFlyList.contains(passenger)) {
            return false;
        }

        flightRosters
                .computeIfAbsent(flightNumber, k -> new ArrayList<>())
                .add(passenger);

        globalPassengerDirectory.put(passenger, flightNumber);

        return true;
    }

    public String locatePassengerFlight(Passenger passenger) {
        return globalPassengerDirectory.get(passenger);
    }

    public static void main(String[] args) {

        ManifestManager manager = new ManifestManager();

        Passenger p1 =
                new Passenger("P101", "Alice", "USA");

        Passenger p2 =
                new Passenger("P102", "Bob", "Canada");

        manager.addToNoFlyList(p2);

        System.out.println(
                manager.processCheckIn("AI101", p1));

        System.out.println(
                manager.processCheckIn("AI101", p2));

        System.out.println(
                manager.locatePassengerFlight(p1));
    }
}