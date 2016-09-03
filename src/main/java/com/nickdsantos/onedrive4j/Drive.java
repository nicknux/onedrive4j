package com.nickdsantos.onedrive4j;

/**
 * Details of a drive.
 *
 * @author Luke Quinane
 */
public class Drive {
    /**
     * The ID for the drive.
     */
    public String id;

    /**
     * The drive type.
     */
    public String driveType;

    /**
     * The owner of the drive.
     */
    public IdentitySet owner;

    /**
     * The quota details.
     */
    public Quota quota;
}

