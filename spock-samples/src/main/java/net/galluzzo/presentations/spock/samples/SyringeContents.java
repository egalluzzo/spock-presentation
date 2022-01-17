package net.galluzzo.presentations.spock.samples;

public enum SyringeContents {
    COVID_VACCINE(true, false, false),
    IRON_FILINGS(false, true, false),
    RFID_TAG(false, false, true);

    private final boolean vaccine;
    private final boolean magnetic;
    private final boolean containsMicrochip;

    SyringeContents(boolean vaccine, boolean magnetic, boolean containsMicrochip) {
        this.vaccine = vaccine;
        this.magnetic = magnetic;
        this.containsMicrochip = containsMicrochip;
    }

    public boolean isVaccine() {
        return vaccine;
    }

    public boolean isMagnetic() {
        return magnetic;
    }

    public boolean containsMicrochip() {
        return containsMicrochip;
    }
}
