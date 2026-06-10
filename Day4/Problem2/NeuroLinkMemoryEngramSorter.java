
import java.util.*;
import java.util.stream.Collectors;

abstract class MemoryEngram {
    protected String engramId;
    protected double clarityScore;
    protected boolean isCorrupted;

    public MemoryEngram(String engramId, double clarityScore, boolean isCorrupted) {
        this.engramId = engramId;
        this.clarityScore = clarityScore;
        this.isCorrupted = isCorrupted;
    }

    public String getEngramId() {
        return engramId;
    }

    public double getClarityScore() {
        return clarityScore;
    }

    public boolean isCorrupted() {
        return isCorrupted;
    }
}

class StandardEngram extends MemoryEngram {
    public StandardEngram(String engramId, double clarityScore, boolean isCorrupted) {
        super(engramId, clarityScore, isCorrupted);
    }
}

class ClassifiedEngram extends MemoryEngram {
    private int securityClearanceLevel;

    public ClassifiedEngram(String engramId, double clarityScore,
                            boolean isCorrupted, int securityClearanceLevel) {
        super(engramId, clarityScore, isCorrupted);
        this.securityClearanceLevel = securityClearanceLevel;
    }

    public int getSecurityClearanceLevel() {
        return securityClearanceLevel;
    }
}

@FunctionalInterface
interface EngramValidator {
    boolean isValid(MemoryEngram engram);
}

@FunctionalInterface
interface EngramTranslator {
    String translate(MemoryEngram engram);
}

class CortexProcessor {

    public List<String> processMemories(
            List<MemoryEngram> engrams,
            EngramValidator validator,
            EngramTranslator translator) {

        return engrams.stream()
                .filter(validator::isValid)
                .filter(engram -> engram.getClarityScore() >= 50.0)
                .map(translator::translate)
                .collect(Collectors.toList());
    }
}

public class NeuroLinkMemoryEngramSorter {

    public static void main(String[] args) {

        List<MemoryEngram> engrams = Arrays.asList(
                new StandardEngram("M101", 85.5, false),
                new ClassifiedEngram("M102", 92.0, false, 2),
                new ClassifiedEngram("M103", 88.0, false, 5),
                new StandardEngram("M104", 45.0, false),
                new StandardEngram("M105", 75.0, true)
        );

        EngramValidator validator = engram -> {
            if (engram.isCorrupted()) {
                return false;
            }

            if (engram instanceof ClassifiedEngram) {
                return ((ClassifiedEngram) engram)
                        .getSecurityClearanceLevel() <= 3;
            }

            return true;
        };

        EngramTranslator translator = engram ->
                "ENGRAM-" + engram.getEngramId()
                        + " | CLARITY: "
                        + engram.getClarityScore() + "%";

        CortexProcessor processor = new CortexProcessor();

        List<String> safeMemories =
                processor.processMemories(engrams, validator, translator);

        safeMemories.forEach(System.out::println);
    }
}

