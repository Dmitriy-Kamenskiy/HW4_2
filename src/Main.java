import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        int minors = (int) persons.stream().filter(minor -> minor.getAge() < 18).count();

        List<String> recruits = persons.stream().filter(recruit -> recruit.getAge() > 17)
                .filter(recruit -> recruit.getAge() < 27)
                .map(Person::getFamily).collect(Collectors.toList());

        List<Person> higherEducations = persons.stream().filter(h -> h.getEducation().equals(Education.HIGHER))
                .filter(Main::workingAge)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

    }

    static boolean workingAge(Person person) {
        if (person.getAge() > 17) {
            return switch (person.getSex()) {
                case MAN -> person.getAge() < 65;
                case WOMAN -> person.getAge() < 60;
            };
        }
        return false;
    }
}