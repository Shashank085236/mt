package flush.paths;

import flush.IFlushVisitor;

import java.nio.file.Path;

public class LevelPath implements IPath {

    private Path path;
    private String RT;

    public LevelPath(Path path, String RT){
        this.path = path;
        this.RT = RT;
    }

    @Override
    public Path getPath(){ return path; }


    @Override
    public int getDepth() {
        return 3;
    }

    @Override
    public TYPE getType() {
        return TYPE.LEVEL;
    }

    @Override
    public String getRT() {
        return RT;
    }

    @Override
    public void accept(IFlushVisitor visitor) {
        visitor.visit(this);
    }
}
