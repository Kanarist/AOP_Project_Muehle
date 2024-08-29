package model;

import java.util.Scanner;

public class MuehleLogik {

    private final Spielbrett spielbrett;
    private final Spieler spieler1;
	private final Spieler spieler2;
    private boolean isPlayerOneTurn;  // Bestimmt, welcher Spieler am Zug ist
    private int gesetzteSteine;
    
    private Runnable updateListener;
    
    public MuehleLogik() {
        // Initialisiere das Spielbrett und die Spieler
        this.spielbrett = Spielbrett.initialisiereBrett();
        this.spieler1 = new Spieler("Spieler 1", Spieler.Farbe.WEISS, 0);
        this.spieler2 = new Spieler("Spieler 2", Spieler.Farbe.SCHWARZ, 0);
        this.isPlayerOneTurn = true;  // Spieler 1 beginnt
        this.gesetzteSteine = 0;
    }

    public Spielbrett getSpielbrett() {
		return spielbrett;
	}

    public Spieler getSpieler1() {
		return spieler1;
	}

	public Spieler getSpieler2() {
		return spieler2;
	}

    public void setUpdateListener(Runnable updateListener) {
		this.updateListener = updateListener;
	}
    
    public boolean isSetPhase() {
    	return gesetzteSteine < 18;
    }

	public void handlePlayerAction(int fromX, int fromY, int toX, int toY) {
        if (isGameOver()) {
            System.out.println("Spiel vorbei! " + getWinner().getName() + " hat gewonnen!");
            return;
        }

        if (isSetPhase()) {
            startSetPhase(toX, toY);
        } else {
        	startMovePhase(fromX, fromY, toX, toY);
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
            gesetzteSteine++;
            updateBoard();
            if (spielbrett.pruefeMuele(x, y)) {
            	Scanner scanner = new Scanner(System.in);
                System.out.println("x: ");
                int a = scanner.nextInt();
                System.out.println("y: ");
                int b = scanner.nextInt();
            	this.removeStone(spielbrett, this.getOtherPlayer(), a, b);
            	updateBoard();
            }
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
            if (spielbrett.pruefeMuele(toX, toY)) {
            	Scanner scanner = new Scanner(System.in);
                System.out.println("x: ");
                int a = scanner.nextInt();
                System.out.println("y: ");
                int b = scanner.nextInt();
            	this.removeStone(spielbrett, this.getOtherPlayer(), a, b);
            	updateBoard();
            }
            isPlayerOneTurn = !isPlayerOneTurn;  // Spielerwechsel
        }
    }



    public Spieler getCurrentPlayer() {
        return isPlayerOneTurn ? spieler1 : spieler2;
    }

    private void updateBoard() {
    	if(updateListener != null) {
    		updateListener.run();
    	}
    }

    private boolean isGameOver() {
    	return !isSetPhase() 
    		&& (spieler1.getSteine() < 3 
				|| spieler2.getSteine() < 3 
				|| !hasValidMoves(spieler1)
				|| !hasValidMoves(spieler2));
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
    	if(spieler.getSteine() == 3 ) {
    		// mit 3 Steinen kann er springen
    		return true;
    	}
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
        int z = x + 1;
		while(z < felder.length && felder[y][z].getInhalt() == Feld.Inhalt.verboten) {
			z++;
		}
		if(z < felder.length && felder[y][z].getInhalt() == Feld.Inhalt.leer) {
			return true;
		}
		z = x - 1;
		while(z >= 0 && felder[y][z].getInhalt() == Feld.Inhalt.verboten) {
			z--;
		}
		if(z >= 0 && felder[y][z].getInhalt() == Feld.Inhalt.leer) {
			return true;
		}
		z = y + 1;
		while(z < felder.length && felder[z][x].getInhalt() == Feld.Inhalt.verboten) {
			z++;
		}
		if(z < felder.length && felder[z][x].getInhalt() == Feld.Inhalt.leer) {
			return true;
		}
		z = y - 1;
		while(z >= 0 && felder[z][x].getInhalt() == Feld.Inhalt.verboten) {
			z--;
		}
		if(z >= 0 && felder[z][x].getInhalt() == Feld.Inhalt.leer) {
			return true;
		}
        return false;
    }
    
    public Spieler getOtherPlayer() {
    	if(this.getCurrentPlayer() == spieler1) {
    		return spieler2;
    	}
    	return spieler1;
    }
    
    public boolean removeStone(Spielbrett spielbrett, Spieler player, int x, int y){
        boolean zugErfolgreich = false;
        while (!zugErfolgreich) {
            try {
            	this.getCurrentPlayer().entferneStein(spielbrett, x, y, player);
                }
            catch (FalscheFarbeExeption e) {
                System.out.println("Ungültiger Zug: Das Feld ist unbesetzt. Versuche es erneut.");
                return false;  // Der Zug ist nicht erfolgreich, also muss der Spieler erneut setzen
            }
            catch (BesetztesFeldExeption f) {
                System.out.println("Ungültiger Zug: Der Stein hat die falsche Farbe. Versuche es erneut.");
                return false;  // Der Zug ist nicht erfolgreich, also muss der Spieler erneut setzen
            }
        }
        return true;  // Der Zug war erfolgreich
    }
    
}
