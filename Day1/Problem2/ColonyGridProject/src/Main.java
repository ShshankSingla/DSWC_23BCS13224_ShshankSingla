
public class Main {

    public static void main(String[] args) {

        ColonyPowerManager manager = new ColonyPowerManager();
        manager.turnOnSector(0);
        manager.turnOnSector(3);
        manager.turnOnSector(7);

        manager.displaySectorStates();

        System.out.println("Is Sector 3 ON? "+ manager.isSectorOn(3));

        manager.turnOffSector(3);
        manager.displaySectorStates();
        System.out.println("Is Sector 3 ON? "+ manager.isSectorOn(3));
    }
}