package flush.paths;

import flush.IFlushVisitor;

import java.nio.file.Path;

public interface IPath {
    enum TYPE{ LEVEL, BIT_FILE }
    void accept(IFlushVisitor visitor);
    Path getPath();
    String getRT();
    int getDepth();
    TYPE getType();
}
