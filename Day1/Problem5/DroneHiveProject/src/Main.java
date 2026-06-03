public class Main {

    public static void main(String[] args) throws InterruptedException {

        DroneHive hive = new DroneHive();

        Thread radar = new Thread(() -> {

            try {
                Thread.sleep(1000);
            }

            catch(Exception e) {
            }

            hive.triggerEmergencyAbort();

            System.out.println("Storm detected. Emergency Abort Activated");
        });

        radar.start();

        Thread[] drones = new Thread[10];

        for(int i = 0; i < drones.length; i++) {

            drones[i] = new Thread(() -> {

                hive.droneReturned();

                while(!hive.isEmergencyAbort()) {
                }

                System.out.println(Thread.currentThread().getName() + " rerouting");
            });

            drones[i].start();
        }

        for(Thread drone : drones)
            drone.join();

        radar.join();

        System.out.println("Total Drones Returned: " + hive.getTotalDronesReturned());
    }
}