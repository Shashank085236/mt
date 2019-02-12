package flush;

import flush.paths.BitFilePath;
import flush.paths.LevelPath;

public interface IFlushVisitor {
     public void visit(LevelPath path);
     public void visit(BitFilePath path);
}
