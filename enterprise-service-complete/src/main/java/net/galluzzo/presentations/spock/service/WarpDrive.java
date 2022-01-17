package net.galluzzo.presentations.spock.service;

public interface WarpDrive {
    /**
     * Returns the ship's current warp factor.
     * @return A positive number (usually between 0 and 10)
     */
    double getWarpFactor();

    void setWarpFactor(double warpFactor);
    void setCoordinates(String coordinates);
}
