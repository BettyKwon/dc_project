/**
 * Created by Betty on 19/06/2017.
 */

import java.util.ArrayList;

public class CSMA_CD {

    public static void main(String[] args) {
        int numNodes = 10;
        double lambda = 10;     // works well up to 800
        Medium medium = new Medium();

        for(int i = 0; i < numNodes; i++) {
            (new Node(i, lambda, medium)).run();
        }
    }
}

class Medium {
    long busyUntil;

    boolean idle() {
        if(busyUntil >= getTimeInMicro()) return false;
        else return true;
    }

    void setBusyUntil(long t) {
        busyUntil = t;
    }

    long getTimeInMicro() {
        return System.nanoTime() / 1000;
    }
}

class Node extends Thread {
    int id;
    ArrayList<Integer> packets;
    double lambda;     // int???
    int packetIdx;
    long lastPacketGenerated;
    long nextPacketArriving;
    Medium medium;

    public Node(int index, double l, Medium m) {
        id = index;
        lambda = l;
        packets = new ArrayList<>();
        packetIdx = 0;
        medium = m;
        lastPacketGenerated = getTimeInMicro();
        nextPacketArriving = lastPacketGenerated + getPoissonInMicro(lambda);
    }
    public void run() {
        System.out.println("Node " + id + " is running. " + lastPacketGenerated + "  " + getPoissonInMicro(lambda));
        System.out.println("Node " + id + "done.");

        while(true) {
            if(getTimeInMicro() >= nextPacketArriving) {
                packets.add(packetIdx);
                packetIdx++;
                lastPacketGenerated = nextPacketArriving;
                nextPacketArriving = lastPacketGenerated + getPoissonInMicro(lambda);
            }
            if(!packets.isEmpty()) {
                if(medium.idle()) {
                    transmit();
                    if(medium.idle()) {
                        // transmission success
                    }
                    else {
                        // collision occurred
                    }
                }

            }
            else {
                try {
                    sleep(0, (int) (nextPacketArriving - getTimeInMicro()) * 1000);
                }
                catch(InterruptedException ie) {}
            }
        }

    }

    void transmit() {
        // TODO : suspend for transmission time
    }
    long getTimeInMicro() {
        return System.nanoTime() / 1000;
    }

    public int getPoissonInMicro(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return (int) ((k - 1) * 1000000 / (lambda * lambda));
    }
}