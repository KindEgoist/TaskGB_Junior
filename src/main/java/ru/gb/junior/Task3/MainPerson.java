package ru.gb.junior.Task3;

import ru.gb.junior.Task3.entity.Person;
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
        personService.getPersonsByName("Yana").forEach(System.out::println);

        System.out.println("Получаем по Возрасту");
        personService.getPersonsByAge(40).forEach(System.out::println);

        System.out.println("Получаем Весь список");
        personService.getAllPersons().forEach(System.out::println);

        System.out.println("Изменение по ID");
        personService.updatePerson(2, "Elena", 35);

        System.out.println("Удаление по ID");
        personService.deletePerson(1);

        PersonService.close();
    }
}
