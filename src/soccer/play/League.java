
package soccer.play;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import soccer.event.GameEvent;
import soccer.event.Goal;
import soccer.util.PlayerDatabase;
import soccer.util.PlayerDatabaseException;
import soccer.util.Settings;

/**
 *
 * @author mont_
 */
public class League {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        League theLeague =  new League();
        String teamNanes = "Santa Fe,Nacional,Junior,Millonarios,America,Cali";
        int teamSize = 5;
        
        try{
            Team[] theTeams = theLeague.createTeams(teamNanes, teamSize);
            Game[] theGames =theLeague.createGames(theTeams);
            
            System.out.println(theLeague.getLeagueAnnouncement(theGames));
            for(Game currGame : theGames){
                currGame.playGame();
                System.out.println(currGame.getDescription(true));
            }
            
            theLeague.setPlayerStats(theGames);
            theLeague.showBestPlayersByLeague(theTeams);
            
            theLeague.setTeamsStats(theTeams, theGames);
            theLeague.showBestTeam(theTeams);
            
            
            
            IDisplayDataItem[][] dataGrid =theLeague.getLeagueDataGrid(theGames, theTeams);
            theLeague.outputTextLeagueGrid(dataGrid);
            
        }catch(PlayerDatabaseException e){
            e.printStackTrace(System.err);
        }
                
    }
    
    public Team[] createTeams(String teamNames, int teamSize) throws PlayerDatabaseException{
          PlayerDatabase playerDB = new PlayerDatabase();
          
          StringTokenizer teamNameTokens = new StringTokenizer(teamNames,",");
          Team[] theTeams = new Team[teamNameTokens.countTokens()];
          for (int i = 0; i < theTeams.length; i++) {
            theTeams[i]= new Team(teamNameTokens.nextToken(), playerDB.getTeam(teamSize));
        }
         return theTeams;
    }
    
    public Game[] createGames (Team[] theTeams){
        int daysBetweenGames = 0;
        
        ArrayList theGames = new ArrayList();
        //Millonarios,Santa Fe,Nacional,Junior,America,Cali
        //Millonarios,Santa Fe,Nacional,Junior,America,Cali
        for (Team homeTeam : theTeams ){
            for (Team awayTeam : theTeams){
                if(homeTeam != awayTeam){
                   daysBetweenGames += Settings.DAYS_BETWEEN_GAMES;
                   theGames.add(new Game(homeTeam,awayTeam,LocalDateTime.now().plusDays(daysBetweenGames)));
                }
            }
        }
        
        return ((Game[])theGames.toArray(new Game[1]));
    }
    
    public void setTeamsStats (Team[] theTeams,Game[]theGames){
        for(Team currTeam : theTeams){
            currTeam.setGoalsTotal(0);
            currTeam.setPointsTotal(0);
            
            for(Player currPlayer : currTeam.getPlayerArray()){
                currPlayer.setGoalsScored(0);
            }
        }
        
        for (Game currGame : theGames){
            GameResult theResult = currGame.getGameResult();
            
            if(theResult.isIsDrawn()){
                currGame.getHomeTeam().incPointTotal(Settings.DRAWN_GAME_POINTS);
                theResult.getAWAY_TEAM().incPointTotal(Settings.DRAWN_GAME_POINTS);
            }
            else{
                theResult.getWinner().incPointTotal(Settings.WINNER_GAME_POINTS);
            }
            
            theResult.getHOME_TEAM().incGoalsTotal(theResult.getHomeTeamGoals());
            theResult.getAWAY_TEAM().incGoalsTotal(theResult.getAwayTeamGoals());
        }
    }
    
    public void showBestTeam (Team[] theTeams){
        
        Arrays.sort(theTeams);
        Team currBestTeam = theTeams[0];
        System.out.println("\nResultados de la Liga");
        //Resultados de la Liga
        // equipo1 : 12 : 6
        // equipo2 : 10 : 8
        
        //El Ganador de la Liga es: equipo1
        for(Team currTeam: theTeams){
            System.out.println(currTeam.getTeamName()+" : "+currTeam.getPointsTotal()+" : "
            +currTeam.getGoalsTotal());
        }
        System.out.println("\nEl Ganador de la Liga es: "+currBestTeam.getTeamName());
    }
    
    public String getLeagueAnnouncement(Game[] theGames){
        // La liga está programada para durar 4 meses, y 20 dias
        Period thePeriod = Period.between(theGames[0].getTheDateTime().toLocalDate(),
                theGames[theGames.length - 1].getTheDateTime().toLocalDate());
        return "La liga está programada para durar "+
                thePeriod.getMonths()+" meses, y "+
                thePeriod.getDays()+" dias\n";
    }
    
    public void setPlayerStats (Game[] theGames){
        for(Game currGame : theGames){
            for (GameEvent currEvent : currGame.getEvents()){
                if(currEvent instanceof Goal){
                    currEvent.getThePlayer().incGoalsScored();
                }
            }
        }
    }
    
    public void showBestPlayersByLeague(Team[] theTeams){
        ArrayList <Player> thePlayers = new ArrayList();
        
        for(Team currTeam :theTeams){
            thePlayers.addAll(Arrays.asList(currTeam.getPlayerArray()));
        }
        
        Collections.sort(thePlayers, (p1,p2) -> Double.valueOf(p2.getGoalsScored()).compareTo(Double.valueOf(p1.getGoalsScored())));
        
        System.out.println("\n\nEl Goleador de la Liga");
        //El Goleador de la Liga
        //JUgador 1 : 12
        //JUgador 2 : 11
        
        for(Player currPlayer: thePlayers){
            System.out.println(currPlayer.getPlayerName()+" : " +currPlayer.getGoalsScored() );
        }
    }
    
    public void showBestPlayerByTeam(Team[] theTeams){
        
        for(Team currTeam : theTeams){
            Arrays.sort(currTeam.getPlayerArray(),(p1,p2) -> Double.valueOf(p2.getGoalsScored()).compareTo(Double.valueOf(p1.getGoalsScored())));
            
            System.out.println("\n\nEl mejor Jugador de "+currTeam.getTeamName());
            for(Player currPlayer : currTeam.getPlayerArray()){
                System.out.println(currPlayer.getPlayerName()+" : "+ currPlayer.getGoalsScored());
            }
        }
    }
    
    public IDisplayDataItem[][] getLeagueDataGrid (Game[] theGames, Team[] theTeams){
        
        int numTeams = theTeams.length;
        
        
        IDisplayDataItem[][] theGrid =new IDisplayDataItem[numTeams + 1][numTeams +3] ;
        
        int colNum =0;
        int rowNum = 0;
        
        theGrid[rowNum][colNum] = new DisplayString("");
        for (int i = 0; i < theTeams.length; i++) {
            theTeams[i].setId(i);
            theGrid[rowNum][colNum+1]=theTeams[i];
            colNum++;
            }
          /*  --------------------------------------------------
                    equipo1 equipo2 equipo3 equipo4 Puntos Goles
            equipo1  X        2-1      3-0   1-1       12   7
            equipo2 2-1       x   
            equipo3  
                */   
          
          theGrid[rowNum][colNum+1] = new DisplayString("Puntos");
          theGrid[rowNum][colNum+2] = new DisplayString("Goles");
        
          for (int i = 0; i < theTeams.length; i++) {
            rowNum = i+1;
            
            colNum = 0;
            Team currHomeTeam = theTeams[i];
            theGrid[rowNum][colNum]= currHomeTeam;
            
            for(Team currAwayTeam:theTeams ){
                colNum++;
                if(currHomeTeam != currAwayTeam){
                    for (Game theGame : theGames){
                        if(theGame.getHomeTeam() == currHomeTeam && theGame.getAwayTeam()==currAwayTeam ){
                            theGrid[rowNum][colNum] = theGame;
                            break;        
                    }
                }
            }else{
                    theGrid[rowNum][colNum]= new DisplayString(" X ");
        }
    }
            theGrid[rowNum][colNum+1]= new DisplayString(new Integer(currHomeTeam.getPointsTotal()).toString());
            theGrid[rowNum][colNum+2]= new DisplayString(new Integer(currHomeTeam.getGoalsTotal()).toString());

                }
          return theGrid;
            }
        
    public void outputTextLeagueGrid(IDisplayDataItem[][] dataGrid){
        int[] stringLength = new int[dataGrid[0].length];
        int totalLength = 0;
        
        for (int i = 0; i < dataGrid[0].length; i++) {
            int currLongest = 0;
            for(IDisplayDataItem[] dataGrid1 : dataGrid){
                int currLength = dataGrid1[i].getDisplayDetail().length();
                if(currLength > currLongest) currLongest = currLength;
            }
            stringLength[i] =currLongest;
            totalLength += currLongest;
        }
        
        int numCols = dataGrid[0].length;
        int numExtraCharsPerColum = 3;
        
        String separatorLine = new String(new char[totalLength + (numCols*numExtraCharsPerColum)+1]).replace("\0", "-");
        System.out.println(separatorLine);
        
        for(IDisplayDataItem[] theRow : dataGrid){
            for (int j = 0; j < theRow.length; j++) {
                int extraChars = stringLength[j] - theRow[j].getDisplayDetail().length();
                System.out.print("| "+ theRow[j].getDisplayDetail() + new String(new char [extraChars]).replace("\0"," ")+" ");
            }
            System.out.print("|");
            System.out.println();
            System.out.println(separatorLine);
        }
    }
    
    public void simpleDisplay (IDisplayDataItem[][] dataGrid){
        for(IDisplayDataItem[] theRow : dataGrid){
            for(IDisplayDataItem theItem : theRow){
                System.out.println(theItem.getDisplayDetail()+" : ");
            }
            System.out.println();
        }
    }
}
