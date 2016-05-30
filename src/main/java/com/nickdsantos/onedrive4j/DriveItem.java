package com.nickdsantos.onedrive4j;

import com.google.gson.annotations.SerializedName;

/**
 * A drive item.
 *
 * @author Luke Quinane
 */
public class DriveItem {
    /**
     * The ID.
     */
    public String id;

    /**
     * The URL to use to download the item.
     */
    @SerializedName("@content.downloadUrl")
    public String downloadUrl;

    /**
     * The user who created the item.
     */
    public DriveOwner createdBy;

    /**
     * The user who last modified the item.
     */
    public DriveOwner lastModifiedBy;

    /**
     * The creation date/time.
     */
    public String createdDateTime;

    /**
     * The last modification date/time.
     */
    public String lastModifiedDateTime;

    public String cTag;

    public String eTag;

    /**
     * The name of the item.
     */
    public String name;

    /**
     * The details of the parent item.
     */
    public DriveParentReference parentReference;

    /**
     * The size.
     */
    public long size;

    /**
     * The web URL.
     */
    public String webUrl;

    /**
     * The file system info.
     */
    public DriveFileSystemInfo fileSystemInfo;

    /**
     * The folder information.
     */
    public DriveFolder folder;
}