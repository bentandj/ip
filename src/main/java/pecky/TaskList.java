package pecky;

import java.util.ArrayList;

/**
 * Represents a list of Tasks.
 */

public class TaskList {
    private static final int DEFAULT_CAPACITY = 10;
    private final ArrayList<Task> taskArrayList;

    /**
     * Constructs a new TaskList object with the default capacity.
     */

    public TaskList() {
        taskArrayList = new ArrayList<Task>(DEFAULT_CAPACITY);
    }

    /**
     * Adds the given task to the ArrayList.
     *
     * @param task The task to be added.
     */

    public void add(Task task) {
        taskArrayList.add(task);
    }

    /**
     * Removes and returns the given task.
     *
     * @param i The index of the task to be removed.
     * @return The removed task.
     */

    public Task remove(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        Task taskRemoved = taskArrayList.get(i);
        taskArrayList.remove(i);
        return taskRemoved;
    }

    /**
     * Returns the current number of tasks.
     *
     * @return The current number of tasks.
     */

    public int size() {
        return taskArrayList.size();
    }

    /**
     * Returns the task at the index.
     *
     * @param i The index of the task to be returned.
     * @return The task at the index.
     */

    public Task get(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        return taskArrayList.get(i);
    }

    /**
     * Marks the specified task completed.
     *
     * @param i The index of the task to be marked completed.
     */

    public void mark(int i) {
        if (i < 0 || i >= size()) {
            return;
        }
        taskArrayList.get(i).markDone();
    }

    /**
     * Marks the specified task not completed.
     *
     * @param i The index of the task to be marked not completed.
     */

    public void unmark(int i) {
        if (i < 0 || i >= size()) {
            return;
        }
        taskArrayList.get(i).markNotDone();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns a user-friendly string representation of the TaskList object.
     * The format is
     * "1. Task 1".
     * "2. Task 2".
     * etc.
     *
     * @return String representation of the TaskList object.
     */

    @Override
    public String toString() {
        if (size() == 0) {
            return "";
        }
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < size() - 1; i++) {
            sB.append(i + 1);
            sB.append(". ");
            sB.append(taskArrayList.get(i));
            sB.append("\n");
        }
        sB.append(size());
        sB.append(". ");
        sB.append(taskArrayList.get(size() - 1));
        return sB.toString();
    }

    /**
     * This method returns a string representation of the TaskList object that
     * is easy to read and write to a text file.
     * The format is one task per line.
     *
     * @return String representation of the TaskList object for easy I/O.
     */

    public String toTaskListString() {
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < taskArrayList.size(); i++) {
            Task task = taskArrayList.get(i);
            sB.append(task.toTaskListString());
            sB.append("\n");
        }
        return sB.toString();
    }
}
