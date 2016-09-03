/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Nick DS (me@nickdsantos.com)
 * @param <T>
 *
 */
public class OneDriveJsonToMapResponseHandler implements ResponseHandler<Map<Object,Object>> {

	/* (non-Javadoc)
	 * @see org.apache.http.client.ResponseHandler#handleResponse(org.apache.http.HttpResponse)
	 */
	@Override
	public Map<Object,Object> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		StatusLine statusLine = response.getStatusLine();		
		System.out.println("code: " + statusLine.getStatusCode() +"; reason: " + statusLine.getReasonPhrase());
		HttpEntity respEntity = response.getEntity();
		if (respEntity != null) {				
			Gson gson = new GsonBuilder().create();
			Reader reader = new InputStreamReader(respEntity.getContent(), Charset.forName("UTF-8"));
			return gson.fromJson(reader, Map.class);
		}
		
		return null;
	}
}
