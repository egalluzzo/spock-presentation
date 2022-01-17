package net.galluzzo.presentations.spock.service;

import net.galluzzo.presentations.spock.dto.StatusDto;

public class StatusService {
    private WarpDrive warpDrive;
    private ShieldService shieldService;

    public StatusService(WarpDrive warpDrive, ShieldService shieldService) {
        this.warpDrive = warpDrive;
        this.shieldService = shieldService;
    }

    public StatusDto reportStatus() {
        return null;
    }
}
