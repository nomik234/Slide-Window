public class Packet {
    public int sequenceNumber;
    public String data;

    public Packet(int sequenceNumber, String data) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
    }
}
