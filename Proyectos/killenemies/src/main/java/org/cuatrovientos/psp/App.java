package org.cuatrovientos.psp;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.google.gson.Gson;

public class App {
    private static final String RUTA_ARCHIVO = "partida.json";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Character> personajes = null;

        // persistencia de datos
        try {
            personajes = cargarPartidajson(RUTA_ARCHIVO);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al intetar cargar la partida.");
        }

        Hero hero;

        if (personajes == null || personajes.isEmpty()) {
            System.out.println("== Partida nueva ==");
            System.out.print("Introduce el nombre para tu heroe: ");
            String datoLeer = scan.nextLine();
            hero = new Hero(datoLeer);

            personajes = crearListaYShuffle();
        } else {
            System.out.println("== Partida previa ==");
            showCharacters(personajes);
            System.out.print("Introduce el nombre para tu heroe: ");
            String datoLeer = scan.nextLine();

        }

        showCharacters(personajes);
        scan.close();
    }

    public static void mostrarLucha(ArrayList<Character> personajes) {
        for (int i = 0; i < personajes.size(); i++) {
            Character personaje = personajes.get(i);
            if (personaje instanceof Enemy enemy) {
                System.err.println("El personaje " + (i + 1) + " es un enemigo! ¡Mátalo!");
                System.err.println(enemy.kill());
            } else {
                System.out.println("El personaje " + (i + 1) + " es un amigo!");
            }
        }
    }

    // pertenece a la clase y no al objeto, en JAVA siempre static
    private static ArrayList<Character> crearListaYShuffle() {
        ArrayList<Character> personajes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            personajes.add(new Friend());
            personajes.add(new Enemy());
        }

        Collections.shuffle(personajes);

        return personajes;
    }

    // Contar Amigos y Enemigos
    public static void showCharacters(ArrayList<Character> personajes) {
        System.out.println("=== Personajes: Enemigos y Amigos ===");
        int amigos = 0;
        int enemigos = 0;

        for (Character character : personajes) {
            if (character.isEnemy()) {
                enemigos++;
            } else {
                amigos++;
            }
        }

        System.out.println("Recuento total: ");
        System.out.println("Enemigos: " + enemigos);
        System.out.println("Amigos: " + amigos);
    }

    // Persistencia de Datos
    private static void guardarPartidajson(EstadoJuego eJ, String ruta) {
        Gson gson = new Gson();
        // Guardar
        try (FileWriter fw = new FileWriter(ruta)) {
            gson.toJson(eJ, fw);

        } catch (IOException e) {
            // la ruta puede estar vacía y que no exista. Con esto ya lo comprueba:
            System.out.println("No se ha podido guardar la partida: " + e.getMessage());
        }
    }

    private static EstadoJuego cargarPartidajson(String ruta) {
        File fichero = new File(ruta);
        if (!fichero.exists ()){
            return fichero = null;
        }

        Gson gson = new Gson();

        try (FileReader fr = new FileReader(ruta)) {
            return gson.fromJson(fr, EstadoJuego.class);
            
        } catch (IOException e) {
            System.out.println("No se ha podido cargar la partida: " + e.getMessage());
            return null;
        }
        return null;
    }

}
