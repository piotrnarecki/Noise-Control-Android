package com.example.noisecontrol;

public final class ProcessingSettings {


    private static ProcessingSettings INSTANCE;

    private int time = 400; //czas w ms

    private ProcessingSettings() {
    }

    public static ProcessingSettings getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProcessingSettings();
        }

        return INSTANCE;
    }


    public static ProcessingSettings getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(ProcessingSettings INSTANCE) {
        ProcessingSettings.INSTANCE = INSTANCE;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
