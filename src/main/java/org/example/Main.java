package org.example;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBaseConnection db = new DataBaseConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
        LessonDao lessonDao = new LessonDao(db);

        // Додавання нового домашнього завдання та уроку
        Homework homework = new Homework(1, "Домашнє завдання 1", "Виконати завдання №1 з підручника");
        Lesson lesson = new Lesson(1, "Урок 1", homework);
        lessonDao.addLesson(lesson);

        // Отримання списку всіх уроків
        List<Lesson> lessons = lessonDao.getAllLessons();
        System.out.println("Список уроків:");
        for (Lesson l : lessons) {
            System.out.println(l.toString());
        }

       // Отримання уроку за ID та видалення його
        int lessonId = 2;
        Lesson lessonById = lessonDao.getLessonById(lessonId);
        System.out.println("Урок з ID=" + lessonId + ": " + lessonById.toString());
        lessonDao.deleteLessonById(lessonId);
   }
}

