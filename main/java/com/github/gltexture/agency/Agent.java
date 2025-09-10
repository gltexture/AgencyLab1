package com.github.gltexture.agency;

import com.github.gltexture.IUpdatable;
import com.github.gltexture.clients.Client;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

public class Agent implements IUpdatable {
    private static int GLOBAL_ID;
    private final int id;
    private final Deque<Client> clientDeque;
    private Client currentClient;
    private int currentServingTime;
    private final Report report;

    public Agent() {
        this.clientDeque = new ArrayDeque<>();
        this.id = Agent.GLOBAL_ID++;
        this.report = new Report();
    }

    public void addClient(Client client) {
        this.getClientDeque().addLast(client);
        System.out.println("Agent: " + this.id + " got new client: " + client.id());
    }

    @Override
    public void update(int time) {
        if (this.currentClient == null) {
            Client toServe = this.clientDeque.peekFirst();
            if (toServe != null) {
                this.currentClient = toServe;
                this.currentServingTime = toServe.serviceTime();
            }
        } else {
            if (--this.currentServingTime <= 0) {
                this.report.setServedClients(this.report.getServedClients() + 1);
                this.report.setMaxTime(this.report.getMaxTime() + this.currentClient.serviceTime());
                System.out.println("(Time): " + time + " | Agent: " + this.id + ". Done: " + this.currentClient.id());
                this.clientDeque.pollFirst();
                this.currentClient = null;
            }
        }
    }

    public int currentServiceLoad() {
        int load = this.currentServingTime;
        for (Client c : clientDeque) {
            load += c.serviceTime();
        }
        return load;
    }

    public Report getReport() {
        return this.report;
    }

    public Client getCurrentClient() {
        return this.currentClient;
    }

    public int getCurrentServingTime() {
        return this.currentServingTime;
    }

    public int getId() {
        return this.id;
    }

    public Deque<Client> getClientDeque() {
        return this.clientDeque;
    }

    public void logReport() {
        System.out.println("Agent: " + this.id + ". Report: " + this.getReport().getServedClients() + " - Clients, " + this.getReport().getMaxTime() + " - Time.");
    }

    public static class Report {
        private int servedClients;
        private int maxTime;

        public Report() {
            this.servedClients = 0;
            this.maxTime = 0;
        }

        public int getServedClients() {
            return this.servedClients;
        }

        public void setServedClients(int servedClients) {
            this.servedClients = servedClients;
        }

        public int getMaxTime() {
            return this.maxTime;
        }

        public void setMaxTime(int maxTime) {
            this.maxTime = maxTime;
        }
    }
}
