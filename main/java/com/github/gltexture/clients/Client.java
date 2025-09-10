package com.github.gltexture.clients;

public record Client(int id, int serviceTime) {
    private static int GLOBAL_ID;

    public static Client createNew(int serviceTime) {
        return new Client(Client.GLOBAL_ID++, serviceTime);
    }
}
