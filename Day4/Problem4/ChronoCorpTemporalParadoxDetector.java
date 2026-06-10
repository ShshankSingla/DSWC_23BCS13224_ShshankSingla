
import java.util.*;
import java.util.stream.Collectors;

abstract class TemporalEntity {
    protected String entityName;
    protected int originYear;

    public TemporalEntity(String entityName, int originYear) {
        this.entityName = entityName;
        this.originYear = originYear;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getOriginYear() {
        return originYear;
    }
}

class HumanEntity extends TemporalEntity {
    public HumanEntity(String entityName, int originYear) {
        super(entityName, originYear);
    }
}

class ArtifactEntity extends TemporalEntity {
    private boolean isRadioactive;

    public ArtifactEntity(String entityName, int originYear, boolean isRadioactive) {
        super(entityName, originYear);
        this.isRadioactive = isRadioactive;
    }

    public boolean isRadioactive() {
        return isRadioactive;
    }
}

class HistoricalEvent {
    private List<TemporalEntity> entities;
    private int eventYear;

    public HistoricalEvent(int eventYear, List<TemporalEntity> entities) {
        this.eventYear = eventYear;
        this.entities = entities;
    }

    public int getEventYear() {
        return eventYear;
    }

    public List<TemporalEntity> getEntities() {
        return entities;
    }
}

@FunctionalInterface
interface ParadoxChecker {
    boolean check(TemporalEntity entity, int eventYear);
}

@FunctionalInterface
interface ThreatMapper {
    String map(TemporalEntity entity);
}

class EventEntity {
    private TemporalEntity entity;
    private int eventYear;

    public EventEntity(TemporalEntity entity, int eventYear) {
        this.entity = entity;
        this.eventYear = eventYear;
    }

    public TemporalEntity getEntity() {
        return entity;
    }

    public int getEventYear() {
        return eventYear;
    }
}

class ParadoxAnalyzer {

    public List<String> detectParadoxes(
            List<HistoricalEvent> timeline,
            ParadoxChecker checker,
            ThreatMapper mapper) {

        return timeline.stream()
                .flatMap(event ->
                        event.getEntities().stream()
                                .map(entity -> new EventEntity(entity, event.getEventYear())))
                .filter(item -> checker.check(item.getEntity(), item.getEventYear()))
                .map(item -> mapper.map(item.getEntity()))
                .collect(Collectors.toList());
    }
}

public class ChronoCorpTemporalParadoxDetector {

    public static void main(String[] args) {

        HistoricalEvent event1 = new HistoricalEvent(
                1950,
                Arrays.asList(
                        new HumanEntity("John", 1940),
                        new HumanEntity("Neo", 2080)
                )
        );

        HistoricalEvent event2 = new HistoricalEvent(
                2000,
                Arrays.asList(
                        new ArtifactEntity("Quantum Device", 2150, true),
                        new HumanEntity("Sarah", 1995)
                )
        );

        List<HistoricalEvent> timeline = Arrays.asList(event1, event2);

        ParadoxChecker checker =
                (entity, eventYear) -> entity.getOriginYear() > eventYear;

        ThreatMapper mapper =
                entity -> entity.getEntityName() + " detected out of time!";

        ParadoxAnalyzer analyzer = new ParadoxAnalyzer();

        List<String> paradoxes =
                analyzer.detectParadoxes(timeline, checker, mapper);

        paradoxes.forEach(System.out::println);
    }
}

