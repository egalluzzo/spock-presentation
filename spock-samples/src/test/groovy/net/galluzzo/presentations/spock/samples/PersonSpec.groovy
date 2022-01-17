package net.galluzzo.presentations.spock.samples

import spock.lang.Specification

import static net.galluzzo.presentations.spock.samples.SyringeContents.*
import static net.galluzzo.presentations.spock.samples.VaccinationSite.LEFT_ARM

class PersonSpec extends Specification {
    void "a Person's name should be what is given to it when it is created"() {
        given:
        var person = new Person("Eric")

        when:
        var name = person.name

        then:
        name == "Eric"
    }

    void "a Person's name should be what is given to it, but using expect"() {
        given:
        var person = new Person("Eric")

        expect:
        person.name == "Eric"
    }

    void "determine results of COVID vaccination"() {
        given:
        var person = new Person("Eric")
        Syringe syringe = Mock()

        when:
        person.inject(syringe)

        then:
        1 * syringe.empty(LEFT_ARM) >> COVID_VACCINE
        !person.magnetic
        person.microchipCount == 0
    }

    void "determine results of injecting stuff"() {
        given:
        var person = new Person("Eric")
        Syringe syringe = Mock()

        when:
        person.inject(syringe)

        then:
        1 * syringe.empty(LEFT_ARM) >> contents
        person.magnetic == magnetic
        person.microchipCount == microchipCount

        where:
        contents      || magnetic | microchipCount
        COVID_VACCINE || false    | 0
        IRON_FILINGS  || true     | 0
        RFID_TAG      || false    | 1
    }

    void "filling out vaccination card should only include vaccines"() {
        given:
        var person = new Person("Eric")
        Syringe vaccineSyringe = Mock()
        Syringe ironFilingsSyringe = Mock()
        VaccinationCard vaccinationCard = Mock()

        when:
        person.inject(vaccineSyringe)
        person.inject(ironFilingsSyringe)
        person.inject(vaccineSyringe)
        person.fillOut(vaccinationCard)

        then:
        2 * vaccineSyringe.empty(_) >> COVID_VACCINE
        1 * ironFilingsSyringe.empty(_) >> IRON_FILINGS
        1 * vaccinationCard.record({ it.size() == 2 && it.stream().allMatch(c -> c == COVID_VACCINE) })
    }
}
