package net.galluzzo.presentations.spock.controller;

import net.galluzzo.presentations.spock.dto.StatusDto;
import net.galluzzo.presentations.spock.service.StatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public StatusDto reportStatus() {
        return statusService.reportStatus();
    }
}
