package isel.mpd.requests;

import java.io.Reader;
import java.util.function.Consumer;

public interface Request {
	Reader getReader(String path);


}
