/*
 * Author: Víctor Medrano Rodríguez
 * Date of creation: i dont remember, but probably at the beginning of November
 * File: Main.java
 * Description: A homemade snake game
 */

package main;

import gui.MainFrame;

public class Main {

	public static void main(String[] args) {

		try {
			new MainFrame();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
