package model.test;
import model.BesetztesFeldExeption;
import model.NichtBenachbartExeption;
import model.Spielbrett;
import model.Spieler;
import model.Spieler.Farbe;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Spielbrett muehle = Spielbrett.initialisiereBrett();
		
		Spieler spieler1 = new Spieler("Martin", Farbe.SCHWARZ, 0);
		Spieler spieler2 = new Spieler("Sebastian", Farbe.WEISS, 0);
		
		try {
			muehle.setzeStein(spieler1, 0, 0);
			muehle.setzeStein(spieler2, 1, 1);
			muehle.setzeStein(spieler1, 3, 0);
			muehle.setzeStein(spieler2, 7, 7);
			muehle.setzeStein(spieler1, 3, 1);
			muehle.setzeStein(spieler2, 1, 3);
			muehle.setzeStein(spieler1, 3, 2);
			muehle.bewegeStein(1, 3, 1, 6);
			muehle.springeStein(1, 1, 0, 7);
			muehle.entferneStein(spieler1, 0, 0);
			if (muehle.pruefeMuele(1, 0)) {
				System.out.println("muele");
			}
			else {
				System.out.println("keine muele");
			}
		} catch (BesetztesFeldExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NichtBenachbartExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		muehle.BrettAusgeben();
		System.out.println(spieler1.getSteine());
		System.out.println(spieler2.getSteine());

	}
}
