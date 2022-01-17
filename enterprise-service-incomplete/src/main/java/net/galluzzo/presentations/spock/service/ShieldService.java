package net.galluzzo.presentations.spock.service;

public interface ShieldService {
    /**
     * Determines what fraction of the shields are still holding.
     *
     * @return  A number between 0 and 1
     */
    double shieldFractionRemaining();
}
