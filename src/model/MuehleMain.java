package model;

import muehle.GUI.DrawBoard;

public class MuehleMain {

    private Spielbrett spielbrett;
    private Spieler spieler1;
    private Spieler spieler2;
    private boolean isPlayerOneTurn;  // Bestimmt, welcher Spieler am Zug ist
    private DrawBoard drawBoard; // Das GUI-Board, das aktualisiert wird
    private int gesetzteSteine;

    public MuehleMain(DrawBoard drawBoard) {
        // Initialisiere das Spielbrett und die Spieler
        this.spielbrett = Spielbrett.initialisiereBrett();
        this.spieler1 = new Spieler("Spieler 1", Spieler.Farbe.WEISS, 0);
        this.spieler2 = new Spieler("Spieler 2", Spieler.Farbe.SCHWARZ, 0);
        this.isPlayerOneTurn = true;  // Spieler 1 beginnt
        this.drawBoard = drawBoard;
        this.gesetzteSteine = 0;
    }

    public void handlePlayerAction(int fromX, int fromY, int toX, int toY) {
        if (isGameOver()) {
            System.out.println("Spiel vorbei! " + getWinner().getName() + " hat gewonnen!");
            return;
        }

        if (gesetzteSteine < 18) {
            startSetPhase(toX, toY);
        } else if (spieler1.getSteine() > 3 && spieler2.getSteine() > 3) {
            startMovePhase(fromX, fromY, toX, toY);
        } else {
            startJumpPhase(fromX, fromY, toX, toY);
        }

        if (isGameOver()) {
            System.out.println("Spiel vorbei! " + getWinner().getName() + " hat gewonnen!");
        }
    }

    private void startSetPhase(int x, int y) {
        // Setzphase
        SpielPhasen.SetPhase setPhase = new SpielPhasen.SetPhase();
        Spieler currentPlayer = getCurrentPlayer();
        boolean zugErfolgreich = setPhase.handleAction(spielbrett, currentPlayer, -1, -1, x, y);
        if (zugErfolgreich) {
            updateBoard();
            gesetzteSteine++;
            isPlayerOneTurn = !isPlayerOneTurn;  // Spielerwechsel
        }
    }

    private void startMovePhase(int fromX, int fromY, int toX, int toY) {
        // Zugphase
        SpielPhasen.MovePhase movePhase = new SpielPhasen.MovePhase();
        Spieler currentPlayer = getCurrentPlayer();
        boolean zugErfolgreich = movePhase.handleAction(spielbrett, currentPlayer, fromX, fromY, toX, toY);
        if (zugErfolgreich) {
            updateBoard();
            isPlayerOneTurn = !isPlayerOneTurn;  // Spielerwechsel
        }
    }

    private void startJumpPhase(int fromX, int fromY, int toX, int toY) {
        // Sprungphase
        SpielPhasen.JumpPhase jumpPhase = new SpielPhasen.JumpPhase();
        Spieler currentPlayer = getCurrentPlayer();
        boolean zugErfolgreich = jumpPhase.handleAction(spielbrett, currentPlayer, fromX, fromY, toX, toY);
        if (zugErfolgreich) {
            updateBoard();
            isPlayerOneTurn = !isPlayerOneTurn;  // Spielerwechsel
        }
    }

    private Spieler getCurrentPlayer() {
        return isPlayerOneTurn ? spieler1 : spieler2;
    }

    private void updateBoard() {
        drawBoard.updateBoard();
    }

    private boolean isGameOver() {
        return spieler1.getSteine() < 3 || spieler2.getSteine() < 3 || !hasValidMoves(spieler1) || !hasValidMoves(spieler2);
    }

    private Spieler getWinner() {
        if (spieler1.getSteine() < 3 || !hasValidMoves(spieler1)) {
            return spieler2;
        } else {
            return spieler1;
        }
    }

    private boolean hasValidMoves(Spieler spieler) {
        // Überprüfe, ob der Spieler noch gültige Züge hat
        Feld[][] felder = spielbrett.getFelder();
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                if (felder[i][j].getInhalt() == Feld.farbeFeld(spieler)) {
                    // Überprüfe mögliche Züge für diesen Stein
                    if (canMove(i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canMove(int x, int y) {
        // Überprüfe, ob ein Stein an Position (x, y) bewegt werden kann
        Feld[][] felder = spielbrett.getFelder();
        // Beispiele für mögliche Bewegungen prüfen
        if (x > 0 && felder[y][x - 1].getInhalt() == Feld.Inhalt.leer) return true;
        if (x < felder.length - 1 && felder[y][x + 1].getInhalt() == Feld.Inhalt.leer) return true;
        if (y > 0 && felder[y - 1][x].getInhalt() == Feld.Inhalt.leer) return true;
        if (y < felder.length - 1 && felder[y + 1][x].getInhalt() == Feld.Inhalt.leer) return true;
        return false;
    }

    public static void main(String[] args) {
        // Initialisiere hier das GUI und das Spielbrett
        Spielbrett spielbrett = Spielbrett.initialisiereBrett();
        DrawBoard drawBoard = new DrawBoard();
        MuehleMain game = new MuehleMain(drawBoard);
        
        // Das Starten des Spiels und die Interaktion erfolgen über die GUI
    }
}
