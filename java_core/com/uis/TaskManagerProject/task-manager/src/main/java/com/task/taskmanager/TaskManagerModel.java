package com.task.taskmanager;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import com.task.logger.Logger;
import com.task.taskmanager.CategoryBean;
import com.task.taskmanager.Task;
import com.task.taskmanager.TaskManagerView;

public class TaskManagerModel {
    private static Logger logger = Logger.getInstance();

    public boolean createCategoryBean(CategoryBean CategoryBean) {

        File CategoryBeanFile = new File(TaskManagerView.pathToData + "/" + CategoryBean.getName() + ".todo"); // yes i can give
                                                                                                       // my own
        // extension...

        // // TaskManager.logger.log(CategoryBeanFile,Logger.LOW_PRIORITY);

        // System.out.println(CategoryBeanFile.exists());
        // System.out.println(CategoryBeanFile.getAbsolutePath());
        // System.out.println(CategoryBeanFile.getParentFile());
        // System.out.println(CategoryBeanFile.listFiles());
        // // System.out.println(CategoryBeanFile.);

        // for(File f : CategoryBeanFile.listFiles()){
        // System.out.println(f.getName());
        // }

        if (CategoryBeanFile.exists()) {
            return false;
        }

        else {
            BufferedOutputStream bw = null;
            try {
                bw = new BufferedOutputStream(new FileOutputStream(CategoryBeanFile));
            } catch (IOException e) {
                TaskManagerModel.logger.log(e.getMessage(), 5);
                return false;
            } finally {
                try {
                    bw.close();
                } catch (Exception e) {
                    logger.log("failed to close output stream => " + e.getMessage(), 5);
                }
            }
            return true;

        }

    }

    public boolean createIssue(CategoryBean CategoryBean, Task issue) {
        // Implementation for creating an issue

        File CategoryBeanFile = new File(TaskManagerView.pathToData + "/" + CategoryBean.getName() + ".todo");
        if (!CategoryBeanFile.exists()) {
            logger.log("business check failure-> CategoryBean does not exist", 5);
            return false;
        }

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(CategoryBeanFile, true));

            bw.write(issue.toString());
            bw.newLine();
        } catch (Exception e) {
            logger.log("Error while writing issue to file: " + e.getMessage(), 5);
            return false;
        } finally {
            try {
                bw.close();

            } catch (Exception e) {
                logger.log("failed to close buffered writer => " + e.getMessage(), 5);
            }
        }

        return true;
    }

    public Set<CategoryBean> listCategories() {
        File dataDir = new File(TaskManagerView.pathToData);

        if (!dataDir.isDirectory()) {
            throw new IllegalStateException("wrong path???");
        }
        Set<CategoryBean> categorySet = new HashSet<CategoryBean>();
        for (File catFile : dataDir.listFiles()) {
            CategoryBean categoryBean = new CategoryBean(catFile.getName().split("\\.")[0]);
            categorySet.add(categoryBean);
        }

        return categorySet;
    }
}
