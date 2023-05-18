package isel.mpd.requests;


import java.io.Reader;

public class CounterRequest implements Request {

	public CounterRequest(Request req) {
		// TO IMPLEMENT
	}

	@Override
	public Reader getReader(String path) {
		// TO IMPLEMENT
		return null;
	}

	public int getCount() {
		// TO IMPLEMENT
		return 0;

	}
}

