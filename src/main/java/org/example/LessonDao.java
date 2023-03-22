package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
    private final DataBaseConnection db;

    public LessonDao(DataBaseConnection db) {
        this.db = db;
    }

    public void addLesson(Lesson lesson) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();

            Homework homework = lesson.getHomework();
            if (homework != null) {
                PreparedStatement homeworkStmt = conn.prepareStatement("INSERT INTO Homework (id, name, description) VALUES (?, ?, ?)");
                homeworkStmt.setInt(1, Homework.getId());
                homeworkStmt.setString(2, homework.getName());
                homeworkStmt.setString(3, homework.getDescription());
                homeworkStmt.executeUpdate();
            }

            stmt = conn.prepareStatement("INSERT INTO Lesson (id, name, updatedAt, homework_id) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, lesson.getId());
            stmt.setString(2, lesson.getName());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(4, Homework.getId());
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            db.close(conn);
        }
    }

    public List<Lesson> getAllLessons() throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Lesson LEFT JOIN Homework ON Lesson.homework_id = Homework.id");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int homeworkId = rs.getInt("homework_id");
                String homeworkName = rs.getString("name");
                String homeworkDescription = rs.getString("description");
                int lessonId = rs.getInt("id");
                String lessonName = rs.getString("name");
                Homework homework = null;
                if (homeworkId != 0) {
                    PreparedStatement homeworkStmt = conn.prepareStatement("SELECT * FROM Homework WHERE id = ?");
                    homeworkStmt.setInt(1, homeworkId);
                    ResultSet homeworkRs = homeworkStmt.executeQuery();
                    if (homeworkRs.next()) {
                        homeworkName = homeworkRs.getString("name");
                        homeworkDescription = homeworkRs.getString("description");
                        homework = new Homework(homeworkId, homeworkName, homeworkDescription);
                    }
                    homeworkRs.close();
                    homeworkStmt.close();
                }
                Lesson lesson = new Lesson(lessonId, lessonName, homework);
                lessons.add(lesson);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            db.close(conn);
        }
        return lessons;
    }

    public Homework getHomeworkById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Homework homework = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement("SELECT * FROM Homework WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int homeworkId = resultSet.getInt("id");
                String homeworkName = resultSet.getString("name");
                String homeworkDescription = resultSet.getString("description");

                homework = new Homework(homeworkId, homeworkName, homeworkDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    db.close(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return homework;
    }

    public Lesson getLessonById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Lesson lesson = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement("SELECT * FROM Lesson WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int lessonId = resultSet.getInt("id");
                String lessonName = resultSet.getString("name");
                int homeworkId = resultSet.getInt("homework_id");

                Homework homework = getHomeworkById(homeworkId);

                lesson = new Lesson(lessonId, lessonName, homework);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    db.close(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lesson;
    }
    public void deleteLessonById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement("DELETE FROM Lesson WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            db.close(conn);
        }
    }


}