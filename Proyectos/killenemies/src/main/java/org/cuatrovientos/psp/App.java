package org.cuatrovientos.psp;

import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) {
        ArrayList<Character> personajes = CrearListaYShuffle();

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
    private static ArrayList<Character> CrearListaYShuffle() {
        ArrayList<Character> personajes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            personajes.add(new Friend());
            personajes.add(new Enemy());
        }

        Collections.shuffle(personajes);

        return personajes;
    }
}
