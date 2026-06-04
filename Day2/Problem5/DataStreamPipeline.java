abstract class DataPayload {

    public abstract String getRawContent();
}

class JsonPayload extends DataPayload {

    private String rawContent;

    public JsonPayload(String rawContent) {
        this.rawContent = rawContent;
    }

    @Override
    public String getRawContent() {
        return rawContent;
    }
}

class XmlPayload extends DataPayload {

    private String rawContent;

    public XmlPayload(String rawContent) {
        this.rawContent = rawContent;
    }

    @Override
    public String getRawContent() {
        return rawContent;
    }
}

class PipelineProcessor<T extends DataPayload> {

    public void process(T payload) {

        System.out.println(
                "Processing payload: " +
                        payload.getRawContent()
        );
    }
}

public class DataStreamPipeline {

    public static void main(String[] args) {

        JsonPayload jsonPayload =
                new JsonPayload("{ \"name\" : \"ABC\" }");

        XmlPayload xmlPayload =
                new XmlPayload("<name>ABC</name>");

        PipelineProcessor<JsonPayload> jsonProcessor =
                new PipelineProcessor<>();

        PipelineProcessor<XmlPayload> xmlProcessor =
                new PipelineProcessor<>();

        jsonProcessor.process(jsonPayload);

        xmlProcessor.process(xmlPayload);
    }
}