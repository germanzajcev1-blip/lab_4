package com.example.realestateapp;

/**
 * Базовый абстрактный класс, представляющий объект недвижимости.
 * Содержит общие поля и методы для расчета стоимости.
 *
 * @author Зайцев Герман Петрович
 * @version 1.0
 */
public class RealEstate {
    /** Адрес объекта недвижимости. */
    protected String address;
    /** Площадь объекта недвижимости в квадратных метрах. */
    protected double area;
    /** Базовая цена объекта недвижимости. */
    protected double price;

    /**
     * Создает новый объект недвижимости.
     *
     * @param address Адрес объекта.
     * @param area    Площадь в кв.м.
     * @param price   Базовая цена.
     */
    public RealEstate(String address, double area, double price) {
        this.address = address;
        this.area = area;
        this.price = price;
    }

    /**
     * Возвращает адрес объекта.
     * @return Адрес.
     */
    public String getAddress() { return address; }

    /**
     * Устанавливает адрес объекта.
     * @param address Новый адрес.
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * Возвращает площадь объекта.
     * @return Площадь в кв.м.
     */
    public double getArea() { return area; }

    /**
     * Устанавливает площадь объекта.
     * @param area Новая площадь.
     */
    public void setArea(double area) { this.area = area; }

    /**
     * Возвращает базовую цену объекта.
     * @return Базовая цена.
     */
    public double getPrice() { return price; }

    /**
     * Устанавливает базовую цену объекта.
     * @param price Новая базовая цена.
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Вычисляет базовую стоимость за один квадратный метр.
     *
     * @return Стоимость за 1 кв.м. Если площадь равна 0, возвращает 0.
     */
    public double calculatePricePerSqM() {
        if (area == 0) return 0;
        return price / area;
    }

    /**
     * Возвращает строковое представление информации об объекте недвижимости.
     *
     * @return Строка с адресом, площадью, ценой и ценой за кв.м.
     */
    public String getInfo() {
        return String.format("Адрес: %s, Площадь: %.2f, Цена: %.2f, Цена за кв.м: %.2f",
                address, area, price, calculatePricePerSqM());
    }
}