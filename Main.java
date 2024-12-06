public class Main {
    public static void main(String[] args) {
        Receiver receiver = new Receiver(5);
        Sender sender = new Sender(5);

        // Simulate packet arrival at receiver
        receiver.receivePacket(new Packet(1, "Data1"));
        receiver.receivePacket(new Packet(2, "Data2"));
        receiver.receivePacket(new Packet(3, "Data3"));

        // Simulate ACK arrival at sender
        sender.receiveACK(1);
        sender.receiveACK(2);
        sender.receiveACK(3);
    }
}
