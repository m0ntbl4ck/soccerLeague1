
package soccer.event;

/**
 *
 * @author mont_
 */
public class Shoot extends GameEvent{

    public Shoot() {
    }

    @Override
    public String toString() {
        return "Disparo a porteria ";
    }

    @Override
    public GameEvent[] getNextEvents() {
       GameEvent theEvent[] = {new Goal(), new Kickout()};
       return theEvent;
    }

    @Override
    public boolean changePlayer() {
        return false;
    }
    
    @Override
    public boolean changeTeam() {
       return false;
    }
    
    
}
