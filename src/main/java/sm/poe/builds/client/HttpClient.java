
package sm.poe.builds.client;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class HttpClient
{
	public String get(String url) throws URISyntaxException, IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder(new URI(url))
				.GET()
				.build();
		HttpResponse<String> response = java.net.http.HttpClient
				.newBuilder()
				.build()
				.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}
}
