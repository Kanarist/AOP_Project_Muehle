package model;

import model.exception.BesetztesFeldException;
import model.exception.FalscheFarbeExeption;
import model.exception.LogikException;
import model.exception.UnremovebleExeption;

public class MuehleLogik {
	
    private Spielbrett spielbrett;
    private final Spieler spielerWeiss;
	private final Spieler spielerSchwarz;
    private boolean isPlayerOneTurn;  // Bestimmt, welcher Spieler am Zug ist
    private int gesetzteSteine;
    
    private boolean removeStoneStatus;
    
	private Runnable updateListener;
	
	private boolean gameOver;
	
	private String debugMessage;
    
	public MuehleLogik() {
        // Initialisiere das Spielbrett und die Spieler
        this.spielbrett = Spielbrett.initialisiereBrett();
        this.spielerWeiss = new Spieler("Spieler Weiß", Spieler.Farbe.WEISS, 0);
        this.spielerSchwarz = new Spieler("Spieler Schwarz", Spieler.Farbe.SCHWARZ, 0);
        this.isPlayerOneTurn = true;  // Spieler 1 beginnt
        this.gesetzteSteine = 0;
    }

	public void neuesSpiel() {
		spielbrett = Spielbrett.initialisiereBrett();
		spielerWeiss.setSteine(0);
		spielerSchwarz.setSteine(0);
		gesetzteSteine = 0;
		isPlayerOneTurn = true;
		removeStoneStatus = false;
		gameOver = false;
		updateBoard();
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
    
    public boolean isGameOver() {
		return gameOver;
	}

	public void handlePlayAction(int x, int y) {
		debugMessage = null;
		
        if (gameOver) {
            return;
        }
        
        boolean setOrMoveIsfinished = false;

        try {
            if(removeStoneStatus) {
            	handleRemoveStone(x, y);
            	setOrMoveIsfinished = true;
            } 
            else if (isSetPhase()) {
            	doSetAction(x, y);
            	setOrMoveIsfinished = true;
            } else {
            	Position position = spielbrett.getSelPostion();
            	if(position != null) {
            		
            		// Wenn gleiche Position, dann Selektion wieder aufheben
            		if(position.getXAxis() == x && position.getYAxis() == y) {
            			spielbrett.setSelPostion(null);
            		} else {
                    	doMoveAction(position.getXAxis(), position.getYAxis(), x, y);
                		spielbrett.setSelPostion(null);
                		setOrMoveIsfinished = true;
            		}
            			
            	} else {
    				if(spielbrett.getFelder()[y][x].gehoertSpieler(getCurrentPlayer())) {
    	        		spielbrett.setSelPostion(new Position(x,y));
    					debugMessage = "Stein ausgewählt bei Position: (" + x + ", " + y + ")";
    				} else {
    					debugMessage = "Kein gültiger Stein zum Auswählen!(" + x + ", " + y + ")";
    				}
            	}
            }

            if(setOrMoveIsfinished) {
            	updateGameOver();
                if (gameOver) {
                	debugMessage = "Spiel vorbei! " + getWinner().getName() + " hat gewonnen!";
                } else {
                    removeStoneStatus = spielbrett.istTeilVonMuehle(x, y);
                    if(!removeStoneStatus) {
                    	isPlayerOneTurn = !isPlayerOneTurn;  // Spielerwechsel
                    }
                }
            }
            
        } catch (Exception exception) {
        	debugMessage = exception.getMessage();
		}
        updateBoard();
    }

	public String getDebugMessage() {
		return debugMessage;
	}

	
    private void handleRemoveStone(int x, int y) throws BesetztesFeldException, FalscheFarbeExeption, UnremovebleExeption {
    	if(removeStoneStatus) {
        	entferneStein(x, y);
            removeStoneStatus = false;
    	}
    }
    
    public void entferneStein(int x, int y) throws BesetztesFeldException, 
	FalscheFarbeExeption,UnremovebleExeption {
		if (spielbrett.istFeldFrei(x,y)) {
			throw new BesetztesFeldException();
		}
		if(!getFelder()[y][x].gehoertSpieler(getOtherPlayer())) {
			throw new FalscheFarbeExeption(); 
		}
		if(spielbrett.istTeilVonMuehle(x, y)
				&& spielbrett.freieSteineVorhanden(getFelder()[y][x].getInhalt())) {
			throw new UnremovebleExeption();
		}
		getFelder()[y][x].setInhalt(Feld.Inhalt.leer);
		getOtherPlayer().setSteine(getOtherPlayer().getSteine()-1);

	}
    

    public boolean isSetPhase() {
    	return gesetzteSteine < 18;
    }

    private void doSetAction(int x, int y) throws LogikException {
        // Setzphase
        SpielPhasen.SetPhase setPhase = new SpielPhasen.SetPhase();
        Spieler currentPlayer = getCurrentPlayer();
        setPhase.handleAction(spielbrett, currentPlayer, -1, -1, x, y);
        gesetzteSteine++;
    }

    private void doMoveAction(int fromX, int fromY, int toX, int toY) throws LogikException {
        // Zugphase
        SpielPhasen.MovePhase movePhase = new SpielPhasen.MovePhase();
        Spieler currentPlayer = getCurrentPlayer();
        movePhase.handleAction(spielbrett, currentPlayer, fromX, fromY, toX, toY);
    }



    public Spieler getCurrentPlayer() {
        return isPlayerOneTurn ? spielerWeiss : spielerSchwarz;
    }

    private void updateBoard() {
    	if(updateListener != null) {
    		updateListener.run();
    	}
    }

    private void updateGameOver() {
    	gameOver =  !isSetPhase() 
    		&& (spielerWeiss.getSteine() < 3 
				|| spielerSchwarz.getSteine() < 3 
				|| !hasValidMoves(spielerWeiss)
				|| !hasValidMoves(spielerSchwarz));
    }

    public Spieler getWinner() {
        if (spielerWeiss.getSteine() < 3 || !hasValidMoves(spielerWeiss)) {
            return spielerSchwarz;
        } else {
            return spielerWeiss;
        }
    }

    private boolean hasValidMoves(Spieler spieler) {
        // Überprüfe, ob der Spieler noch gültige Züge hat
    	if(spieler.getSteine() == 3) {
    		// mit 3 Steinen kann er springen
    		return true;
    	}
    	Feld[][] felder = spielbrett.getFelder();
        for (int y = 0; y < felder.length; y++) {
            for (int x = 0; x < felder[y].length; x++) {
                if (felder[y][x].getInhalt() == Feld.farbeFeld(spieler)) {
                    // Überprüfe mögliche Züge für diesen Stein
                    if (canMove(x, y)) {
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
    	if(this.getCurrentPlayer() == spielerWeiss) {
    		return spielerSchwarz;
    	}
    	return spielerWeiss;
    }
    
    public void debugChangePlayer() {
    	debugMessage = null;
    	isPlayerOneTurn = !isPlayerOneTurn;
    	updateBoard();
    }

    public void debugForceMovePhase() {
    	debugMessage = null;
    	if(gesetzteSteine < 18) {
    		gesetzteSteine = 18;
    		updateBoard();
    	}
    }
    
    public void debugPlaceStone(Feld.Inhalt inhalt, int x, int y) {
    	debugMessage = null;
    	switch(spielbrett.getFelder()[y][x].getInhalt()) {
    	case verboten:
    		return;
    	case leer:
    		if(inhalt == Feld.Inhalt.weiss) {
    			spielerWeiss.setSteine(spielerWeiss.getSteine() + 1);
    		} else if(inhalt == Feld.Inhalt.schwarz) {
    			spielerSchwarz.setSteine(spielerSchwarz.getSteine() + 1);
    		}
    		break;
    	case weiss:	
    		if(inhalt != Feld.Inhalt.weiss) {
    			spielerWeiss.setSteine(spielerWeiss.getSteine() - 1);
    		}
    		if(inhalt == Feld.Inhalt.schwarz) {
    			spielerSchwarz.setSteine(spielerSchwarz.getSteine() + 1);
    		}
    		break;
    	case schwarz:	
    		if(inhalt != Feld.Inhalt.schwarz) {
    			spielerSchwarz.setSteine(spielerSchwarz.getSteine() - 1);
    		}
    		if(inhalt == Feld.Inhalt.weiss) {
    			spielerWeiss.setSteine(spielerWeiss.getSteine() + 1);
    		}
    	}
    	spielbrett.getFelder()[y][x].setInhalt(inhalt);
    	updateBoard();
    }
}
