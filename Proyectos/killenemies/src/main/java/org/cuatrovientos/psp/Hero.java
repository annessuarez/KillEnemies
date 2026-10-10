package org.cuatrovientos.psp;

public class Hero implements Character{
    private String name;
    private int numEnemigosMuertos;
    private int numAmigosDefendidos;

    //constructor
    public Hero(String name){
        this.name = name;
        this.numEnemigosMuertos = 0;
        this.numAmigosDefendidos = 0;
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    //aunq ponga que se muestre X mensaje y sea con el syso, hay que poner void.
    public void Attack(Enemy enemy){
       System.out.println("¡He atacado a un enemigo!");
       enemy.kill();
       numEnemigosMuertos++;
    }

    public void Defend(Friend friend){
        System.out.println("¡He defendido a un amigo!");
        friend.heal();
        numAmigosDefendidos++;
    }

    //Getters del hero
    public String getName() {
        return name;
    }

    public int getNumEnemigosMuertos() {
        return numEnemigosMuertos;
    }

    public int getNumAmigosDefendidos() {
        return numAmigosDefendidos;
    }

    
    
}
