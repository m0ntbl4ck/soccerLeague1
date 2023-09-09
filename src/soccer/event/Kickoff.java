
package soccer.event;

/**
 *
 * @author mont_
 */
public class Kickoff extends GameEvent{

    public Kickoff() {
    }

    @Override
    public String toString() {
        return "Inicio de juego ";
    }
    
    
    @Override
    public GameEvent[] getNextEvents() {
        GameEvent theEvent[] = {new ReceivePass(), new GainPossession()};
        return theEvent;
    }

    @Override
    public boolean changePlayer() {
        return true;
    }

    @Override
    public boolean changeTeam() {
       return true;
    }
    
    @Override
    public void setBallPos(int ballPos){
        super.ballPos = 50;
    }
}
