
package soccer.event;

/**
 *
 * @author mont_
 */
public class Pass extends GameEvent{

    public Pass() {
        super();
    }

    @Override
    public String toString() {
        return "Pase Ejecutado ";
    }
    
    
    @Override
    public GameEvent[] getNextEvents() {
       GameEvent theEvent[] = {new ReceivePass(),new GainPossession()};
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
