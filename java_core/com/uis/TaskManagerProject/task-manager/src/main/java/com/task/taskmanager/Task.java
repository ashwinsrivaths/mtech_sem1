package com.task.taskmanager;

import java.util.Date;

public class Task {

    private String name;
    private String description;
    private Category category;
    private Date plannedCompletionDate;

    public Task(String name, String description, Category category, Date plannedCompletionDate) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
        return "Task [name=" + name + ", description=" + description + ", category=" + category
                + ", plannedCompletionDate=" + plannedCompletionDate + "]";
    }

}
