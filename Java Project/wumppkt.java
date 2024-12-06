public class wumppkt {
    // Packet class
    public static class Packet {
        public int sequenceNumber;
        public String data;

        public Packet(int sequenceNumber, String data) {
            this.sequenceNumber = sequenceNumber;
            this.data = data;
        }
    }
}
