package net.galluzzo.presentations.spock.controller

import groovy.json.JsonSlurper
import net.galluzzo.presentations.spock.dto.StatusDto
import net.galluzzo.presentations.spock.dto.WarpDriveDto
import net.galluzzo.presentations.spock.service.StatusService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest(StatusController)
class StatusControllerSpec extends Specification {
    @SpringBean
    private StatusService service = Mock()

    @Autowired
    private MockMvc mockMvc

    void "status endpoint returns 200 and is serialized correctly"() {
        when:
        var response = mockMvc.perform(get("/status"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().response
        var json = new JsonSlurper().parse(response.contentAsString.toCharArray())

        then:
        1 * service.reportStatus() >> new StatusDto(new WarpDriveDto(2.0d, 8.0d), 20)
        json != null
        verifyAll(json) {
            warpDrive?.warpFactor == 2.0d
            warpDrive?.lightSpeedMultiples == 8.0d
            shieldPercentageRemaining == 20
        }
    }
}
