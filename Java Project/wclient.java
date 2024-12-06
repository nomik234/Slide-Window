import java.util.HashMap;
import java.util.Map;

public class wclient {

    // Sender class
    public static class Sender {
        private int LA; // Last ACKed
        private int W; // Window size
        private Map<Integer, wumppkt.Packet> sentPackets;

        public Sender(int windowSize) {
            this.LA = 0;
            this.W = windowSize;
            this.sentPackets = new HashMap<>();
            for (int i = 1; i <= W; i++) {
                sendPacket(new wumppkt.Packet(i, "Data" + i));
            }
        }

        public void receiveACK(int M) {
            if (M <= LA || M > LA + W) {
                // Ignore the ACK
                return;
            } else {
                int K = LA + W + 1;
                LA = M;
                for (int i = K; i <= LA + W; i++) {
                    sendPacket(new wumppkt.Packet(i, "Data" + i));
                }
            }
        }

        private void sendPacket(wumppkt.Packet packet) {
            sentPackets.put(packet.sequenceNumber, packet);
            System.out.println("Sent: " + packet.data);
        }
    }

    // Receiver class
    public static class Receiver {
        private int LA; // Last ACKed
        private int W; // Window size
        private Map<Integer, wumppkt.Packet> earlyArrivals;

        public Receiver(int windowSize) {
            this.LA = 0;
            this.W = windowSize;
            this.earlyArrivals = new HashMap<>();
        }

        public void receivePacket(wumppkt.Packet packet) {
            int M = packet.sequenceNumber;

            if (M <= LA || M > LA + W) {
                // Ignore the packet
                return;
            } else if (M > LA + 1) {
                // Store the packet in EarlyArrivals
                earlyArrivals.put(M, packet);
            } else if (M == LA + 1) {
                // Deliver the packet and slide the window
                deliverPacket(packet);
                LA++;
                while (earlyArrivals.containsKey(LA + 1)) {
                    wumppkt.Packet nextPacket = earlyArrivals.remove(LA + 1);
                    deliverPacket(nextPacket);
                    LA++;
                }
            }

            sendACK(LA);
        }

        private void deliverPacket(wumppkt.Packet packet) {
            System.out.println("Delivered: " + packet.data);
        }

        private void sendACK(int ackNumber) {
            System.out.println("ACK: " + ackNumber);
        }
    }

    // Main method
    public static void main(String[] args) {
        Receiver receiver = new Receiver(5);
        Sender sender = new Sender(5);

        // Simulate packet arrival at receiver
        receiver.receivePacket(new wumppkt.Packet(1, "Data1"));
        receiver.receivePacket(new wumppkt.Packet(2, "Data2"));
        receiver.receivePacket(new wumppkt.Packet(3, "Data3"));

        // Simulate ACK arrival at sender
        sender.receiveACK(1);
        sender.receiveACK(2);
        sender.receiveACK(3);
    }
}
