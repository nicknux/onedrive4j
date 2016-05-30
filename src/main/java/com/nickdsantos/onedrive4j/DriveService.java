package com.nickdsantos.onedrive4j;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * A service for accessing the OneDrive files.
 *
 * @author Luke Quinane
 */
public class DriveService {
    static Logger logger = Logger.getLogger(DriveService.class.getName());

    public static final String API_HOST = "api.onedrive.com/v1.0";
    public static final String DEFAULT_SCHEME = "https";
    public static final String DRIVES_URL_PATH = "/drives";

    /**
     * This class should only be instantiated from the OneDrive.getDriveService() method.
     */
    protected DriveService() {
    }

    /**
     * Gets a list of drives.
     *
     * @param accessToken the access token.
     * @return the list of drives.
     * @throws IOException if an error occurs.
     */
    public List<Drive> getDrives(String accessToken) throws IOException {
        URI uri;
        try {
            uri = new URIBuilder()
                    .setScheme(DEFAULT_SCHEME)
                    .setHost(API_HOST)
                    .setPath(DRIVES_URL_PATH)
                    .addParameter("access_token", accessToken)
                    .build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid drives path", e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(uri);
            String rawResponse = httpClient.execute(httpGet, new OneDriveStringResponseHandler());

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Drive[].class, new ResponseValueDeserializer<>(Drive[].class))
                    .create();

            return Arrays.asList(gson.fromJson(rawResponse, Drive[].class));
        } catch (Exception e) {
            throw new IOException("Error getting drives", e);
        }
    }

    /**
     * Gets the root items for a drive.
     *
     * @param accessToken the access token.
     * @param driveId     the drive Id.
     * @return the list of items.
     * @throws IOException if an error occurs.
     */
    public List<DriveItem> getRootItems(String accessToken, String driveId) throws IOException {
        URI uri;
        try {
            uri = new URIBuilder()
                    .setScheme(DEFAULT_SCHEME)
                    .setHost(API_HOST)
                    .setPath("/drives/" + driveId + "/root/children")
                    .addParameter("access_token", accessToken)
                    .build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid drives path", e);
        }

        return getDriveItemsImpl(uri);
    }

    /**
     * Gets the folder items.
     *
     * @param accessToken the access token.
     * @param folderId    the folder Id to look under.
     * @return the list of drive items.
     * @throws IOException if an error occurs.
     */
    public List<DriveItem> getChildItems(String accessToken, String folderId) throws IOException {
        URI uri;
        try {
            uri = new URIBuilder()
                    .setScheme(DEFAULT_SCHEME)
                    .setHost(API_HOST)
                    .setPath("/drive/items/" + folderId + "/children/")
                    .addParameter("access_token", accessToken)
                    .build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid drives path", e);
        }

        return getDriveItemsImpl(uri);
    }

    /**
     * Gets the drive items.
     *
     * @param uri the URI to fetch from.
     * @return the list of items.
     * @throws IOException if an error occurs.
     */
    private List<DriveItem> getDriveItemsImpl(URI uri) throws IOException {
        ImmutableList.Builder<DriveItem> driveItems = new ImmutableList.Builder<>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            while (uri != null) {
                HttpGet httpGet = new HttpGet(uri);
                String rawResponse = httpClient.execute(httpGet, new OneDriveStringResponseHandler());
                DriveItemsResponse driveItemsResponse = new Gson().fromJson(rawResponse, DriveItemsResponse.class);

                driveItems.addAll(Arrays.asList(driveItemsResponse.value));

                uri = driveItemsResponse.nextLink == null ? null : URI.create(driveItemsResponse.nextLink);
            }

            return driveItems.build();
        } catch (Exception e) {
            throw new IOException("Error getting drives", e);
        }
    }

    /**
     * Downloads the content for an item.
     *
     * @param accessToken the access token.
     * @param driveItem   the item.
     * @param outputStream the stream to write to.
     * @throws IOException if an error occurs.
     */
    public void downloadItemContent(String accessToken, DriveItem driveItem, OutputStream outputStream)
            throws IOException {

        URI uri;
        try {
            if (Strings.isNullOrEmpty(driveItem.downloadUrl)) {
                uri = new URIBuilder()
                        .setScheme(DEFAULT_SCHEME)
                        .setHost(API_HOST)
                        .setPath("/drive/items/" + driveItem.id + "/content")
                        .addParameter("access_token", accessToken)
                        .build();
            } else {
                uri = new URI(driveItem.downloadUrl);
            }
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Invalid drives path", e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(uri);
            try (InputStream inputStream = httpClient.execute(httpGet).getEntity().getContent())
            {
                IOUtils.copyLarge(inputStream, outputStream, new byte[0x10000]);
            }
        } catch (Exception e) {
            throw new IOException("Error getting drives", e);
        }
    }
}
