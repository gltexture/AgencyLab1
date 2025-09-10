package com.github.gltexture;

public class Main {
    public static int N = 4; // AGENTS
    public static int M = 12; // CLIENTS
    public static float a = 1.0f;
    public static float b = 20.0f;

    public static void main(String[] args) {
        Environment environment = new Environment(Main.a, Main.b);
        environment.simulate();
    }
}