public class Main {

    public static void main(String[] args) {

        // 2D cargo hold
        float[][] cargo = {

                {1200.5f, 3400.75f, 1500.25f},
                {5000.0f, 2200.4f},
                {9999.99f, 7500.5f}
        };

        // Create MiningShip object
        MiningShip ship1 = new MiningShip(
                (short) 101,
                true,
                'A',
                cargo
        );

        // 1D Array of Objects
        SpaceVessel[] fleet = new SpaceVessel[1];

        fleet[0] = ship1;

        // Display details
        ship1.displayDetails();

        // Total ore weight
        System.out.println(
                "Total Ore Weight: "
                        + ship1.calculateTotalOreWeight()
                        + " kg"
        );

        // Heaviest container
        System.out.println(
                "Heaviest Container: "
                        + ship1.findHeaviestContainer()
                        + " kg"
        );
    }
}