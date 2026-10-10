package org.cuatrovientos.psp;

import java.util.ArrayList;

public class EstadoJuego {
    private Hero hero;
    private ArrayList<String> personajesString;

    public EstadoJuego(){

    }

    public EstadoJuego(ArrayList<Character> personajes, Hero hero){
        this.hero = hero;
        this.personajesString = new ArrayList<>();
        //casteo para saber que tipo de personaje es
        for (Character per : personajes) {
            if (per.isEnemy()) {
                personajesString.add("Enemie");
            }else{
                personajesString.add("Friend");
            }
        }
    }

    public Hero getHero(){
        return hero;
    }

    public void setHero(Hero hero){
        this.hero = hero;
    }

    //dependiendo de lo que haya en el arrayList de String, se crea un enemie o frined.
    public ArrayList<Character> getPersonajes(){
       ArrayList<Character> listaPersonajes = new ArrayList<>();
       if (personajesString != null){
        for (String tipo : personajesString){
            if (tipo.equals("Enemie")){
                listaPersonajes.add(new Enemy());
            }else{
                listaPersonajes.add(new Friend());
            }
        }
       }
       return listaPersonajes;
    }
}
