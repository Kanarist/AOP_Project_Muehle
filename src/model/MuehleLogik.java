package model;

import model.exception.BesetztesFeldException;
import model.exception.FalscheFarbeException;
import model.exception.LogikException;
import model.exception.UnremovebleExecption;

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
    
	//initialisiere das Spielbrett und die Spieler
	public MuehleLogik() {
        this.spielbrett = Spielbrett.initialisiereBrett();
        this.spielerWeiss = new Spieler("Spieler Weiß", Spieler.Farbe.WEISS, 0);
        this.spielerSchwarz = new Spieler("Spieler Schwarz", Spieler.Farbe.SCHWARZ, 0);
        this.isPlayerOneTurn = true;  // Spieler 1 beginnt
        this.gesetzteSteine = 0;
    }

	//resetted Spiel
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
	
	//Registrierung der GUI
	//Der updateListener wird aufgerufen, wenn sich das Model geaendert hat.
	//Damit wird die Oberflaeche bei Aenderungen automatisch aktualisiert.
    public void setUpdateListener(Runnable updateListener) {
		this.updateListener = updateListener;
	}
    
    //muss Stein entfernt werden?
	public boolean isRemoveStoneStatus() {
		return removeStoneStatus;
	}

	public Feld[][] getFelder() {
		return spielbrett.getFelder();
	}
	
	//ist Stein ausgewaehlt? b
	public boolean isSelPosition(int x, int y) {
		Position selPosition = spielbrett.getSelPostion();
		return selPosition != null && selPosition.getXAxis() == x && selPosition.getYAxis() == y;
	}
    
	//ist Spiel vorbei?
    public boolean isGameOver() {
		return gameOver;
	}

    //verarbeitet Spieleraktionen, wird von GUI gerufen 
	public void handlePlayAction(int x, int y) {
		debugMessage = null;
        if (gameOver) {
            return; //keine Reaktion wenn Spiel vorbei
        }
        
        boolean setOrMoveIsfinished = false;

        try {
        	//Stein nach Muehle entfernen
            if(removeStoneStatus) {
            	handleRemoveStone(x, y);
            	setOrMoveIsfinished = true;
            }
            //Stein in Setzphase setzen
            else if (isSetPhase()) {
            	doSetAction(x, y);
            	setOrMoveIsfinished = true;
            } else {
            	Position position = spielbrett.getSelPostion();
            	if(position != null) {
            		
            		// wenn gleiche Position, dann Selektion wieder aufheben
            		if(position.getXAxis() == x && position.getYAxis() == y) {
            			spielbrett.setSelPostion(null);
            		} else { //selektierten Stein bewegen 
                    	doMoveAction(position.getXAxis(), position.getYAxis(), x, y);
                		spielbrett.setSelPostion(null);
                		setOrMoveIsfinished = true;
            		}
            			
            	} else { //wenn Stein Spieler gehört, Stein zum ziehen selektieren
    				if(spielbrett.getFelder()[y][x].gehoertSpieler(getCurrentPlayer())) {
    	        		spielbrett.setSelPostion(new Position(x,y));
    					debugMessage = "Stein ausgewählt bei Position: (" + x + ", " + y + ")";
    				} else {
    					debugMessage = "Kein gültiger Stein zum Auswählen!(" + x + ", " + y + ")";
    				}
            	}
            }

            if(setOrMoveIsfinished) { //auf Spielende oder Muehle pruefen
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

	//gibt Debugmessage wieder
	public String getDebugMessage() {
		return debugMessage;
	}

    //entfernt Stein nach Muehle
	private void handleRemoveStone(int x, int y) throws BesetztesFeldException, FalscheFarbeException, UnremovebleExecption {
    	if(removeStoneStatus) {
        	entferneStein(x, y);
            removeStoneStatus = false;
    	}
    }
    
    //entfernt Stein bei passender Feldfarbe vom Gegner
    public void entferneStein(int x, int y) throws BesetztesFeldException, 
	FalscheFarbeException,UnremovebleExecption {
		if (spielbrett.istFeldFrei(x,y)) {
			throw new BesetztesFeldException();
		}
		if(!getFelder()[y][x].gehoertSpieler(getOtherPlayer())) {
			throw new FalscheFarbeException(); 
		}
		//darf nicht entfernt werden wenn in Muehle ausser wenn alle gegnerischen Steine Teil von Muehle
		if(spielbrett.istTeilVonMuehle(x, y)
				&& spielbrett.freieSteineVorhanden(getFelder()[y][x].getInhalt())) {
			throw new UnremovebleExecption();
		}
		getFelder()[y][x].setInhalt(Feld.Inhalt.leer);
		getOtherPlayer().setSteine(getOtherPlayer().getSteine()-1);

	}
    
    //ueberprueft ob genug Steine in Setzphase gesetzt wurden
    public boolean isSetPhase() {
    	return gesetzteSteine < 18;
    }

    //Platzierphase von ziehendem Spieler ausgeführt
    private void doSetAction(int x, int y) throws LogikException {
        // Setzphase
        SpielPhasen.SetPhase setPhase = new SpielPhasen.SetPhase();
        Spieler currentPlayer = getCurrentPlayer();
        setPhase.handleAction(spielbrett, currentPlayer, -1, -1, x, y);
        gesetzteSteine++;
    }

    //Zugphase von ziehendem Spieler ausgeführt
    private void doMoveAction(int fromX, int fromY, int toX, int toY) throws LogikException {
        // Zugphase
        SpielPhasen.MovePhase movePhase = new SpielPhasen.MovePhase();
        Spieler currentPlayer = getCurrentPlayer();
        movePhase.handleAction(spielbrett, currentPlayer, fromX, fromY, toX, toY);
    }



    //gibt Spieler der am Zug ist wieder
    public Spieler getCurrentPlayer() {
        return isPlayerOneTurn ? spielerWeiss : spielerSchwarz;
    }

    //GUI wird ueber Modelaenderung informiert
    private void updateBoard() {
    	if(updateListener != null) {
    		updateListener.run();
    	}
    }

    // ueberprueft, ob Siegesbedingungen erfüllt
    private void updateGameOver() {
    	gameOver =  !isSetPhase() 
    		&& (spielerWeiss.getSteine() < 3 
				|| spielerSchwarz.getSteine() < 3 
				|| !hasValidMoves(spielerWeiss)
				|| !hasValidMoves(spielerSchwarz));
    }

    // gibt Gewinner zurueck
    public Spieler getWinner() {
        if (spielerWeiss.getSteine() < 3 || !hasValidMoves(spielerWeiss)) {
            return spielerSchwarz;
        } else {
            return spielerWeiss;
        }
    }

    // ueberprueft, ob Spieler gueltige Zuege hat
    private boolean hasValidMoves(Spieler spieler) {
    	if(spieler.getSteine() == 3) {
    		// mit 3 Steinen kann er springen
    		return true;
    	}
    	Feld[][] felder = spielbrett.getFelder();
        for (int y = 0; y < felder.length; y++) {
            for (int x = 0; x < felder[y].length; x++) {
                if (felder[y][x].getInhalt() == Feld.farbeFeld(spieler)) {
                    // ueberprüft mögliche Züge für diesen Stein
                    if (canMove(x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // ueberprueft, ob Stein bewegt werden kann
    private boolean canMove(int x, int y) {
        Feld[][] felder = spielbrett.getFelder();
        // ueberprueft in alle Richtungen ob leeres Feld vorhanden
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
    
    //gibt Spieler wieder der nicht am Zug ist
    public Spieler getOtherPlayer() {
    	if(this.getCurrentPlayer() == spielerWeiss) {
    		return spielerSchwarz;
    	}
    	return spielerWeiss;
    }
    
    //Debug: wechselt Spieler der am Zug ist
    public void debugChangePlayer() {
    	debugMessage = null;
    	isPlayerOneTurn = !isPlayerOneTurn;
    	updateBoard();
    }

    //Debug: überspringt Setzphase
    public void debugForceMovePhase() {
    	debugMessage = null;
    	if(gesetzteSteine < 18) {
    		gesetzteSteine = 18;
    		updateBoard();
    	}
    }
    
    //Debug: aendert Feldfarbe und passt Steinanzahl der Spieler entsprechend an
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
