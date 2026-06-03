import java.util.concurrent.atomic.AtomicInteger;

class DroneHive {

    private AtomicInteger totalDronesReturned = new AtomicInteger(0);

    private volatile boolean emergencyAbort = false;

    public void droneReturned() {
        totalDronesReturned.incrementAndGet();
    }

    public int getTotalDronesReturned() {
        return totalDronesReturned.get();
    }

    public void triggerEmergencyAbort() {
        emergencyAbort = true;
    }

    public boolean isEmergencyAbort() {
        return emergencyAbort;
    }
}