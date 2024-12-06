import java.util.HashMap;
import java.util.Map;

public class Sender {
    private int LA; // Last ACKed
    private int W; // Window size
    private Map<Integer, Packet> sentPackets;

    public Sender(int windowSize) {
        this.LA = 0;
        this.W = windowSize;
        this.sentPackets = new HashMap<>();
        for (int i = 1; i <= W; i++) {
            sendPacket(new Packet(i, "Data" + i));
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
                sendPacket(new Packet(i, "Data" + i));
            }
        }
    }

    private void sendPacket(Packet packet) {
        sentPackets.put(packet.sequenceNumber, packet);
        System.out.println("Sent: " + packet.data);
    }
}
