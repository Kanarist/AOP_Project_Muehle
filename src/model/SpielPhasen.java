package model;

public class SpielPhasen {

    // Inneres Interface
    public interface SpielPhase {
        boolean handleAction(Spielbrett spielbrett, Spieler player, int fromX, int fromY, int toX, int toY);
    }

    // Setzphase
    public static class SetPhase implements SpielPhase {
        @Override
        public boolean handleAction(Spielbrett spielbrett, Spieler player, int fromX, int fromY, int toX, int toY) {
            boolean zugErfolgreich = false;
            while (!zugErfolgreich) {
                try {
                    if (spielbrett.istFeldFrei(toX, toY)) {
                        spielbrett.getFelder()[toY][toX].setInhalt(Feld.farbeFeld(player));
                        player.setSteine(player.getSteine() + 1);
                        zugErfolgreich = true;
                    } else {
                        throw new BesetztesFeldExeption();
                    }
                } catch (BesetztesFeldExeption e) {
                    System.out.println("Ungültiger Zug: Das Feld ist bereits besetzt. Versuche es erneut.");
                    return false;  // Der Zug ist nicht erfolgreich, also muss der Spieler erneut setzen
                }
            }
            return true;  // Der Zug war erfolgreich
        }
    }

    // Zugphase
    public static class MovePhase implements SpielPhase {
        @Override
        public boolean handleAction(Spielbrett spielbrett, Spieler player, int fromX, int fromY, int toX, int toY) {
            boolean zugErfolgreich = false;
            while (!zugErfolgreich) {
                try {
                    if (!spielbrett.istFeldFrei(toX, toY) || spielbrett.istFeldFrei(fromX, fromY)) {
                        throw new BesetztesFeldExeption();
                    }
                    if (player.getSteine() > 3 && !spielbrett.istBenachbart(fromX, fromY, toX, toY)) {
                        throw new NichtBenachbartExeption();
                    }
                    if (spielbrett.getFelder()[fromY][fromX].getInhalt() != Feld.farbeFeld(player)) {
                        throw new FalscheFarbeExeption();
                    }
                    spielbrett.getFelder()[toY][toX].setInhalt(spielbrett.getFelder()[fromY][fromX].getInhalt());
                    spielbrett.getFelder()[fromY][fromX].setInhalt(Feld.Inhalt.leer);
                    zugErfolgreich = true;
                } catch (BesetztesFeldExeption | NichtBenachbartExeption | FalscheFarbeExeption e) {
                    System.out.println("Ungültiger Zug: " + e.getMessage() + " Versuche es erneut.");
                    return false;  // Der Zug ist nicht erfolgreich, also muss der Spieler erneut ziehen
                }
            }
            return true;  // Der Zug war erfolgreich
        }
    }
}
