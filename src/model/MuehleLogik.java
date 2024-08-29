package model;

import muehle.GUI.Position;

public class MuehleLogik {
	
    private final Spielbrett spielbrett;
    private final Spieler spieler1;
	private final Spieler spieler2;
    private boolean isPlayerOneTurn;  // Bestimmt, welcher Spieler am Zug ist
    private int gesetzteSteine;
    
    private boolean removeStoneStatus;
    
	private Runnable updateListener;
    
    public MuehleLogik() {
        // Initialisiere das Spielbrett und die Spieler
        this.spielbrett = Spielbrett.initialisiereBrett();
        this.spieler1 = new Spieler("Spieler 1", Spieler.Farbe.WEISS, 0);
        this.spieler2 = new Spieler("Spieler 2", Spieler.Farbe.SCHWARZ, 0);
        this.isPlayerOneTurn = true;  // Spieler 1 beginnt
        this.gesetzteSteine = 0;
    }

    public void setUpdateListener(Runnable updateListener) {
		this.updateListener = updateListener;
	}
    
	public boolean isRemoveStoneStatus() {
		return removeStoneStatus;
	}

	public Feld[][] getFelder() {
		return spielbrett.getFelder();
	}
	
	public boolean isSelPosition(int x, int y) {
		Position selPosition = spielbrett.getSelPostion();
		return selPosition != null && selPosition.getXAxis() == x && selPosition.getYAxis() == y;
	}
    
	public void handlePlayAction(int x, int y) {
        if (isGameOver()) {
            System.out.println("Spiel vorbei! " + getWinner().getName() + " hat gewonnen!");
            return;
        }
        
        boolean zugErfolgreich = false; 		

        if(removeStoneStatus) {
        	zugErfolgreich = handleRemoveStone(x, y);
        } 
        else if (isSetPhase()) {
        	zugErfolgreich = startSetPhase(x, y);
        } else {
        	Position position = spielbrett.getSelPostion();
        	if(position != null) {
            	zugErfolgreich = startMovePhase(position.getXAxis(), position.getYAxis(), x, y);
            	if(zugErfolgreich) {
            		spielbrett.setSelPostion(null);
            	}
        	} else {
				if(spielbrett.getFelder()[y][x].gehoertSpieler(getCurrentPlayer())) {
	        		spielbrett.setSelPostion(new Position(x,y));
					System.out.println("Stein ausgew�hlt bei Position: (" + x + ", " + y + ")");
				} else {
					System.out.println("Kein g�ltiger Stein zum Ausw�hlen!(" + x + ", " + y + ")");
				}
        	}
        }

        if (zugErfolgreich) {
            if (isGameOver()) {
                System.out.println("Spiel vorbei! " + getWinner().getName() + " hat gewonnen!");
            } else {
                removeStoneStatus = spielbrett.pruefeMuehle(x, y);
                if(!removeStoneStatus) {
                	isPlayerOneTurn = !isPlayerOneTurn;  // Spielerwechsel
                }
            }
        }
        updateBoard();
    }
	
    private boolean handleRemoveStone(int x, int y){
    	if(removeStoneStatus) {
            try {
            	this.getCurrentPlayer().entferneStein(spielbrett, x, y, getOtherPlayer());
                }
            catch (FalscheFarbeExeption e) {
                System.out.println("Ung�ltiger Zug: Das Feld ist unbesetzt. Versuche es erneut.");
                return false;  // Der Zug ist nicht erfolgreich, also muss der Spieler erneut setzen
            }
            catch (BesetztesFeldExeption f) {
                System.out.println("Ung�ltiger Zug: Der Stein hat die falsche Farbe. Versuche es erneut.");
                return false;  // Der Zug ist nicht erfolgreich, also muss der Spieler erneut setzen
            }
            removeStoneStatus = false;
            return true;  // Das Enternen war erfolgreich
    	}
    	return false;
    }

    public boolean isSetPhase() {
    	return gesetzteSteine < 18;
    }

    private boolean startSetPhase(int x, int y) {
        // Setzphase
        SpielPhasen.SetPhase setPhase = new SpielPhasen.SetPhase();
        Spieler currentPlayer = getCurrentPlayer();
        boolean zugErfolgreich = setPhase.handleAction(spielbrett, currentPlayer, -1, -1, x, y);
        if (zugErfolgreich) {
            gesetzteSteine++;
        }
        return zugErfolgreich;
    }

    private boolean startMovePhase(int fromX, int fromY, int toX, int toY) {
        // Zugphase
        SpielPhasen.MovePhase movePhase = new SpielPhasen.MovePhase();
        Spieler currentPlayer = getCurrentPlayer();
        return movePhase.handleAction(spielbrett, currentPlayer, fromX, fromY, toX, toY);
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
        // �berpr�fe, ob der Spieler noch g�ltige Z�ge hat
    	if(spieler.getSteine() == 3) {
    		// mit 3 Steinen kann er springen
    		return true;
    	}
    	Feld[][] felder = spielbrett.getFelder();
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                if (felder[i][j].getInhalt() == Feld.farbeFeld(spieler)) {
                    // �berpr�fe m�gliche Z�ge f�r diesen Stein
                    if (canMove(i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canMove(int x, int y) {
        // �berpr�fe, ob ein Stein an Position (x, y) bewegt werden kann
        Feld[][] felder = spielbrett.getFelder();
        // Beispiele f�r m�gliche Bewegungen pr�fen
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
    

    
}
