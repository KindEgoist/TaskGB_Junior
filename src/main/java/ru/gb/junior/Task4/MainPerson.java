package ru.gb.junior.Task4;

import ru.gb.junior.Task4.entity.Person;

import java.util.List;

public class MainPerson {
    public static void main(String[] args) {
        PersonService personService = new PersonService();

        System.out.println("Добавляем пользователей");
        personService.addPerson(new Person("Max", 42));
        personService.addPerson(new Person("Misha", 40));
        personService.addPerson(new Person("Yana", 40));

        System.out.println("Получаем по ID");
        System.out.println(personService.getPersonById(2));

        System.out.println("Получаем по Имени");
        List<Person> personListByName = personService.getPersonsByName("Yana");
        for (Person person : personListByName) System.out.println(person);

        System.out.println("Получаем по Возрасту");
        List<Person> personListByAge = personService.getPersonsByAge(40);
        for (Person person : personListByAge) System.out.println(person);

        System.out.println("Получаем Весь список");
        List<Person> personListAll = personService.getAllPersons();
        for (Person person : personListAll) System.out.println(person);

        System.out.println("Изменение по ID");
        personService.updatePerson(2, "Elena", 35);

        System.out.println("Удаление по ID");
        personService.deletePerson(1);

        PersonService.close();
    }
}
