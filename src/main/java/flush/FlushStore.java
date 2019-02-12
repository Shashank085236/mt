package flush;

import java.io.IOException;

public abstract class FlushStore {
	 public abstract void store(String data) throws IOException;
	 public abstract void close();
}
