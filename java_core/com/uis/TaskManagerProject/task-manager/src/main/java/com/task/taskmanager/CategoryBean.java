package com.task.taskmanager;

import com.task.taskmanager.Util;

public final class CategoryBean {
    final String category;

    public String getName() {
        return category;
    }

    public CategoryBean(String category) {
        if (!Util.validateName(category)) {
            throw new IllegalArgumentException("name\n" + //
                                "  - should start with letter\n" + //
                                "  - should only contain aplha numeric values\n" + //
                                "  - should only be single word");
        }
        this.category = category;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
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
        CategoryBean other = (CategoryBean) obj;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Category [category=" + category + "]";
    }

    
}
