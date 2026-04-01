
package com.paubox.data;

public class DynamicTemplate {
    private int id;
    private String name;
    private String content;

    public DynamicTemplate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DynamicTemplate(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public DynamicTemplate(int id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

}