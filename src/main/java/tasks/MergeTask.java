package tasks;

public class MergeTask implements Task {
    @Override
    public void run() {
        System.out.println("Invoked merge task...");
    }
}
