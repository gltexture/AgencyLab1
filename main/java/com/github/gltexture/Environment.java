package com.github.gltexture;

import com.github.gltexture.agency.Agency;
import com.github.gltexture.clients.Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Environment {
    private final Random random = new Random();
    private final Agency agency;
    private final float a;
    private final float b;
    private int t;

    public Environment(float a, float b) {
        this.agency = new Agency(Main.N);
        this.a = a;
        this.b = b;
    }

    private float nextInterval() {
        return this.a + (this.b - this.a) * this.random.nextFloat();
    }

    public List<Float> generateArrivalTimes(int n) {
        List<Float> times = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            times.add(a + (b - a) * random.nextFloat());
        }
        Collections.sort(times);
        return times;
    }

    public void simulate() {
        List<Float> arrivals = this.generateArrivalTimes(Main.M);
        int nextClientIndex = 0;
        int totalArrived = 0;

        while (true) {
            if (nextClientIndex < arrivals.size() && arrivals.get(nextClientIndex) <= t) {
                Client client = Client.createNew(this.random.nextInt(10) + 1);
                this.agency.addNewClient(client);
                nextClientIndex++;
                totalArrived++;
            }

            this.agency.update(t);

            boolean allArrived = totalArrived >= Main.M;
            boolean allDone = this.agency.getAgents().stream().allMatch(agent -> agent.getCurrentClient() == null && agent.getClientDeque().isEmpty());

            if (allArrived && allDone) {
                break;
            }

            this.t += 1;
        }

        this.agency.logReport();
    }
}
