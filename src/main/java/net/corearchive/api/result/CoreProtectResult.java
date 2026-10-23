package net.corearchive.api.result;

/**
 * Common fields shared by typed CoreArchive lookup results.
 */
public interface CoreProtectResult {
    int getActionId();

    String getActionString();

    String getPlayer();

    long getTimestamp();

    int getX();

    int getY();

    int getZ();

    String worldName();
}
