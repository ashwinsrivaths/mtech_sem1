package com.uis.InnerClassesAndSingleton;

public class CommandLogger {

    public static final int LOW_PRIORITY = 1;
    public static final int MEDIUM_PRIORITY = 2;
    public static final int HIGH_PRIORITY = 3;
    public static final int CRITICAL_PRIORITY = 4;

    private CommandLogger() {

    }

    public static final CommandLogger instance = new CommandLogger();

    public static CommandLogger getInstance() {
        return instance;
    }

    public void log(String msg, int priority) {
        // Implementation for logging commands

        new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            String log = String.format("Priority: %d, Message: %s", priority, msg);
                            System.out.println(log);
                        } catch (Exception e) {
                            System.out.println("Error while logging command: " + e.getMessage());
                        }
                    }
                }).start();
    }

}
