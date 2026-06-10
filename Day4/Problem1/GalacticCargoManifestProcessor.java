
import java.util.*;
import java.util.stream.Collectors;

abstract class Cargo {
    protected String containerId;
    protected double valueInCredits;
    protected boolean isHazardous;

    public Cargo(String containerId, double valueInCredits, boolean isHazardous) {
        this.containerId = containerId;
        this.valueInCredits = valueInCredits;
        this.isHazardous = isHazardous;
    }

    public String getContainerId() {
        return containerId;
    }

    public double getValueInCredits() {
        return valueInCredits;
    }

    public boolean isHazardous() {
        return isHazardous;
    }
}

class StandardCargo extends Cargo {
    public StandardCargo(String containerId, double valueInCredits, boolean isHazardous) {
        super(containerId, valueInCredits, isHazardous);
    }
}

class BiologicalCargo extends Cargo {
    private boolean isShielded;

    public BiologicalCargo(String containerId, double valueInCredits,
                           boolean isHazardous, boolean isShielded) {
        super(containerId, valueInCredits, isHazardous);
        this.isShielded = isShielded;
    }

    public boolean isShielded() {
        return isShielded;
    }
}

@FunctionalInterface
interface CargoInspector {
    boolean isSafe(Cargo cargo);
}

@FunctionalInterface
interface CargoCompressor {
    String compress(Cargo cargo);
}

class ManifestProcessor {

    public List<String> processManifest(
            List<Cargo> manifest,
            CargoInspector inspector,
            CargoCompressor compressor) {

        return manifest.stream()
                .filter(inspector::isSafe)
                .filter(cargo -> cargo.getValueInCredits() >= 1000.0)
                .map(compressor::compress)
                .collect(Collectors.toList());
    }
}

public class GalacticCargoManifestProcessor {

    public static void main(String[] args) {

        List<Cargo> manifest = Arrays.asList(
                new StandardCargo("ALPHA-99", 5000.50, false),
                new BiologicalCargo("BETA-01", 8000.00, true, false),
                new BiologicalCargo("GAMMA-77", 12000.75, true, true),
                new StandardCargo("DELTA-55", 500.00, false),
                new StandardCargo("OMEGA-11", 2500.00, true)
        );

        CargoInspector inspector = cargo -> {
            if (cargo.isHazardous()
                    && cargo instanceof BiologicalCargo
                    && !((BiologicalCargo) cargo).isShielded()) {
                return false;
            }
            return true;
        };

        CargoCompressor compressor = cargo ->
                cargo.getContainerId().substring(0, 4)
                        + "-"
                        + (int) cargo.getValueInCredits();

        ManifestProcessor processor = new ManifestProcessor();

        List<String> telemetryData =
                processor.processManifest(manifest, inspector, compressor);

        telemetryData.forEach(System.out::println);
    }
}

