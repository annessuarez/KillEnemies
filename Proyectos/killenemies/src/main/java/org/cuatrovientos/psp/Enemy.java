package org.cuatrovientos.psp;

public class Enemy implements Character {
    @Override
	public boolean isEnemy() {
		return true;
	}
	
	public String kill() {
		return "Ahg, me mataste, bastardo!";
	}
}
