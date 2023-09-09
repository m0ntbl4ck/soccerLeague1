
package soccer.play;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import soccer.event.GameEvent;
import java.util.ArrayList;
import soccer.event.Kickoff;
import soccer.util.Settings;

/**
 *
 * @author mont_
 */
public class Game {
    
    private Team homeTeam;
    private Team awayTeam;
    private GameEvent[] events;
    private LocalDateTime theDateTime;
    
    private boolean detailAvaileble = false;
    private int id = 0;
    private String detailType = "Game";

    public Game(Team homeTeam, Team awayTeam, LocalDateTime theDateTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.theDateTime = theDateTime;
    }

    public void playGame(){
        ArrayList<GameEvent> eventList = new ArrayList();
        Team currTeam;
        Player currPlayer;
        GameEvent currEvent = new Kickoff();
        currEvent.setBallPos(50);
        
        currEvent.setTheTeam(Math.random()>0.5 ?homeTeam : awayTeam);
        currEvent.setThePlayer(currEvent.getTheTeam().getPlayerArray()[(int)(Math.random()*this.homeTeam.getPlayerArray().length)]);
        currEvent.setTheTime(0);
        eventList.add(currEvent);
        
        
        for (int i = 1; i <= Settings.GAME_LENGTH; i++) {
            
            if(Math.random() > Settings.GAME_EVENT_FREQUENCY){
                currTeam = currEvent.getTheTeam();
                currPlayer = currEvent.getThePlayer();
                
                int currBallPos = currEvent.getBallPos();
                
                
                currEvent = currTeam.getNextPlayAttempt(currEvent);
                
                currEvent.setBallPos(currBallPos);
                
                if(currEvent.changeTeam()){
                   // currTeam = g
                }
            }
            
        }
    }
    
    public void getDescription(boolean showEvents){
        
        StringBuilder returnString = new StringBuilder();
        
        returnString.append(this.getHomeTeam().getTeamName() +" vs. "+
                this.getAwayTeam().getTeamName()+" ("+
                this.getTheDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE)+")");
        returnString.append("\n");
        // equeipo1 vs. equipo2 (12/09/2023)
        //equpi 1 Gana!
        
        GameResult theResult = getGameResult();
        
        if(theResult.isIsDrawn()){
            returnString.append(("Empate!"));     
        } else{
            returnString.append(theResult.getWinner().getTeamName());
            returnString.append(" Gana!");
        }returnString.append(" ("+theResult.getHomeTeamGoals()+" - "+ theResult.getAwayTeamGoals()+") \n" );
        
        
        
        
        
        
        
    }
    
    public GameResult getGameResult(){
        return new GameResult(this);
    }
    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public GameEvent[] getEvents() {
        return events;
    }

    public void setEvents(GameEvent[] events) {
        this.events = events;
    }

    public LocalDateTime getTheDateTime() {
        return theDateTime;
    }

    public void setTheDateTime(LocalDateTime theDateTime) {
        this.theDateTime = theDateTime;
    }
    
    
    
}
