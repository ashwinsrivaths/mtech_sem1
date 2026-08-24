package com.task.taskmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private String name;
    private String description;
    private CategoryBean category;
    private Date plannedCompletionDate;

    public Task(String name, String description, CategoryBean category, Date plannedCompletionDate) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.plannedCompletionDate = plannedCompletionDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public Date getPlannedCompletionDate() {
        return plannedCompletionDate;
    }

    public void setPlannedCompletionDate(Date plannedCompletionDate) {
        this.plannedCompletionDate = plannedCompletionDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((plannedCompletionDate == null) ? 0 : plannedCompletionDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        if (plannedCompletionDate == null) {
            if (other.plannedCompletionDate != null)
                return false;
        } else if (!plannedCompletionDate.equals(other.plannedCompletionDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name + "  ##  " + description + "  ##  " + category
                + "  ##  " + plannedCompletionDate;
    }

    public Task fromString(String taskString)   throws ParseException {
        String[] stringArr = taskString.split(" ");

        if (stringArr.length != 4) {
            throw new IllegalStateException("malformed data stored in fs please delete and restart the system");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date plannedCompletionDate = sdf.parse(stringArr[3]);
        Date CurrentDate = new Date();
        long diffInMillies = plannedCompletionDate.getTime() - CurrentDate.getTime();
        // Date plannedCompletionDate = new Date(stringArr[3]);
        return new Task(stringArr[0], stringArr[1], new CategoryBean(stringArr[3]), plannedCompletionDate);
    }
}
