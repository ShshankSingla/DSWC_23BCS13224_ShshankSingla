class MiningShip extends SpaceVessel {

    // 2D Array for cargo hold
    // float saves memory and supports decimals
    private float[][] cargoHold;

    public MiningShip(short shipId,
                      boolean operationalStatus,
                      char fleetClassification,
                      float[][] cargoHold) {

        super(shipId, operationalStatus, fleetClassification);

        this.cargoHold = cargoHold;
    }

    // Calculate total ore weight
    public float calculateTotalOreWeight() {

        float total = 0;

        for (int i = 0; i < cargoHold.length; i++) {

            for (int j = 0; j < cargoHold[i].length; j++) {

                total += cargoHold[i][j];
            }
        }

        return total;
    }

    // Find heaviest container
    public float findHeaviestContainer() {

        float max = cargoHold[0][0];

        for (int i = 0; i < cargoHold.length; i++) {

            for (int j = 0; j < cargoHold[i].length; j++) {

                if (cargoHold[i][j] > max) {

                    max = cargoHold[i][j];
                }
            }
        }

        return max;
    }
}