package com.nickdsantos.onedrive4j;

import com.google.gson.annotations.SerializedName;

/**
 * A drive items response.
 *
 * @author Luke Quinane
 */
public class DriveItemsResponse {
    /**
     * The list of drive items.
     */
    public DriveItem[] value;

    /**
     * The next link to query if the list is too large.
     */
    @SerializedName("@odata.nextLink")
    public String nextLink;
}
