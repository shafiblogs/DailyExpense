package com.de.dto;

/**
 * Created by Shafi on 6/3/2015.
 */
public class CategoryDTO {
    private String categoryName;
    private String categoryType;
    private int expenseOrder;
    private int incomeOrder;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public int getExpenseOrder() {
        return expenseOrder;
    }

    public void setExpenseOrder(int expenseOrder) {
        this.expenseOrder = expenseOrder;
    }

    public int getIncomeOrder() {
        return incomeOrder;
    }

    public void setIncomeOrder(int incomeOrder) {
        this.incomeOrder = incomeOrder;
    }


}
