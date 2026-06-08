import java.util.PriorityQueue;

enum TriageLevel {
    CRITICAL,
    URGENT,
    STABLE
}

class Patient implements Comparable<Patient> {

    private String name;
    private TriageLevel severity;
    private long arrivalTime;

    public Patient(String name,
                   TriageLevel severity,
                   long arrivalTime) {

        this.name = name;
        this.severity = severity;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public int compareTo(Patient other) {

        if (this.severity != other.severity) {

            return Integer.compare(
                    this.severity.ordinal(),
                    other.severity.ordinal()
            );
        }

        return Long.compare(
                this.arrivalTime,
                other.arrivalTime
        );
    }

    @Override
    public String toString() {
        return name + " - " + severity;
    }
}

public class TriageManager {

    private PriorityQueue<Patient> queue =
            new PriorityQueue<>();

    public void admitPatient(Patient patient) {
        queue.offer(patient);
    }

    public Patient getNextPatient() {
        return queue.poll();
    }

    public static void main(String[] args) {

        TriageManager manager =
                new TriageManager();

        manager.admitPatient(
                new Patient(
                        "John",
                        TriageLevel.URGENT,
                        100
                )
        );

        manager.admitPatient(
                new Patient(
                        "Mary",
                        TriageLevel.CRITICAL,
                        200
                )
        );

        manager.admitPatient(
                new Patient(
                        "David",
                        TriageLevel.CRITICAL,
                        150
                )
        );

        System.out.println(
                manager.getNextPatient());

        System.out.println(
                manager.getNextPatient());

        System.out.println(
                manager.getNextPatient());
    }
}