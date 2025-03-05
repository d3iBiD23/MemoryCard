package com.example.memorycard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carga el archivo FXML que define la interfaz gráfica del juego.
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));

        // Crea la escena usando el contenido del FXML.
        Scene scene = new Scene(fxmlLoader.load());

        // Configura el título de la ventana.
        stage.setTitle("Memory Card Game");

        // Establece la escena en el escenario principal.
        stage.setScene(scene);

        // Muestra la ventana en pantalla.
        stage.show();
    }

    public static void main(String[] args) {
        // Lanza la aplicación JavaFX.
        launch();
    }
}