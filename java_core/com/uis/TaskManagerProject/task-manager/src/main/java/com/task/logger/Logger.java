package com.task.logger;

import java.time.LocalDateTime;
import java.util.Date;


public class Logger {

    public static final int LOW_PRIORITY = 1;
    public static final int MEDIUM_PRIORITY = 2;
    public static final int HIGH_PRIORITY = 3;
    public static final int CRITICAL_PRIORITY = 4;

    private Logger() {

    }

    public static final Logger instance = new Logger();

    public static Logger getInstance() {
        return instance;
    }

    public void log(String msg, int priority) {
        // Implementation for logging commands
        new Thread(
                // this is an aninomous inner class object of type Runnable
                // this object is being passed to the Thread constructor and the thread will execute the run method of this object when it starts (as usual)
                new Runnable() {
                    public void run() {
                        try {
                            // LocalDateTime now = LocalDateTime.now();
                            Date dt = new Date();
                            String log = "Log at: " + dt.toString() + ", Priority: " + priority + ", Message: " + msg;
                            System.out.println(log);
                        } catch (Exception e) {
                            System.out.println("Error while logging command: " + e.getMessage());
                        }
                    }
                }).start();
    }

}
