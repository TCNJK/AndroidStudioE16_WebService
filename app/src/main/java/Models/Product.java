package Models;

public class Product {
    public int id;
    public String name;
    public int categoryId;
    public int unitsInStock;
    public double unitPrice;
    public Category category;

    public Product(int id, String name, int categoryId, int unitsInStock, double unitPrice, Category category) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.unitsInStock = unitsInStock;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public Product(String name, int categoryId, int unitsInStock, double unitPrice) {
        this.name = name;
        this.categoryId = categoryId;
        this.unitsInStock = unitsInStock;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
