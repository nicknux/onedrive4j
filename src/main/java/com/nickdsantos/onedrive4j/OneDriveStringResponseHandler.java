package com.nickdsantos.onedrive4j;

import com.google.common.io.CharStreams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * A response handler which returns the response content as a string.
 *
 * @author Luke Quinane
 */
public class OneDriveStringResponseHandler implements ResponseHandler<String> {
	@Override
	public String handleResponse(HttpResponse response) throws IOException {
		HttpEntity respEntity = response.getEntity();
		if (respEntity != null) {
			Reader reader = new InputStreamReader(respEntity.getContent(), Charset.forName("UTF-8"));
			return CharStreams.toString(reader);
		}
		
		return null;
	}
}
