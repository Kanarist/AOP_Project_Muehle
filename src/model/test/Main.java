package model.test;
import java.util.Scanner;

import model.BesetztesFeldExeption;
import model.MuehleMain;
import model.NichtBenachbartExeption;
import model.Spielbrett;
import model.Spieler;
import model.Spieler.Farbe;
import muehle.GUI.DrawBoard;
import muehle.GUI.MuehleFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MuehleMain game = new MuehleMain();
		MuehleFrame muehleGame = new MuehleFrame(game.getSpielbrett(), game.getDrawBoard());
        Scanner scanner = new Scanner(System.in);
        boolean spiel = true;
        while(spiel) {
        	System.out.println("from x: ");
            int a = scanner.nextInt();
            System.out.println("from y: ");
            int b = scanner.nextInt();
            System.out.println("to x: ");
            int c = scanner.nextInt();
            System.out.println("to y: ");
            int d = scanner.nextInt();
            game.handlePlayerAction(a, b, c, d);
        }

	}
}
