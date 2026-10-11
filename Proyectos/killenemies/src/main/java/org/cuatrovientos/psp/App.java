package org.cuatrovientos.psp;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String RUTA_ARCHIVO = "partida.json";
        EstadoJuego eJ = cargarPartidajson(RUTA_ARCHIVO);
        Hero hero;
        ArrayList<Character> personajes;
        String datoLeer;

        //muestra de la versión 1
        System.out.println("== Muestra versión 1 ==");
        mostrarLucha(crearListaYShuffle());
        System.out.println("");

        //muestra version 2 con persistencia de datos
        if (eJ == null || eJ.getPersonajes() == null || eJ.getPersonajes().isEmpty()) {
            System.out.println("== Partida nueva ==");
            System.out.print("Introduce el nombre para tu heroe: ");
            datoLeer = scan.nextLine();
            hero = new Hero(datoLeer);
            personajes = crearListaYShuffle();
        } else {
            System.out.println("== Partida previa ==");
            hero = eJ.getHero();
            personajes = eJ.getPersonajes();
            System.out.println("Heroe seleccionado: " + hero.getName());
        }

        boolean jugando = true;
        do {
            showCharacters(personajes);
            boolean esCorrecto = false;
            int accion = 0;
            do {
                System.out.println("Opciones");
                System.out.println("1. Atacar personaje");
                System.out.println("2. Salvar personaje");
                System.out.println("3. Guardar partida y fin");
                System.out.println("Opción seleccionada: ");
                datoLeer = scan.nextLine();

                try {
                    accion = Integer.parseInt(datoLeer);
                    if (accion >= 1 && accion <= 3) {
                        esCorrecto = true;
                    } else {
                        esCorrecto = false;
                        System.err.println("Número fuera de rango (1, 2 o 3)");
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Número inválido");
                }

            } while (esCorrecto == false);

            if (accion == 3) {
                guardarPartidajson(new EstadoJuego(personajes, hero), RUTA_ARCHIVO); // hay que hacer esto porq es un
                                                                                     // juego nuevo
                System.out.println("Partida guardada");
                jugando = false;
                break;
            }

            esCorrecto = true;
            int indicePersonaje = -1;
            
            do {
            System.out.println("Introduce número de personaje (0...,"+ (personajes.size() - 1)+"): ");
            datoLeer = scan.nextLine();
                try {
                    indicePersonaje = Integer.parseInt(datoLeer);
                    if (indicePersonaje < 0 || indicePersonaje >= personajes.size()) {
                        System.err.println("Indice fuera de rango.");
                        esCorrecto = false;
                    } else {
                        esCorrecto = true;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Indice inválido");
                    esCorrecto = false;
                }
            } while (esCorrecto == false);

            Character personaje = personajes.get(indicePersonaje);
            if (accion == 1) {
                if (personaje.isEnemy()) { // la opcion elegida es un enemigo
                    Enemy enemy = (Enemy) personaje;
                    System.out.println("¡Has atacado a un enemigo, bien hecho!");
                    hero.attack(enemy);
                    personajes.remove(indicePersonaje);
                } else {
                    System.out.println("Tolai, ¡Que es un amigo!");
                    personajes.remove(personaje);
                }
            } else if (accion == 2) {
                if (personaje.isEnemy()) {
                    System.out.println("Tolai, ¡Que es un enemigo! Se han duplicado.");
                    personajes.add(new Enemy());
                } else {
                    Friend friend = (Friend) personaje;
                    System.out.println("¡Has defendido a un amigo, bien hecho!");
                    hero.defend(friend);
                }
            }

            if (personajes.isEmpty()) {
                System.out.println("Ya no quedan personajes");
                jugando = false;
            }
            System.out.println("== Resumen de " + hero.getName() + " ==");
            System.out.println("Enemigos matados: " + hero.getNumEnemigosMuertos());
            System.out.println("Amigos defendidos: " + hero.getNumAmigosDefendidos());

        } while (jugando == true);

        scan.close();
    }

    // lucha sin heroe
    public static void mostrarLucha(ArrayList<Character> personajes) {
        for (int i = 0; i < personajes.size(); i++) {
            Character personaje = personajes.get(i);
            if (personaje instanceof Enemy enemy) {
                System.out.println("El personaje " + (i + 1) + " es un enemigo! ¡Mátalo!");
                System.out.println(enemy.kill());
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

        for (int i = 0; i < personajes.size(); i++) {
            Character character = personajes.get(i);
            String tipo;

            if (character.isEnemy()) {
                tipo = "Enemigo";
                enemigos++;
            } else {
                tipo = "Amigo";
                amigos++;
            }
            System.out.println("Personaje [" + i + "]: " + tipo);
        }
        System.out.println("Recuento total: Amigos (" + amigos + ") y Enemigos (" + enemigos + ")");
    }

    // Persistencia de Datos
    private static void guardarPartidajson(EstadoJuego eJ, String ruta) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        if (!fichero.exists()) {
            return null;
        }

        Gson gson = new Gson();

        try (FileReader fr = new FileReader(ruta)) {
            return gson.fromJson(fr, EstadoJuego.class);

        } catch (IOException e) {
            System.out.println("No se ha podido cargar la partida: " + e.getMessage());
            return null;
        }
    }

}
