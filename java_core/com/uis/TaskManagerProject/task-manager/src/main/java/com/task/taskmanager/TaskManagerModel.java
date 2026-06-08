package com.task.taskmanager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.task.logger.Logger;

import com.task.taskmanager.TaskManagerView;

public class TaskManagerModel {
    private static Logger logger = Logger.getInstance();

    public boolean createCategory(String name) {

        File categoryFile = new File(TaskManagerView.pathToData + "/" + name + ".todo"); // yes i can give my own
        // extension...

        // // TaskManager.logger.log(categoryFile,Logger.LOW_PRIORITY);

        // System.out.println(categoryFile.exists());
        // System.out.println(categoryFile.getAbsolutePath());
        // System.out.println(categoryFile.getParentFile());
        // System.out.println(categoryFile.listFiles());
        // // System.out.println(categoryFile.);

        // for(File f : categoryFile.listFiles()){
        // System.out.println(f.getName());
        // }

        if (categoryFile.exists()) {
            return false;
        }

        else {
            BufferedOutputStream bw = null;
            try {
                bw = new BufferedOutputStream(new FileOutputStream(categoryFile));
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
}
