
package soccer.event;

/**
 *
 * @author mont_
 */
public class Goal extends GameEvent{

    public Goal() {
    }

    @Override
    public String toString() {
        return "Gool! ";
    }
    
    
    @Override
    public GameEvent[] getNextEvents() {
       GameEvent theEvent[] ={new Kickoff()};
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
    
    @Override
    public void setBallPos(int ballPos){
        super.ballPos = 100;
    }
    
}
