
import java.util.*;

abstract class EngineLog {
    protected String timestamp;
    protected double coreTemperature;
    protected boolean isAnomaly;

    public EngineLog(String timestamp, double coreTemperature, boolean isAnomaly) {
        this.timestamp = timestamp;
        this.coreTemperature = coreTemperature;
        this.isAnomaly = isAnomaly;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getCoreTemperature() {
        return coreTemperature;
    }

    public boolean isAnomaly() {
        return isAnomaly;
    }
}

class NominalLog extends EngineLog {
    public NominalLog(String timestamp, double coreTemperature, boolean isAnomaly) {
        super(timestamp, coreTemperature, isAnomaly);
    }
}

class CriticalLog extends EngineLog {
    private String errorCode;

    public CriticalLog(String timestamp, double coreTemperature,
                       boolean isAnomaly, String errorCode) {
        super(timestamp, coreTemperature, isAnomaly);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

@FunctionalInterface
interface LogAuditor {
    boolean audit(EngineLog log);
}

@FunctionalInterface
interface HeatExtractor {
    double extract(EngineLog log);
}

class TelemetryProcessor {

    public double getPeakCriticalTemp(
            List<EngineLog> logs,
            LogAuditor auditor,
            HeatExtractor extractor) {

        return logs.stream()
                .filter(auditor::audit)
                .mapToDouble(extractor::extract)
                .max()
                .orElse(0.0);
    }
}

public class AstroDyneThrusterTelemetryAnalyzer {

    public static void main(String[] args) {

        List<EngineLog> logs = Arrays.asList(
                new NominalLog("10:00:01", 450.5, false),
                new CriticalLog("10:00:05", 890.2, false, "OVERHEAT"),
                new NominalLog("10:00:08", 620.0, true),
                new CriticalLog("10:00:12", 975.8, true, "FUEL_LEAK"),
                new CriticalLog("10:00:15", 1100.4, false, "OVERHEAT")
        );

        LogAuditor auditor = log -> {
            if (log.isAnomaly()) {
                return true;
            }

            if (log instanceof CriticalLog) {
                return ((CriticalLog) log)
                        .getErrorCode()
                        .equals("OVERHEAT");
            }

            return false;
        };

        HeatExtractor extractor = EngineLog::getCoreTemperature;

        TelemetryProcessor processor = new TelemetryProcessor();

        double peakTemperature =
                processor.getPeakCriticalTemp(logs, auditor, extractor);

        System.out.println("Peak Critical Temperature: " + peakTemperature);
    }
}

