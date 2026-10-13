package com.example.realestateapp;

public class RealEstate {
    protected String address;
    protected double area;
    protected double price;

    public RealEstate(String address, double area, double price) {
        this.address = address;
        this.area = area;
        this.price = price;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double calculatePricePerSqM() {
        if (area == 0) return 0;
        return price / area;
    }

    public String getInfo() {
        return String.format("Адрес: %s, Площадь: %.2f, Цена: %.2f, Цена за кв.м: %.2f",
                address, area, price, calculatePricePerSqM());
    }
}