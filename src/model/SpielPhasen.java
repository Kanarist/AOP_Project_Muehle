package model;

import model.exception.BesetztesFeldException;
import model.exception.FalscheFarbeException;
import model.exception.LogikException;
import model.exception.NichtBenachbartExecption;

public class SpielPhasen {

    // Inneres Interface
    public interface SpielPhase {
        void handleAction(Spielbrett spielbrett, Spieler player,int fromX, int fromY, int toX, int toY) throws LogikException;
    }

    // Setzphase
    public static class SetPhase implements SpielPhase {
        @Override
        
        //prueft ob Feld leer
        public void handleAction(Spielbrett spielbrett, Spieler player, int fromX, int fromY, int toX, int toY) throws LogikException {
            if (spielbrett.istFeldFrei(toX, toY)) {
            	//setzt Feld zur Spielerfarbe 
                spielbrett.getFelder()[toY][toX].setInhalt(Feld.farbeFeld(player));
                player.setSteine(player.getSteine() + 1);
            } else {
                throw new BesetztesFeldException();
            }
        }
    }

    // Zugphase
    public static class MovePhase implements SpielPhase {
        @Override
        
        //prueft ob from-Feld besetzt, to-Feld leer, passende Farbe und wenn Spieler mehr als 3 Steine ob benachbart
        public void handleAction(Spielbrett spielbrett, Spieler player, int fromX, int fromY, int toX, int toY) throws LogikException {
            if (!spielbrett.istFeldFrei(toX, toY) || spielbrett.istFeldFrei(fromX, fromY)) {
                throw new BesetztesFeldException();
            }
            if (player.getSteine() > 3 && !spielbrett.istBenachbart(fromX, fromY, toX, toY)) {
                throw new NichtBenachbartExecption();
            }
            if (spielbrett.getFelder()[fromY][fromX].getInhalt() != Feld.farbeFeld(player)) {
                throw new FalscheFarbeException();
            }
            //setzt altes Feld leer und neues Feld zur Spielerfarbe 
            spielbrett.getFelder()[toY][toX].setInhalt(spielbrett.getFelder()[fromY][fromX].getInhalt());
            spielbrett.getFelder()[fromY][fromX].setInhalt(Feld.Inhalt.leer);        }
    }

}
