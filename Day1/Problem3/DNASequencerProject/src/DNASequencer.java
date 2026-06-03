class DNASequencer {

    private StringBuilder dnaSequence;

    public DNASequencer(int capacity) {
        dnaSequence = new StringBuilder(capacity);
    }

    public void ingestSequence(char[] sensorData) {
        for(char ch : sensorData)
            dnaSequence.append(ch);
    }

    public void mutateDNA(String target, String replacement) {
        int index = dnaSequence.indexOf(target);

        if(index != -1)
            dnaSequence.replace(index, index + target.length(), replacement);
    }

    public void displaySequence() {
        System.out.println(dnaSequence);
    }

    public int getLength() {
        return dnaSequence.length();
    }
}