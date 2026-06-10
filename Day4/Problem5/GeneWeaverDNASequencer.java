
import java.util.*;
import java.util.stream.Collectors;

abstract class DNASample {
    protected String sampleId;
    protected double purityPercentage;

    public DNASample(String sampleId, double purityPercentage) {
        this.sampleId = sampleId;
        this.purityPercentage = purityPercentage;
    }

    public String getSampleId() {
        return sampleId;
    }

    public double getPurityPercentage() {
        return purityPercentage;
    }
}

class HumanSample extends DNASample {
    private String bloodType;

    public HumanSample(String sampleId, double purityPercentage, String bloodType) {
        super(sampleId, purityPercentage);
        this.bloodType = bloodType;
    }

    public String getBloodType() {
        return bloodType;
    }
}

class AlienSample extends DNASample {
    private boolean isSiliconBased;

    public AlienSample(String sampleId, double purityPercentage, boolean isSiliconBased) {
        super(sampleId, purityPercentage);
        this.isSiliconBased = isSiliconBased;
    }

    public boolean isSiliconBased() {
        return isSiliconBased;
    }
}

@FunctionalInterface
interface ViabilityChecker {
    boolean isViable(DNASample sample);
}

@FunctionalInterface
interface GenomeMapper {
    String map(DNASample sample);
}

class Sequencer {

    public Map<String, List<String>> classifyGenomes(
            List<DNASample> samples,
            ViabilityChecker checker,
            GenomeMapper mapper) {

        return samples.stream()
                .filter(checker::isViable)
                .collect(Collectors.groupingBy(
                        sample -> sample.getClass().getSimpleName(),
                        Collectors.mapping(
                                mapper::map,
                                Collectors.toList())));
    }
}

public class GeneWeaverDNASequencer {

    public static void main(String[] args) {

        List<DNASample> samples = Arrays.asList(
                new HumanSample("001", 92.5, "O-"),
                new HumanSample("002", 75.0, "A+"),
                new AlienSample("003", 88.0, true),
                new AlienSample("004", 65.0, false),
                new HumanSample("005", 95.0, "B+"));

        ViabilityChecker checker = sample -> sample.getPurityPercentage() >= 80.0;

        GenomeMapper mapper = sample -> {
            if (sample instanceof HumanSample) {
                return "ID: " + sample.getSampleId()
                        + " (Type: "
                        + ((HumanSample) sample).getBloodType() + ")";
            }

            return "ID: " + sample.getSampleId()
                    + " (Silicon: "
                    + ((AlienSample) sample).isSiliconBased() + ")";
        };

        Sequencer sequencer = new Sequencer();

        Map<String, List<String>> result = sequencer.classifyGenomes(samples, checker, mapper);

        result.forEach((species, genomes) -> {
            System.out.println(species);
            genomes.forEach(System.out::println);
        });
    }
}
