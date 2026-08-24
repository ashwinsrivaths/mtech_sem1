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
    // private static Set categories = new HashSet<CategoryBean>();
    public static final String pathToData = "/techData/taskManagerData";

    public static void main(String[] args) {
        TaskManagerModel model = new TaskManagerModel();
        try {

            logger.log("Welcome to Task Manager!", Logger.LOW_PRIORITY);

            Scanner scanner = new Scanner(System.in);

            int scannerInput = 0;
            while (scannerInput != 7) {

                System.out.println("\nMenu:");
                System.out.println("1. create CategoryBean");
                System.out.println("2. load CategoryBean");
                System.out.println("3. remove CategoryBean");
                System.out.println("4. list CategoryBean");
                System.out.println("5. search CategoryBean");
                System.out.println("6. export CategoryBean");
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
                        logger.log("You chose to create a CategoryBean.", Logger.LOW_PRIORITY);
                        // Implementation for creating a CategoryBean
                        CategoryBean CategoryBean = createCategoryBean(scanner, model);
                        issuesExplorer(scanner, model, CategoryBean);
                        break;
                    case 2:
                        logger.log("You chose to load a CategoryBean.", Logger.LOW_PRIORITY);
                        // Implementation for loading a CategoryBean
                        break;
                    case 3:
                        logger.log("You chose to remove a CategoryBean.", Logger.LOW_PRIORITY);
                        // Implementation for removing a CategoryBean
                        break;
                    case 4:
                        logger.log("You chose to list categories.", Logger.LOW_PRIORITY);

                        CategoryBean CategoryBean1 = listAndChooseCategoryBean(scanner, model);
                        issuesExplorer(scanner, model, CategoryBean1);


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

    private static CategoryBean createCategoryBean(Scanner scanner, TaskManagerModel model) {
        System.out.println("enter CategoryBean name");
        String name = scanner.nextLine();
        CategoryBean CategoryBean = null;
        try {
            CategoryBean = new CategoryBean(name);
            // TaskManager.categories.add(CategoryBean);
            if (model.createCategoryBean(CategoryBean)) {
                logger.log("successfully created CategoryBean", Logger.MEDIUM_PRIORITY);
            } else {
                logger.log("unable to create CategoryBean", Logger.MEDIUM_PRIORITY);
            }
        } catch (Exception e) {
            logger.log(e.getMessage(), Logger.HIGH_PRIORITY);
        }

        return CategoryBean;
    }

    private static CategoryBean listAndChooseCategoryBean(Scanner scanner, TaskManagerModel model) {
        String s = "The available categories are: \n";
        Set<CategoryBean> categories = model.listCategories();
        s = s + categories + "\nwhich category will you like to choose";
        System.out.println(s);


        String choice = scanner.nextLine();
        
        CategoryBean cat = new CategoryBean(choice);
        if (categories.contains(cat)) {
            
        } else {
            throw new IllegalArgumentException("category not found");
        }
        return cat;
    }

    private static void issuesExplorer(Scanner scanner, TaskManagerModel model, CategoryBean CategoryBean) {
        int choice = 0;
        while (choice != 6) {
            System.out.println("\nCategoryBean=> " + CategoryBean.getName() + "\nMenu:");
            System.out.println("1. create issue");
            System.out.println("2. load issue");
            System.out.println("3. remove issue");
            System.out.println("4. list issues");
            System.out.println("5. search issues");
            System.out.println("6. Exit");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 6) {
                    throw new IllegalArgumentException("Invalid input. Please enter a number between 1 and 6.");
                }
            } catch (Exception e) {
                logger.log("Invalid input. Please enter a number between 1 and 6.", Logger.LOW_PRIORITY);
            }

            switch (choice) {
                case 1:
                    logger.log("You chose to create an issue.", Logger.LOW_PRIORITY);
                    // Implementation for creating an issue
                    createIssue(scanner, model, CategoryBean);
                    break;
                case 2:
                    logger.log("You chose to load an issue.", Logger.LOW_PRIORITY);
                    // Implementation for loading an issue
                    break;
                case 3:
                    logger.log("You chose to remove an issue.", Logger.LOW_PRIORITY);
                    // Implementation for removing an issue
                    break;
                case 4:
                    logger.log("You chose to list issues.", Logger.LOW_PRIORITY);
                    // Implementation for listing issues

                    break;
                case 5:
                    logger.log("You chose to search issues.", Logger.LOW_PRIORITY);
                    // Implementation for searching issues
                    break;
                case 6:
                    logger.log("Exiting Issue Explorer. Returning to main menu.", Logger.LOW_PRIORITY);
                    break;
            }
        }
    }

    private static void createIssue(Scanner scanner, TaskManagerModel model, CategoryBean CategoryBean) {
        System.out.println("enter issue name");

        try {
            String name = scanner.nextLine();
            Task issue = new Task(name, name, CategoryBean, null);
            // TaskManager.categories.add(CategoryBean);
            if (model.createIssue(CategoryBean, issue)) {
                logger.log("successfully created issue", Logger.MEDIUM_PRIORITY);
            } else {
                logger.log("unable to create issue", Logger.MEDIUM_PRIORITY);
            }
        } catch (Exception e) {
            logger.log(e.getMessage(), Logger.HIGH_PRIORITY);
        }

    }

}
