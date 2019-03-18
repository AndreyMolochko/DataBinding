package com.example.databinding.enums;

public enum Categories {
    SOUPS("Soups", 0), MAIN("Main", 1), SALADS("Salads", 2), DESSERT("Dessert", 3), DRINKS("Drinks",
                                                                                           4);
    private String category;
    private int id;

    Categories(String category, int id) {
        this.category = category;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
