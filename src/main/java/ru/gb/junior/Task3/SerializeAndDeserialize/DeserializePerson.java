package ru.gb.junior.Task3.SerializeAndDeserialize;

import ru.gb.junior.Task4.entity.Person;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializePerson {

    public static void main(String[] args) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.ser"))) {
            Person person = (Person) ois.readObject();
            System.out.println("Объект: " + person + " -> Десериализован.");
            System.out.println("Десериализация прошла успешна!!!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

