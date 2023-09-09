
package soccer.event;

/**
 *
 * @author mont_
 */
public class Dribble extends GameEvent {

    public Dribble() {
        super();
    }
    
    public String toString(){
        return "Regate ";
    }
    /**
     * 
     * @return un array de posibles eventos que suran despues de este 
     */
    @Override
    public GameEvent[] getNextEvents() {
        GameEvent theEvent[] = {new GainPossession(), new Shoot(),new Pass()};
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
