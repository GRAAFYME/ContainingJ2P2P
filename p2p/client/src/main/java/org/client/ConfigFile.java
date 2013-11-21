package org.client;


public class ConfigFile {

    private int PlayerJumpSpeed, PlayerFallSpeed, PlayersetGravity, flyCamspeed;

    public ConfigFile() {
        PlayerJumpSpeed = 1000;
        PlayerFallSpeed = 0;
        PlayersetGravity = 0;
        flyCamspeed = 80;

    }

    public int PlayerJumpSpeed() {
        return PlayerJumpSpeed;
    }

    public int PlayerFallSpeed() {
        return PlayerFallSpeed;
    }

    public int PlayersetGravity() {
        return PlayersetGravity;
    }

    public int flyCamspeed() {
        return flyCamspeed;
    }
}
