package com.de.dto;

/**
 * Created by Shafi on 5/28/2015.
 */
public class ReportDTO {
    private int amount;
    private String date;
    private String category;
    private String description;

    public ReportDTO() {
    }

    public ReportDTO(String category, String date, int amount, String description) {
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
