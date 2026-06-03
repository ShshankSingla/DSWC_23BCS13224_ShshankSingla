public class Main {

    public static void main(String[] args) {

        DNASequencer sequencer = new DNASequencer(100000);

        char[] sensorData = {'A','C','T','G','A','A','C','T','G','G'};

        sequencer.ingestSequence(sensorData);

        System.out.println("Original DNA Sequence:");
        sequencer.displaySequence();

        sequencer.mutateDNA("ACT", "TTT");

        System.out.println("\nAfter Mutation:");
        sequencer.displaySequence();

        System.out.println("\nSequence Length: " + sequencer.getLength());
    }
}