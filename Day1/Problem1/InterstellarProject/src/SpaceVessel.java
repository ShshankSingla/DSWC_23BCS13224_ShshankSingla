abstract class SpaceVessel {

    // Max 30,000 → short
    protected short shipId;

    // true / false
    protected boolean operationalStatus;

    // 'A', 'B', 'C'
    protected char fleetClassification;

    public SpaceVessel(short shipId,
                        boolean operationalStatus,
                        char fleetClassification) {

        this.shipId = shipId;
        this.operationalStatus = operationalStatus;
        this.fleetClassification = fleetClassification;
    }

    public void displayDetails() {

        System.out.println("Ship ID: " + shipId);
        System.out.println("Operational: " + operationalStatus);
        System.out.println("Fleet Class: " + fleetClassification);
    }
}