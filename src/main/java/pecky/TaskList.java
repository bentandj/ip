package pecky;

import java.util.ArrayList;

public class TaskList {
    private static final int DEFAULT_CAPACITY = 10;
    private final ArrayList<Task> taskArrayList;

    public TaskList() {
        taskArrayList = new ArrayList<Task>(DEFAULT_CAPACITY);
    }

    public void add(Task task) {
        taskArrayList.add(task);
    }

    public Task remove(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        Task taskRemoved = taskArrayList.get(i);
        taskArrayList.remove(i);
        return taskRemoved;
    }

    public int size() {
        return taskArrayList.size();
    }

    public Task get(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        return taskArrayList.get(i);
    }

    public void mark(int i) {
        if (i < 0 || i >= size()) {
            return;
        }
        taskArrayList.get(i).markDone();
    }

    public void unmark(int i) {
        if (i < 0 || i >= size()) {
            return;
        }
        taskArrayList.get(i).markNotDone();
    }

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
