package com.mockable.dumav10;

public class History {

    private Integer id;
    private Boolean choice;
    private String text, person, dialog, background;

    public History() {
        id = 1;
        text = "";
        person = "";
        dialog = "";
        background = "";
        choice = false;
    }

    public History(Integer id, String text, String person, String dialog, String background, Boolean choice) {
        this.id = id;
        this.text = text;
        this.person = person;
        this.dialog = dialog;
        this.background = background;
        this.choice = choice;
    }

    public Boolean getChoice() {
        return choice;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getPerson() {
        return person;
    }

    public String getDialog() {
        return dialog;
    }

    public String getBackground() {
        return background;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
