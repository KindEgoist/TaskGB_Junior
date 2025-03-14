package ru.gb.junior.Task3.SerializeAndDeserialize;

import ru.gb.junior.Task4.entity.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializePerson {
    public static void main(String[] args) {

        Person person = new Person("Макс", 42);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.ser"))) {
            oos.writeObject(person);
            System.out.println("Объект: " + person + " -> Сериализован.");
            System.out.println("Сериализация прошла успешно!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

