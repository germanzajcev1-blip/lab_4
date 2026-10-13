package com.example.realestateapp;

public class Apartment extends RealEstate {
    private boolean hasRenovation;
    private int yearBuilt;
    private int floor;

    public Apartment(String address, double area, double price, boolean hasRenovation, int yearBuilt, int floor) {
        super(address, area, price);
        this.hasRenovation = hasRenovation;
        this.yearBuilt = yearBuilt;
        this.floor = floor;
    }

    @Override
    public double calculatePricePerSqM() {
        double basePrice = super.calculatePricePerSqM();

        if (hasRenovation) {
            basePrice *= 1.20;
        }

        int currentYear = java.time.Year.now().getValue();
        int age = currentYear - yearBuilt;
        if (age > 20) {
            double reduction = age * 0.006;
            basePrice *= (1 - reduction);
        }

        if (floor == 1) {
            basePrice *= 0.98;
        }

        return basePrice;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + String.format(" | Тип: Квартира, Ремонт: %s, Год: %d, Этаж: %d",
                hasRenovation ? "Да" : "Нет", yearBuilt, floor);
    }
}