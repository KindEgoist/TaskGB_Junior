package ru.gb.junior.Task3;

import jakarta.persistence.*;
import ru.gb.junior.Task3.entity.Person;

import java.util.List;

public class PersonService {
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("PersonPU");

    public void addPerson(Person person) {
        EntityManager entityManager = FACTORY.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            System.out.println(person + " добавлен в таблицу");
        } finally {
            entityManager.close();
        }
    }

    public Person getPersonById(int id) {
        isIdLessThanZero(id);
        EntityManager entityManager = FACTORY.createEntityManager();
        try {
            return entityManager.find(Person.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Person> getPersonsByAge(int age) {
        if (age < 20 || age > 80) {
            throw new IllegalArgumentException("Возраст должен быть в диапазоне от 20 до 80 лет!");
        }
        EntityManager entityManager = FACTORY.createEntityManager();
        try {
            return entityManager.createQuery("FROM Person p WHERE p.age = :age", Person.class)
                    .setParameter("age", age)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Person> getPersonsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не должно быть пустым!");
        }
        EntityManager entityManager = FACTORY.createEntityManager();
        try {
            return entityManager.createQuery("FROM Person p WHERE p.name = :name", Person.class)
                    .setParameter("name", name)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Person> getAllPersons() {
        EntityManager entityManager = FACTORY.createEntityManager();
        try {
            return entityManager.createQuery("FROM Person p", Person.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void updatePerson(int id, String name, int age) {
        isIdLessThanZero(id);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя не должно быть пустым!");
        }
        if (age < 20 || age > 80) {
            throw new IllegalArgumentException("Возраст должен быть в диапазоне от 20 до 80 лет!");
        }

        EntityManager entityManager = FACTORY.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Person person = entityManager.find(Person.class, id);
            if (person != null) {
                person.setName(name);
                person.setAge(age);
                entityManager.merge(person);
                entityManager.getTransaction().commit();
                System.out.println("Person с ID: " + id + " обновлен!");
            }
        } finally {
            entityManager.close();
        }
    }

    public void deletePerson(int id) {
        isIdLessThanZero(id);
        EntityManager entityManager = FACTORY.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Person person = entityManager.find(Person.class, id);
            if (person != null) {
                entityManager.remove(person);
                entityManager.getTransaction().commit();
                System.out.println("Person с ID " + id + " удален!");
            }
        } finally {
            entityManager.close();
        }
    }

    private void isIdLessThanZero(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID должен быть положительным числом!");
    }

    public static void close() {
        if (FACTORY != null) {
            FACTORY.close();
        }
    }
}
