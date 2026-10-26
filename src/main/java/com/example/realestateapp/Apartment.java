package com.example.realestateapp;

import java.time.Year;

/**
 * Класс, представляющий квартиру. Наследуется от {@link RealEstate}.
 * Учитывает наличие ремонта, год постройки и этаж при расчете стоимости.
 *
 * @author Зайцев Герман Петрович
 * @version 1.0
 */
public class Apartment extends RealEstate {
    /** Флаг наличия ремонта. */
    private boolean hasRenovation;
    /** Год постройки дома. */
    private int yearBuilt;
    /** Этаж, на котором находится квартира. */
    private int floor;

    /**
     * Создает объект квартиры.
     *
     * @param address       Адрес.
     * @param area          Площадь.
     * @param price         Базовая цена.
     * @param hasRenovation Наличие ремонта.
     * @param yearBuilt     Год постройки.
     * @param floor         Этаж.
     */
    public Apartment(String address, double area, double price, boolean hasRenovation, int yearBuilt, int floor) {
        super(address, area, price);
        this.hasRenovation = hasRenovation;
        this.yearBuilt = yearBuilt;
        this.floor = floor;
    }

    /**
     * Вычисляет стоимость квадратного метра с учетом особенностей квартиры.
     * Учитывает ремонт (надбавка 20%), возраст дома (скидка) и первый этаж (скидка 2%).
     *
     * @return Стоимость за 1 кв.м.
     */
    @Override
    public double calculatePricePerSqM() {
        double basePrice = super.calculatePricePerSqM();

        if (hasRenovation) {
            basePrice *= 1.20;
        }

        int currentYear = Year.now().getValue();
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

    /**
     * Возвращает расширенную информацию о квартире.
     *
     * @return Строка с базовой информацией и специфичными параметрами квартиры.
     */
    @Override
    public String getInfo() {
        return super.getInfo() + String.format(" | Тип: Квартира, Ремонт: %s, Год: %d, Этаж: %d",
                hasRenovation ? "Да" : "Нет", yearBuilt, floor);
    }
}