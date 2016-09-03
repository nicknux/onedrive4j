package com.nickdsantos.onedrive4j;

/**
 * Drive quota details.
 *
 * @author Luke Quinane
 */
public class Quota {
    public long deleted;
    public long remaining;
    public String state;

    /**
     * The total space.
     */
    public long total;

    /**
     * The used space.
     */
    public long used;
}
