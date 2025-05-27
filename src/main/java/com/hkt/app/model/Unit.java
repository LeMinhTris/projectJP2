// Unit.java
package com.hkt.app.model;

public class Unit {
    private String id;
    private String name;

    public Unit(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name; // để hiển thị tên trong ComboBox
    }
}
