package com.example.realestateapp;

/**
 * Класс, представляющий земельный участок. Наследуется от {@link RealEstate}.
 * Учитывает наличие коммуникаций и удаленность от города.
 *
 * @author Зайцев Герман Петрович
 * @version 1.0
 */
public class LandPlot extends RealEstate {
    /** Флаг наличия коммуникаций. */
    private boolean hasUtilities;
    /** Удаленность от города в километрах. */
    private double distanceFromCity;

    /**
     * Создает объект земельного участка.
     *
     * @param address          Адрес.
     * @param area             Площадь.
     * @param price            Базовая цена.
     * @param hasUtilities     Наличие коммуникаций.
     * @param distanceFromCity Удаленность от города (км).
     */
    public LandPlot(String address, double area, double price, boolean hasUtilities, double distanceFromCity) {
        super(address, area, price);
        this.hasUtilities = hasUtilities;
        this.distanceFromCity = distanceFromCity;
    }

    /**
     * Вычисляет стоимость квадратного метра с учетом особенностей участка.
     * Учитывает коммуникации (надбавка 30%) и удаленность (скидка за каждый км свыше 20).
     *
     * @return Стоимость за 1 кв.м.
     */
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

    /**
     * Возвращает расширенную информацию о земельном участке.
     *
     * @return Строка с базовой информацией и специфичными параметрами участка.
     */
    @Override
    public String getInfo() {
        return super.getInfo() + String.format(" | Тип: Участок, Коммуникации: %s, Удаленность: %.1f км",
                hasUtilities ? "Да" : "Нет", distanceFromCity);
    }
}