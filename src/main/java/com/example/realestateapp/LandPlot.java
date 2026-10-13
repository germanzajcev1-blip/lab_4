package com.example.realestateapp;

public class LandPlot extends RealEstate {
    private boolean hasUtilities;
    private double distanceFromCity;

    public LandPlot(String address, double area, double price, boolean hasUtilities, double distanceFromCity) {
        super(address, area, price);
        this.hasUtilities = hasUtilities;
        this.distanceFromCity = distanceFromCity;
    }

    @Override
    public double calculatePricePerSqM() {
        double basePrice = super.calculatePricePerSqM();

        if (hasUtilities) {
            basePrice *= 1.30;
        }

        if (distanceFromCity > 20) {
            double excessKm = distanceFromCity - 20;
            double reduction = excessKm * 0.0005;
            basePrice *= (1 - reduction);
        }

        return basePrice;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + String.format(" | Тип: Участок, Коммуникации: %s, Удаленность: %.1f км",
                hasUtilities ? "Да" : "Нет", distanceFromCity);
    }
}