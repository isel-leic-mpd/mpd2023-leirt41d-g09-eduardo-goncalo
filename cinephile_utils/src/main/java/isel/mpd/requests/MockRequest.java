package isel.mpd.requests;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class MockRequest implements Request{
	private final static String CACHE_NAME= "queries_cache/";
	private final static String CACHE_PATH;

	static {
		URL url = MockRequest.class.getClassLoader().getResource(CACHE_NAME);
		try {
			File file = new File(url.toURI());
			CACHE_PATH = file.getAbsolutePath() + "/";
		}
		catch(URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static String convert(String path) {
		var start = path.indexOf('3')+1;
		var end = path.lastIndexOf('&');
		if (end == -1) end = path.lastIndexOf('?');
		return  path.substring(start, end)
				   .replace('&', '-')
				   .replace('/', '.')
				   .replace( '?','-')
				   .replace( ',','-')+ ".txt";
	}


	@Override
	public Reader getReader(String path) {
		path = CACHE_NAME + convert(path);
		try {
			return new InputStreamReader(ClassLoader.getSystemResource(path).openStream());
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static void saveOn(String fileName, Reader reader) {
		fileName =  convert(fileName);
		try (PrintWriter writer = new PrintWriter(CACHE_PATH +fileName)) {
			reader.transferTo(writer);
		}
		catch (IOException e){
			throw new UncheckedIOException(e);
		}
	}
}
