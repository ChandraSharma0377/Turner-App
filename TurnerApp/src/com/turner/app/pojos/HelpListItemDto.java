package com.turner.app.pojos;

public class HelpListItemDto {

    private String title;
    private int icon;


    public HelpListItemDto() {
    }

    public HelpListItemDto(int icon, String title) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "HelpListItemDto{" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                '}';
    }
}
