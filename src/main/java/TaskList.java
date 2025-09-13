import java.util.ArrayList;

public class TaskList {
    private static final ArrayList<Task> TASK_LIST = new ArrayList<Task>(10);

    public static void add(Task task) {
        TASK_LIST.add(task);
    }

    public static Task remove(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        Task taskRemoved = TASK_LIST.get(i);
        TASK_LIST.remove(i);
        return taskRemoved;
    }

    public static int size() {
        return TASK_LIST.size();
    }

    public static Task get(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        return TASK_LIST.get(i);
    }

    public static void mark(int i) {
        if (i < 0 || i >= size()) {
            return;
        }
        TASK_LIST.get(i).markDone();
    }

    public static void unmark(int i) {
        if (i < 0 || i >= size()) {
            return;
        }
        TASK_LIST.get(i).markNotDone();
    }
}
