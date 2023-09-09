
package soccer.event;

/**
 *
 * @author mont_
 */
public class Kickout extends GameEvent{

    public Kickout() {
    }

    @Override
    public String toString() {
        return "Salvados. Saque de puerta ";
    }
    
    @Override
    public GameEvent[] getNextEvents() {
        GameEvent theEvent[]= {new GainPossession(),new ReceivePass()};
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
           super.ballPos = 95;
       }
    
}
