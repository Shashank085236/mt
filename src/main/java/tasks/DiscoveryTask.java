package tasks;

import flush.paths.IPath;
import utils.BitFilePathCollector;

public class DiscoveryTask<T extends IPath> implements Task {

    private T path;

    public DiscoveryTask(T path) {
        this.path = path;
    }

    @Override
    public void run() {
        new BitFilePathCollector(this.path).collectFilesAndFlush();
    }

}
