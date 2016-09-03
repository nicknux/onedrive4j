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
    public IdentitySet createdBy;

    /**
     * The user who last modified the item.
     */
    public IdentitySet lastModifiedBy;

    /**
     * The creation date/time.
     */
    public String createdDateTime;

    /**
     * The last modification date/time.
     */
    public String lastModifiedDateTime;

    /**
     * An eTag for the item's content.
     */
    public String cTag;

    /**
     * An eTag for the item's content and metadata.
     */
    public String eTag;

    /**
     * The name of the item.
     */
    public String name;

    /**
     * The item's description
     */
    public String description;

    /**
     * The details of the parent item.
     */
    public ItemReference parentReference;

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
    public FileSystemInfoFacet fileSystemInfo;

    /**
     * The location facet.
     */
    public LocationFacet location;
    /**
     * The deleted facet.
     */
    public DeletedFacet deleted;

    /**
     * The file information.
     */
    public FileFacet file;

    /**
     * The folder information.
     */
    public FolderFacet folder;
}