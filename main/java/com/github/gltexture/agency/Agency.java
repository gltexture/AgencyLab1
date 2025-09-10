package com.github.gltexture.agency;

import com.github.gltexture.IUpdatable;
import com.github.gltexture.clients.Client;

import java.util.*;

public class Agency implements IUpdatable {
    private final int maxAgents;
    private final List<Agent> agents;

    public Agency(int maxAgents) {
        this.maxAgents = maxAgents;
        this.agents = new ArrayList<>();
        this.init();
    }

    private void init() {
        for (int i = 0; i < this.maxAgents; i++) {
            this.agents.add(new Agent());
        }
    }

    public void addNewClient(Client client) {
        if (this.agents.isEmpty()) {
            return;
        }
        List<Agent> agents1 = new ArrayList<>(this.agents);
        agents1.sort(Comparator.comparingInt(Agent::currentServiceLoad).thenComparingInt(Agent::getId));
        agents1.get(0).addClient(client);
    }

    public List<Agent> getAgents() {
        return this.agents;
    }

    public void logReport() {
        List<Agent> agents1 = new ArrayList<>(this.agents);
        agents1.sort(Comparator.comparingInt(e -> ((Agent) e).getReport().getServedClients()).reversed().thenComparingInt(e -> ((Agent) e).getReport().getMaxTime()));
        agents1.forEach(Agent::logReport);
    }

    @Override
    public void update(int time) {
        this.agents.forEach(e -> e.update(time));
    }
}
