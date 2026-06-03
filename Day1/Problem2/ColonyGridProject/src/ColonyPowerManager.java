
class ColonyPowerManager {

    private byte sectorStates = 0;

    public void turnOnSector(int sectorIndex) {
        sectorStates = (byte) (sectorStates | (1 << sectorIndex));
    }

    public void turnOffSector(int sectorIndex) {
        sectorStates = (byte) (sectorStates & ~(1 << sectorIndex));
    }

    public boolean isSectorOn(int sectorIndex) {
        return (sectorStates & (1 << sectorIndex)) != 0;
    }

    public void displaySectorStates() {

        String binary = String.format("%8s", Integer.toBinaryString(sectorStates & 0xFF)).replace(' ', '0');

        System.out.println("Sector States: " + binary);
    }
}