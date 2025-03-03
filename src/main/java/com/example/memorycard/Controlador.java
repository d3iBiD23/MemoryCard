package com.example.memorycard;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Controlador implements Initializable {

    ArrayList<String> botonesPosibles = new ArrayList<>(Arrays.asList("boton0", "boton1", "boton2", "boton3", "boton4",
            "boton5", "boton6", "boton7", "boton8"));

    ArrayList<Button> botones = new ArrayList<>();

    ArrayList<String> patron = new ArrayList<>();

    int ordenPatron = 0;

    Random random = new Random();

    int contador = 0;
    int turno = 1;

    @FXML
    private Button boton0;

    @FXML
    private Button boton1;

    @FXML
    private Button boton2;

    @FXML
    private Button boton3;

    @FXML
    private Button boton4;

    @FXML
    private Button boton5;

    @FXML
    private Button boton6;

    @FXML
    private Button boton7;

    @FXML
    private Button boton8;

    @FXML
    private Text texto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        botones.addAll(Arrays.asList(boton0, boton1, boton2, boton3, boton4,
                boton5, boton6, boton7, boton8));
    }

    @FXML
    void buttonClicked(ActionEvent event) {

        if (((Control) event.getSource()).getId().equals(patron.get(contador))) {
            texto.setText("Correct " + contador);
            Button button = botones.get(getIndexBoton(event));
            cambiarColorBoton(button, "-fx-base: lightgreen");
            contador++;
        } else {
            Button button = botones.get(getIndexBoton(event));
            cambiarColorBoton(button, "-fx-base: red");
            texto.setText("Wrong");
            return;
        }

        if (contador == turno) {
            siguienteTurno();
        }
    }

    @FXML
    void start(ActionEvent event) {
        patron.clear();

        patron.add(botonesPosibles.get(random.nextInt(botonesPosibles.size())));
        mostrarPatron();
        System.out.println(patron);

        contador = 0;
        turno = 1;
    }

    private void siguienteTurno() {
        contador = 0;
        turno++;

        patron.add(botonesPosibles.get(random.nextInt(botonesPosibles.size())));
        mostrarPatron();
        System.out.println(patron);
    }

    private int getIndexBoton(ActionEvent event) {
        String idBoton = ((Control) event.getSource()).getId();
        return Integer.parseInt(idBoton.substring(idBoton.length() - 1));
    }

    private int getIndexBoton(String boton) {
        return Integer.parseInt(boton.substring(boton.length() - 1));
    }

    private void mostrarPatron() {
        PauseTransition pausa = new PauseTransition(
                Duration.seconds(1));
        pausa.setOnFinished(e -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.7), event -> {
                mostrarSiguiente();
            }));
            timeline.setCycleCount(patron.size());
            timeline.play();
        });
        pausa.play();
    }

    private void mostrarSiguiente() {
        Button button = botones.get(getIndexBoton(patron.get(ordenPatron)));
        cambiarColorBoton(button, "-fx-base: gray");
        ordenPatron++;

        if (ordenPatron == turno) {
            ordenPatron = 0;
        }
    }

    private void cambiarColorBoton(Button boton, String color) {
        boton.setStyle(color);
        PauseTransition pausa = new PauseTransition(
                Duration.seconds(0.5));
        pausa.setOnFinished(e -> {
            boton.setStyle(null);
        });
        pausa.play();
    }
}