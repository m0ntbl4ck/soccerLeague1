
package soccer.event;

/**
 *
 * @author mont_
 */
public class ReceivePass extends GameEvent{

    public ReceivePass() {
        super();
    }

    @Override
    public String toString() {
        return "Pase Recibido ";
    }

    @Override
    public GameEvent[] getNextEvents() {
       GameEvent theEvent[]={new Dribble(), new GainPossession(), new Shoot(), new Pass()};
       return theEvent;
    }

    @Override
    public boolean changePlayer() {
       return true;
    }

    @Override
    public boolean changeTeam() {
        return false;
    }
    
    
    
    
}
