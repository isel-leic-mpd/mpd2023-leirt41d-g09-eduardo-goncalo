package isel.mpd.requests;

import java.io.Reader;

public class CounterRequest implements Request {
	private final Request req;
	private int count;

	public CounterRequest(Request req) {
		this.req = req;
	}

	@Override
	public Reader getReader(String path) {
		++count;
		return req.getReader(path);
	}

	public int getCount() {
		return count/2;
	}
}
