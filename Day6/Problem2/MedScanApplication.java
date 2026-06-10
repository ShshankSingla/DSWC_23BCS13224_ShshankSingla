import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Component
@Lazy
class ImageRenderingEngine {

    public ImageRenderingEngine() {
        System.out.println("Heavy ImageRenderingEngine Initialized");
    }

    public void renderImage() {
        System.out.println("Rendering MRI Image");
    }
}

@Component
@Scope("prototype")
class PatientContext {

    private String patientId;

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }
}

@Component
class ScanOrchestrator {

    private final ImageRenderingEngine imageRenderingEngine;
    private final ObjectProvider<PatientContext> patientContextProvider;

    public ScanOrchestrator(
            ImageRenderingEngine imageRenderingEngine,
            ObjectProvider<PatientContext> patientContextProvider) {

        this.imageRenderingEngine = imageRenderingEngine;
        this.patientContextProvider = patientContextProvider;
    }

    public void processScan(String patientId) {

        PatientContext context =
                patientContextProvider.getObject();

        context.setPatientId(patientId);

        System.out.println("Processing scan for patient: "
                + context.getPatientId());

        imageRenderingEngine.renderImage();

        System.out.println("PatientContext HashCode: "
                + context.hashCode());
    }
}

@SpringBootApplication
public class MedScanApplication {

    public static void main(String[] args) {

        var context =
                SpringApplication.run(
                        MedScanApplication.class,
                        args
                );

        ScanOrchestrator orchestrator =
                context.getBean(ScanOrchestrator.class);

        orchestrator.processScan("P1001");
        orchestrator.processScan("P1002");
    }
}