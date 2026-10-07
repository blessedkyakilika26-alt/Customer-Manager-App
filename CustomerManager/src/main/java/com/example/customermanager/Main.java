package com.example.customermanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    // List that stores our customers
    ObservableList<Customer> customers =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        // ==========================================
        // 1. CUSTOMER NAME INPUT
        // ==========================================

        TextField nameField = new TextField();

        nameField.setPromptText("e.g., Mary Banda");

        Label nameLabel = new Label("Customer name");

        nameLabel.setLabelFor(nameField);


        // ==========================================
        // 2. PROVINCE INPUT
        // ==========================================

        ComboBox<String> provinceBox = new ComboBox<>();

        provinceBox.getItems().addAll(
                "Central",
                "Lusaka",
                "Copperbelt",
                "luapula",
                "muchinga",
                "northern",
                "southern",
                "western",
                "North western",
                "Eastern"
        );

        provinceBox.setPromptText("Choose a province");


        // ==========================================
        // 3. STATUS MESSAGE
        // ==========================================

        Label status = new Label();


        // ==========================================
        // 4. TABLEVIEW
        // ==========================================

        TableView<Customer> table = new TableView<>();

        table.setItems(customers);


        // ==========================================
        // 5. CUSTOMER NAME COLUMN
        // ==========================================

        TableColumn<Customer, String> nameCol =
                new TableColumn<>("Customer name");

        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        table.getColumns().add(nameCol);


        // ==========================================
        // 6. PROVINCE COLUMN
        // ==========================================

        TableColumn<Customer, String> provinceCol =
                new TableColumn<>("Province");

        provinceCol.setCellValueFactory(
                new PropertyValueFactory<>("province")
        );

        table.getColumns().add(provinceCol);


        // ==========================================
        // 7. SAVE BUTTON
        // ==========================================

        Button saveButton =
                new Button("Save customer");


        // Save button action
        saveButton.setOnAction(event -> {

            // Get customer name
            String name =
                    nameField.getText().trim();


            // Validate customer name
            if (name.isEmpty()) {

                status.setText(
                        "Enter the customer name."
                );

                nameField.requestFocus();

                return;
            }


            // Get selected province
            String province =
                    provinceBox.getValue();


            // Validate province
            if (province == null) {

                status.setText(
                        "Choose a province."
                );

                provinceBox.requestFocus();

                return;
            }


            customers.add(
                    new Customer(name, province)
            );


            status.setText(
                    "Customer saved."
            );

            nameField.clear();

            provinceBox.setValue(null);

        });


        Button deleteButton =
                new Button("Delete customer");


        // Delete button action
        deleteButton.setOnAction(event -> {

            // Get selected customer
            Customer selected =
                    table.getSelectionModel()
                            .getSelectedItem();


            // Check whether a customer was selected
            if (selected == null) {

                status.setText(
                        "Select a customer first."
                );

                return;
            }


            // Create Delete button
            ButtonType delete =
                    new ButtonType("Delete");


            // Confirmation dialog
            Alert ask = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Delete the selected customer?",
                    delete,
                    ButtonType.CANCEL
            );


            ask.setHeaderText(
                    "Confirm deletion"
            );


            // Check user's choice
            if (ask.showAndWait()
                    .orElse(ButtonType.CANCEL)
                    == delete) {

                // Remove customer
                customers.remove(selected);


                // Display message
                status.setText(
                        "Customer deleted."
                );
            }

        });


        VBox root =
                new VBox(10);


        root.getChildren().addAll(

                nameLabel,

                nameField,

                provinceBox,

                saveButton,

                deleteButton,

                status,

                table
        );


        Scene scene =
                new Scene(
                        root,
                        500,
                        400
                );



        stage.setScene(scene);

        stage.setTitle(
                "Customer Manager"
        );

        stage.show();

    }


    public static void main(String[] args) {

        launch(args);

    }
}