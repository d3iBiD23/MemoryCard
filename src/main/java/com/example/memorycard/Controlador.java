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

    // Lista con los identificadores de los botones disponibles.
    ArrayList<String> botonesPosibles = new ArrayList<>(Arrays.asList(
            "boton0", "boton1", "boton2", "boton3", "boton4",
            "boton5", "boton6", "boton7", "boton8"));

    // Lista de objetos Button que se llenará al iniciar la aplicación.
    ArrayList<Button> botones = new ArrayList<>();

    // Lista que almacena la secuencia que el jugador debe recordar.
    ArrayList<String> patron = new ArrayList<>();

    int ordenPatron = 0; // Índice para mostrar la secuencia de forma ordenada.
    Random random = new Random();
    int contador = 0; // Contador para validar la secuencia ingresada por el usuario.
    int turno = 1; // Indica el turno o ronda actual del juego.

    // Inyección de los botones definidos en el archivo FXML.
    @FXML private Button boton0;
    @FXML private Button boton1;
    @FXML private Button boton2;
    @FXML private Button boton3;
    @FXML private Button boton4;
    @FXML private Button boton5;
    @FXML private Button boton6;
    @FXML private Button boton7;
    @FXML private Button boton8;

    // Control para mostrar mensajes al usuario (ej. "Correct" o "Wrong").
    @FXML private Text texto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Se agregan todos los botones a la lista para facilitar su manejo.
        botones.addAll(Arrays.asList(boton0, boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8));
    }

    // Metodo invocado al hacer clic en cualquiera de los botones del juego.
    @FXML
    void buttonClicked(ActionEvent event) {
        // Compara el ID del botón presionado con el elemento correspondiente del patrón.
        if (((Control) event.getSource()).getId().equals(patron.get(contador))) {
            texto.setText("Correct " + contador); // Mensaje de acierto.
            Button button = botones.get(getIndexBoton(event));
            cambiarColorBoton(button, "-fx-base: lightgreen"); // Ilumina el botón en verde.
            contador++;
        } else {
            // Si el botón no coincide con el patrón, se muestra un error.
            Button button = botones.get(getIndexBoton(event));
            cambiarColorBoton(button, "-fx-base: red"); // Ilumina el botón en rojo.
            texto.setText("Wrong");
            return;
        }

        // Si el usuario ha completado correctamente la secuencia del turno actual, inicia el siguiente turno.
        if (contador == turno) {
            siguienteTurno();
        }
    }

    // Metodo invocado al presionar el botón 'Start' para iniciar el juego.
    @FXML
    void start(ActionEvent event) {
        patron.clear(); // Limpia cualquier secuencia previa.
        // Añade un botón aleatorio a la secuencia.
        patron.add(botonesPosibles.get(random.nextInt(botonesPosibles.size())));
        mostrarPatron(); // Muestra la secuencia al usuario.
        System.out.println(patron); // Imprime el patrón en la consola (útil para depuración).
        contador = 0; // Reinicia el contador.
        turno = 1; // Inicia en la primera ronda.
    }

    // Inicia el siguiente turno añadiendo un nuevo botón a la secuencia.
    private void siguienteTurno() {
        contador = 0; // Reinicia el contador de aciertos.
        turno++; // Incrementa la ronda.
        // Añade un nuevo botón aleatorio al patrón.
        patron.add(botonesPosibles.get(random.nextInt(botonesPosibles.size())));
        mostrarPatron(); // Muestra la secuencia actualizada.
        System.out.println(patron);
    }

    // Obtiene el índice del botón en la lista a partir del evento.
    private int getIndexBoton(ActionEvent event) {
        String idBoton = ((Control) event.getSource()).getId();
        return Integer.parseInt(idBoton.substring(idBoton.length() - 1));
    }

    // Método auxiliar que extrae el índice del botón a partir de su ID (cadena).
    private int getIndexBoton(String boton) {
        return Integer.parseInt(boton.substring(boton.length() - 1));
    }

    // Muestra la secuencia (patrón) utilizando animaciones.
    private void mostrarPatron() {
        // Pausa inicial para darle tiempo al usuario a prepararse.
        PauseTransition pausa = new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished(e -> {
            // Utiliza una Timeline para mostrar cada botón del patrón con un intervalo.
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.7), event -> {
                mostrarSiguiente();
            }));
            timeline.setCycleCount(patron.size());
            timeline.play();
        });
        pausa.play();
    }

    // Muestra el siguiente botón de la secuencia cambiando su color temporalmente.
    private void mostrarSiguiente() {
        Button button = botones.get(getIndexBoton(patron.get(ordenPatron)));
        cambiarColorBoton(button, "-fx-base: gray"); // Ilumina el botón en gris.
        ordenPatron++; // Avanza al siguiente elemento en la secuencia.

        // Si se ha mostrado toda la secuencia, se reinicia el índice.
        if (ordenPatron == turno) {
            ordenPatron = 0;
        }
    }

    // Cambia el color del botón temporalmente y luego restaura su estilo original.
    private void cambiarColorBoton(Button boton, String color) {
        boton.setStyle(color); // Aplica el color indicado.
        // Pausa para que el cambio de color sea visible.
        PauseTransition pausa = new PauseTransition(Duration.seconds(0.5));
        pausa.setOnFinished(e -> {
            boton.setStyle(null); // Restaura el estilo original del botón.
        });
        pausa.play();
    }
}