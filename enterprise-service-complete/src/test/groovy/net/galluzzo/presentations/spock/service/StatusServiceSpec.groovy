package net.galluzzo.presentations.spock.service

import spock.lang.Specification

import static org.hamcrest.Matchers.closeTo
import static spock.util.matcher.HamcrestSupport.that

class StatusServiceSpec extends Specification {
    def "reportStatus should return a speed of #lightSpeedMultiples at warp #warpFactor and shield percentage of #shieldPercentage from shield fraction of #shieldFraction"() {
        given:
        WarpDrive warpDrive = Mock()
        ShieldService shieldService = Mock()
        StatusService service = new StatusService(warpDrive, shieldService)

        when:
        var status = service.reportStatus()

        then:
        status.warpDrive().warpFactor() == warpFactor
        assert that(status.warpDrive().lightSpeedMultiples(), closeTo(lightSpeedMultiples, 1e-5d))
        status.shieldPercentageRemaining() == shieldPercentage

        1 * warpDrive.warpFactor >> warpFactor
        1 * shieldService.shieldFractionRemaining() >> shieldFraction

        where:
        warpFactor | shieldFraction || lightSpeedMultiples | shieldPercentage
        1.0d       | 0.23           || 1.0d                | 23
        2.0d       | 0.789          || 8.0d                | 79
        3.0d       | 1.0            || 27.0d               | 100
        8.5d       | 0.232          || 614.125d            | 23
    }
}
