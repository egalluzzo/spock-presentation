package net.galluzzo.presentations.spock.samples;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Person {
    private final String name;
    private final List<SyringeContents> contents = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isMagnetic() {
        return contents.stream().anyMatch(SyringeContents::isMagnetic);
    }

    public int getMicrochipCount() {
        return contents.stream().mapToInt(c -> c.containsMicrochip() ? 1 : 0).sum();
    }

    public void inject(Syringe syringe) {
        contents.add(syringe.empty(VaccinationSite.LEFT_ARM));
    }

    public void fillOut(VaccinationCard vaccinationCard) {
        vaccinationCard.record(contents.stream().filter(SyringeContents::isVaccine).collect(toList()));
    }
}
