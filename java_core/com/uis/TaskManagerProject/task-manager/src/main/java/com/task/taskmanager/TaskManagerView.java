package com.task.taskmanager;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.task.logger.Logger;

import com.task.taskmanager.TaskManagerModel;

public class TaskManagerView {
    private static Logger logger = Logger.getInstance();
    // private static Set categories = new HashSet<Category>();
    public static final String pathToData = "/techData/taskManagerData";

    public static void main(String[] args) {
        TaskManagerModel model = new TaskManagerModel();
        try {

            logger.log("Welcome to Task Manager!", Logger.LOW_PRIORITY);

            Scanner scanner = new Scanner(System.in);

            int scannerInput = 0;
            while (scannerInput != 7) {

                System.out.println("\nMenu:");
                System.out.println("1. create category");
                System.out.println("2. load category");
                System.out.println("3. remove category");
                System.out.println("4. list category");
                System.out.println("5. search category");
                System.out.println("6. export category");
                System.out.println("7. Exit");

                try {
                    scannerInput = Integer.parseInt(scanner.nextLine());
                    if (scannerInput < 1 || scannerInput > 7) {
                        throw new IllegalArgumentException("Invalid input. Please enter a number between 1 and 7.");
                    }
                } catch (Exception e) {
                    logger.log("Invalid input. Please enter a number between 1 and 7.", Logger.LOW_PRIORITY);
                }

                switch (scannerInput) {
                    case 1:
                        logger.log("You chose to create a category.", Logger.LOW_PRIORITY);
                        // Implementation for creating a category
                        createCategory(scanner, model);
                        break;
                    case 2:
                        logger.log("You chose to load a category.", Logger.LOW_PRIORITY);
                        // Implementation for loading a category
                        break;
                    case 3:
                        logger.log("You chose to remove a category.", Logger.LOW_PRIORITY);
                        // Implementation for removing a category
                        break;
                    case 4:
                        logger.log("You chose to list categories.", Logger.LOW_PRIORITY);
                        // Implementation for listing categories
                        break;
                    case 5:
                        logger.log("You chose to search categories.", Logger.LOW_PRIORITY);
                        // Implementation for searching categories
                        break;
                    case 6:
                        logger.log("You chose to export categories.", Logger.LOW_PRIORITY);
                        // Implementation for exporting categories
                        break;
                    case 7:
                        logger.log("Exiting Task Manager. Goodbye!", Logger.LOW_PRIORITY);
                        break;
                }
                if (scannerInput != 7) {
                    scannerInput = 0;
                }
            }
        } catch (Exception e) {
            logger.log("I am not going to log the stack trace as this is a user facing method", Logger.LOW_PRIORITY);
            logger.log("Error in TaskManager main method: " + e.getMessage(), Logger.CRITICAL_PRIORITY);
        }

    }

    private static boolean createCategory(Scanner scanner, TaskManagerModel model) {
        System.out.println("enter category name");
        String name = scanner.nextLine();

        try {
            Category category = new Category(name);
            // TaskManager.categories.add(category);
            if (model.createCategory(name)) {
                logger.log("successfully created category", Logger.MEDIUM_PRIORITY);
            } else {
                logger.log("unable to create category", Logger.MEDIUM_PRIORITY);
                return false;
            }
        } catch (Exception e) {
            logger.log(e.getMessage(), Logger.HIGH_PRIORITY);
            return false;
        }

        return true;
    }

}
