
package soccer.play;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import soccer.event.GameEvent;
import java.util.ArrayList;
import java.util.Arrays;
import soccer.event.Kickoff;
import soccer.util.Settings;

/**
 *
 * @author mont_
 */
public class Game implements IDisplayDataItem {
    
    private Team homeTeam;
    private Team awayTeam;
    private GameEvent[] events;
    private LocalDateTime theDateTime;
    
    private boolean detailAvailable = false;
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
                   currTeam = getOtherTeam(currTeam);
                   currEvent.reverseBallPos();
                }
                currEvent.setTheTeam(currTeam);
                
                ArrayList <Player> currPlayerList = new ArrayList(Arrays.asList(currEvent.getTheTeam().getPlayerArray()));
                currPlayerList.remove(currPlayer);
                currEvent.setThePlayer(
                currEvent.changePlayer()?
                    currPlayerList.get((int)(Math.random()*currPlayerList.size())):
                    currPlayer
                );
                
                currEvent.setTheTime(i);
                eventList.add(currEvent);
            }
            
            this.events = new GameEvent[eventList.size()];
            eventList.toArray(events);
        }
    }
    
    public String getDescription(boolean showEvents){
        
        StringBuilder returnString = new StringBuilder();
        
        returnString.append(this.getHomeTeam().getTeamName() +" vs. "+
                this.getAwayTeam().getTeamName()+" ("+
                this.getTheDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE)+")");
        returnString.append("\n");
        // equeipo1 vs. equipo2 (12/09/2023)
        //Empate
        //equpi 1 Gana!  2 - 1
       
        GameResult theResult = getGameResult();
        
        if(theResult.isIsDrawn()){
            returnString.append(("Empate!"));     
        } else{
            returnString.append(theResult.getWinner().getTeamName());
            returnString.append(" Gana!");
        }returnString.append(" ("+theResult.getHomeTeamGoals()+" - "+ theResult.getAwayTeamGoals()+") \n" );
        
        
        if(showEvents){
            // 100 : Gool! en los 34 mins por James Rodriguez de Equipo1
            
            for (GameEvent currEvent: this.getEvents()){
                returnString.append(currEvent.getBallPos() + " : "+ currEvent +"en los"+
                currEvent.getTheTime() + " mins por "+
                currEvent.getThePlayer().getPlayerName() +
                " de "+ currEvent.getTheTeam().getTeamName()+
                        "\n");
            }
        }
        return returnString.toString();
    }
    
    public String getDescrption(){
        return getDescription (false);
    }
    
    public String getScore(){
        // 2 - 1
        String theScore;
        GameResult theResult = getGameResult();
        theScore = theResult.getHomeTeamGoals() + " - "+ theResult.getAwayTeamGoals();
        return theScore;
    }
    
    
    public Team getOtherTeam(Team currTeam){
        if(currTeam == homeTeam){
            currTeam = awayTeam;
        }else currTeam = homeTeam;
        return currTeam;
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

    public boolean isDetailAvaileble() {
        return detailAvailable;
    }

    public void setDetailAvaileble(boolean detailAvaileble) {
        this.detailAvailable = detailAvaileble;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public void setEvents(GameEvent[] events) {
        this.events = events;
    }
    public LocalDateTime getLocalDateTime(){
        return getTheDateTime();
    }
    public void setLocalDateTime(LocalDateTime theDateTime){
        this.setTheDateTime(theDateTime);
    }
    public LocalDateTime getTheDateTime() {
        return theDateTime;
    }

    public void setTheDateTime(LocalDateTime theDateTime) {
        this.theDateTime = theDateTime;
    }

    
    
    
    @Override
    public boolean isDetailAvailable() {
        return detailAvailable;
    }

    @Override
    public String getDisplayDetail() {
        return getScore();
    }

    @Override
    public int getID() {
       return id;
    }

}
