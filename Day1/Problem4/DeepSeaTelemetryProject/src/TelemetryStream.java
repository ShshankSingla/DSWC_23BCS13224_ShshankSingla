class TelemetryStream implements AutoCloseable {

    public void readData() throws HardwareLockException {

        double temperature = 500;

        if(temperature > 300)
            throw new SensorCorruptionException("Impossible temperature detected");

        boolean hardwareLocked = false;

        if(hardwareLocked)
            throw new HardwareLockException("Telemetry file locked");
    }

    public void close() {
        System.out.println("Telemetry Stream Closed");
    }
}