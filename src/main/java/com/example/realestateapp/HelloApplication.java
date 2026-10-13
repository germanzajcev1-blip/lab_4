package com.example.realestateapp;

import com.example.realestateapp.Apartment;
import com.example.realestateapp.LandPlot;
import com.example.realestateapp.RealEstate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class HelloApplication extends Application {

    private TextArea outputArea;

    @Override
    public void start(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Файл");
        MenuItem newCalcItem = new MenuItem("Новый расчет");
        MenuItem saveItem = new MenuItem("Сохранить");
        MenuItem exitItem = new MenuItem("Выход");

        newCalcItem.setOnAction(e -> outputArea.clear());
        saveItem.setOnAction(e -> saveToFile());
        exitItem.setOnAction(e -> Platform.exit());

        fileMenu.getItems().addAll(newCalcItem, saveItem, new SeparatorMenuItem(), exitItem);

        Menu helpMenu = new Menu("Справка");
        MenuItem aboutItem = new MenuItem("О программе");
        aboutItem.setOnAction(e -> showAboutDialog());
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Квартира", "Земельный участок");
        typeComboBox.setValue("Квартира");

        TextField addressField = new TextField();
        addressField.setPromptText("Адрес");
        TextField areaField = new TextField();
        areaField.setPromptText("Площадь (кв.м)");
        TextField priceField = new TextField();
        priceField.setPromptText("Базовая цена");

        CheckBox renovationCheck = new CheckBox("Наличие ремонта");
        TextField yearField = new TextField();
        yearField.setPromptText("Год постройки");
        TextField floorField = new TextField();
        floorField.setPromptText("Этаж");

        CheckBox utilitiesCheck = new CheckBox("Наличие коммуникаций");
        TextField distanceField = new TextField();
        distanceField.setPromptText("Удаленность от города (км)");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(new Label("Тип:"), 0, 0);
        grid.add(typeComboBox, 1, 0);
        grid.add(new Label("Адрес:"), 0, 1);
        grid.add(addressField, 1, 1);
        grid.add(new Label("Площадь:"), 0, 2);
        grid.add(areaField, 1, 2);
        grid.add(new Label("Цена:"), 0, 3);
        grid.add(priceField, 1, 3);

        grid.add(new Separator(), 0, 4, 2, 1);

        Label specificLabel = new Label("Параметры квартиры:");
        grid.add(specificLabel, 0, 5, 2, 1);

        VBox apartmentBox = new VBox(5, renovationCheck, yearField, floorField);
        VBox landBox = new VBox(5, utilitiesCheck, distanceField);

        grid.add(apartmentBox, 0, 6, 2, 1);
        grid.add(landBox, 0, 6, 2, 1);
        landBox.setVisible(false);
        landBox.setManaged(false);

        typeComboBox.setOnAction(e -> {
            if (typeComboBox.getValue().equals("Квартира")) {
                apartmentBox.setVisible(true);
                apartmentBox.setManaged(true);
                landBox.setVisible(false);
                landBox.setManaged(false);
                specificLabel.setText("Параметры квартиры:");
            } else {
                apartmentBox.setVisible(false);
                apartmentBox.setManaged(false);
                landBox.setVisible(true);
                landBox.setManaged(true);
                specificLabel.setText("Параметры участка:");
            }
        });

        Button calcButton = new Button("Рассчитать стоимость");
        calcButton.setOnAction(e -> performCalculation(
                typeComboBox.getValue(),
                addressField.getText(),
                areaField.getText(),
                priceField.getText(),
                renovationCheck.isSelected(),
                yearField.getText(),
                floorField.getText(),
                utilitiesCheck.isSelected(),
                distanceField.getText()
        ));

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(150);

        VBox root = new VBox(menuBar, grid, calcButton, outputArea);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 450, 600);
        stage.setTitle("Расчет недвижимости (Вариант 3)");
        stage.setScene(scene);
        stage.show();
    }

    private void performCalculation(String type, String address, String areaStr, String priceStr,
                                    boolean hasRenovation, String yearStr, String floorStr,
                                    boolean hasUtilities, String distanceStr) {
        try {
            double area = Double.parseDouble(areaStr);
            double price = Double.parseDouble(priceStr);
            RealEstate realEstate;

            if (type.equals("Квартира")) {
                int year = Integer.parseInt(yearStr);
                int floor = Integer.parseInt(floorStr);
                realEstate = new Apartment(address, area, price, hasRenovation, year, floor);
            } else {
                double distance = Double.parseDouble(distanceStr);
                realEstate = new LandPlot(address, area, price, hasUtilities, distance);
            }

            outputArea.appendText(realEstate.getInfo() + "\n");
            outputArea.appendText("Стоимость за 1 кв.м: " + String.format("%.2f", realEstate.calculatePricePerSqM()) + "\n");
            outputArea.appendText("----------------------------------------\n");

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, введите корректные числовые значения.");
            alert.showAndWait();
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("calculation_result.txt", true)) {
            writer.write(outputArea.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Сохранение");
            alert.setHeaderText(null);
            alert.setContentText("Результаты сохранены в файл calculation_result.txt");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Лабораторная работа №4");
        alert.setContentText("Разработчик: Зайцев Герман Петрович ЭС-31\nВариант 3: Недвижимость");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}