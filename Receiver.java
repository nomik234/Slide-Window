import java.util.HashMap;
import java.util.Map;

public class Receiver {
    private int LA; // Last ACKed
    private int W; // Window size
    private Map<Integer, Packet> earlyArrivals;

    public Receiver(int windowSize) {
        this.LA = 0;
        this.W = windowSize;
        this.earlyArrivals = new HashMap<>();
    }

    public void receivePacket(Packet packet) {
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
                Packet nextPacket = earlyArrivals.remove(LA + 1);
                deliverPacket(nextPacket);
                LA++;
            }
        }

        sendACK(LA);
    }

    private void deliverPacket(Packet packet) {
        System.out.println("Delivered: " + packet.data);
    }

    private void sendACK(int ackNumber) {
        System.out.println("ACK: " + ackNumber);
    }
}
