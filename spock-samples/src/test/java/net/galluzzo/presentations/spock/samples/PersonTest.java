package net.galluzzo.presentations.spock.samples;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static net.galluzzo.presentations.spock.samples.SyringeContents.*;
import static net.galluzzo.presentations.spock.samples.VaccinationSite.LEFT_ARM;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PersonTest {
    @ParameterizedTest
    @MethodSource("injectionTestArguments")
    public void determineResultsOfInjectingStuff(
            SyringeContents contents, boolean magnetic, int microchipCount) {
        var person = new Person("Eric");
        var syringe = mock(Syringe.class);
        when(syringe.empty(LEFT_ARM)).thenReturn(contents);

        person.inject(syringe);

        verify(syringe).empty(LEFT_ARM);
        assertThat(person.isMagnetic(), is(magnetic));
        assertThat(person.getMicrochipCount(), is(microchipCount));
    }

    private static List<Arguments> injectionTestArguments() {
        return List.of(
                Arguments.of(COVID_VACCINE, false, 0),
                Arguments.of(IRON_FILINGS, true, 0),
                Arguments.of(RFID_TAG, false, 1)
        );
    }

    @Test
    public void fillingOutVaccinationCardShouldOnlyIncludeVaccines() {
        var person = new Person("Eric");
        Syringe vaccineSyringe = mock(Syringe.class);
        Syringe ironFilingsSyringe = mock(Syringe.class);
        VaccinationCard vaccinationCard = mock(VaccinationCard.class);
        when(vaccineSyringe.empty(any())).thenReturn(COVID_VACCINE);
        when(ironFilingsSyringe.empty(any())).thenReturn(COVID_VACCINE);

        person.inject(vaccineSyringe);
        person.inject(ironFilingsSyringe);
        person.inject(vaccineSyringe);
        person.fillOut(vaccinationCard);

        verify(vaccineSyringe, times(2)).empty(any());
        verify(ironFilingsSyringe, times(1)).empty(any());

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<SyringeContents>> syringeContentsCaptor = ArgumentCaptor.forClass(List.class);
        verify(vaccinationCard, times(1)).record(syringeContentsCaptor.capture());
        var vaccines = syringeContentsCaptor.getValue();
        assertThat(vaccines.size(), is(2));
        assertThat(vaccines.stream().allMatch(c -> c == COVID_VACCINE), is(true));
    }
}
