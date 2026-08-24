- mvn clean install
- java -jar target/task-manager-1.0-SNAPSHOT.jar 



# functionality
- crud tasks
- ADD Task
  - name
  - description
  - category
  - date of planned completion
  - 
- all tasks to be associated with a category
- remainder/ pending tasks
  - list tasks based on
  - completed/cancelled/in-progress tasks
  - longest time taking tasks
- persist data (currently in file storage)
- export tasks to excel
- search tasks


# implementation
- one file per category
- one line per task
- MVC design pattern used


# validations
- name
  - should start with letter
  - should only contain aplha numeric values
  - should only be single word