package ru.gb.junior.Task4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.gb.junior.Task4.entity.Person;

import java.util.List;

public class PersonService {
    private static final SessionFactory FACTORY = new Configuration()
            .configure("Hibernate/hibernate.cfg.xml")
            .addAnnotatedClass(Person.class)
            .buildSessionFactory();

    void addPerson(Person person){
        try (Session session = FACTORY.getCurrentSession()){
            session.beginTransaction();
            session.persist(person);
            session.getTransaction().commit();
            System.out.println(person + " добавлен в таблицу");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Person getPersonById(int id){
        isIdLessThanZero(id);
        try (Session session = FACTORY.getCurrentSession()) {
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            session.getTransaction().commit();
            return person;
        }
    }
    public List<Person> getPersonsByAge(int age) {
        if (age < 20 || age > 80) {
            throw new IllegalArgumentException("Возраст должен быть в диапазоне от 20 до 80 лет!");
        }
        try (Session session = FACTORY.getCurrentSession()) {
            session.beginTransaction();
            Query<Person> query = session.createQuery("FROM Person WHERE age = :age", Person.class);
            query.setParameter("age", age);
            List<Person> persons = query.getResultList();
            session.getTransaction().commit();
            return persons;
        }
    }

    public List<Person> getPersonsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не должно быть пустым!");
        }
        try (Session session = FACTORY.getCurrentSession()) {
            session.beginTransaction();
            Query<Person> query = session.createQuery("FROM Person WHERE name = :name", Person.class);
            query.setParameter("name", name);
            List<Person> persons = query.getResultList();
            session.getTransaction().commit();
            return persons;
        }
    }

    public List<Person> getAllPersons() {
        try (Session session = FACTORY.getCurrentSession()) {
            session.beginTransaction();
            Query<Person> query = session.createQuery("FROM Person", Person.class);
            List<Person> persons = query.getResultList();
            session.getTransaction().commit();
            return persons;
        }
    }

    public void updatePerson(int id, String name, int age){
        isIdLessThanZero(id);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя не должно быть пустым!");
        }
        if (age < 20 || age > 80) {
            throw new IllegalArgumentException("Возраст должен быть в диапазоне от 20 до 80 лет!");
        }
        try (Session session = FACTORY.getCurrentSession()){
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            if(person != null){
                person.setName(name);
                person.setAge(age);
                session.getTransaction().commit();
            }
            System.out.println("Person с ID: " + id + "обнавлен!");
        }
    }

    public void deletePerson(int id){
        isIdLessThanZero(id);
        try (Session session = FACTORY.getCurrentSession()){
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            if (person != null){
                session.remove(person);
                session.getTransaction().commit();
                System.out.println("Person с ID " + id + " удален!");
            }
        }
    }
    private void isIdLessThanZero(int id){
        if (id <= 0) throw new IllegalArgumentException("ID должен быть положительным числом!");
    }
    public static void close() {
        if (FACTORY != null) {
            FACTORY.close();
        }
    }
}
