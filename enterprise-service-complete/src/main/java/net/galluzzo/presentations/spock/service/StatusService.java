package net.galluzzo.presentations.spock.service;

import net.galluzzo.presentations.spock.dto.StatusDto;
import net.galluzzo.presentations.spock.dto.WarpDriveDto;

public class StatusService {
    private WarpDrive warpDrive;
    private ShieldService shieldService;

    public StatusService(WarpDrive warpDrive, ShieldService shieldService) {
        this.warpDrive = warpDrive;
        this.shieldService = shieldService;
    }

    public StatusDto reportStatus() {
        var warpFactor = warpDrive.getWarpFactor();
        return new StatusDto(
                new WarpDriveDto(warpFactor, Math.pow(warpFactor, 3.0)),
                (int) Math.round(shieldService.shieldFractionRemaining() * 100.0));
    }
}
