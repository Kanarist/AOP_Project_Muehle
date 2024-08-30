package model;

import model.exception.BesetztesFeldException;
import model.exception.FalscheFarbeExeption;
import model.exception.LogikException;
import model.exception.NichtBenachbartExeption;

public class SpielPhasen {

    // Inneres Interface
    public interface SpielPhase {
        void handleAction(Spielbrett spielbrett, Spieler player,int fromX, int fromY, int toX, int toY) throws LogikException;
    }

    // Setzphase
    public static class SetPhase implements SpielPhase {
        @Override
        public void handleAction(Spielbrett spielbrett, Spieler player, int fromX, int fromY, int toX, int toY) throws LogikException {
            if (spielbrett.istFeldFrei(toX, toY)) {
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
        public void handleAction(Spielbrett spielbrett, Spieler player, int fromX, int fromY, int toX, int toY) throws LogikException {
            if (!spielbrett.istFeldFrei(toX, toY) || spielbrett.istFeldFrei(fromX, fromY)) {
                throw new BesetztesFeldException();
            }
            if (player.getSteine() > 3 && !spielbrett.istBenachbart(fromX, fromY, toX, toY)) {
                throw new NichtBenachbartExeption();
            }
            if (spielbrett.getFelder()[fromY][fromX].getInhalt() != Feld.farbeFeld(player)) {
                throw new FalscheFarbeExeption();
            }
            spielbrett.getFelder()[toY][toX].setInhalt(spielbrett.getFelder()[fromY][fromX].getInhalt());
            spielbrett.getFelder()[fromY][fromX].setInhalt(Feld.Inhalt.leer);        }
    }

}
