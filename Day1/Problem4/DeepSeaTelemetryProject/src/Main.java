public class Main {

    public static void main(String[] args) {

        try(TelemetryStream stream = new TelemetryStream()) {

            stream.readData();
        }

        catch(HardwareLockException e) {
            System.out.println("Checked Exception: " + e.getMessage());
        }

        catch(SensorCorruptionException e) {
            System.out.println("Unchecked Exception: " + e.getMessage());
        }
    }
}