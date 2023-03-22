package org.example;

public class Homework {
    private static int id;
    private String name;
    private String description;

    public Homework(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
