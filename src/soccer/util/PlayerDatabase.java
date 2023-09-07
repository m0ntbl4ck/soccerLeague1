
package soccer.util;

import java.util.*;
import soccer.play.Player;



/**
 * 
 * @author mont_
 */
public class PlayerDatabase {
    
    private ArrayList <Player> players;
    
    
    public PlayerDatabase(){
        StringTokenizer soccerPlayersTokens = new StringTokenizer (soccerPlayers,",");
        players = new ArrayList();
        
        while(soccerPlayersTokens.hasMoreTokens()){
            players.add(new Player(soccerPlayersTokens.nextToken()));
        }
    }
    
    public Player[] getTeam (int numberOfPlayers)throws PlayerDatabaseException{
        
        Player[] teamPlayers = new Player[numberOfPlayers];
        
        for (int i = 0; i < numberOfPlayers; i++) {
            int playerIndex =(int)(Math.random()*players.size());
  
            try {
                teamPlayers[i] = players.get(playerIndex);
                players.remove(playerIndex);
            } catch (IndexOutOfBoundsException e) {
                throw new PlayerDatabaseException("Se supero el limite de jugadores de la base de datos");
            }
        }
        return teamPlayers;
    }
    
    String soccerPlayers =
                    "Carlos Valderrama," +
                    "James Rodriguez," +
                    "Radamel Falcao," +
                    "Ivan Cordoba," +
                    "Juan Cuadrado," +
                    "Freddy Rincon," +
                    "Hugo Rodallega," +
                    "David Ospina," +
                    "Teofilo Gutierrez," +
                    "Carlos Bacca," +
                    "Juan Pablo Angel," +
                    "Fredy Guarin," +
                    "Juan Fernando Quintero," +
                    "Abel Aguilar," +
                    "Pablo Armero," +
                    "Victor Ibarbo," +
                    "Camilo Zuniga," +
                    "Luis Muriel," +
                    "Duvan Zapata," +
                    "Jeison Murillo," +
                    "Jeison Medina," +
                    "Roger Martinez," +
                    "Yerry Mina," +
                    "Davinson Sanchez," +
                    "Santiago Arias," +
                    "Frank Fabra," +
                    "Alvaro Montero," +
                    "Wilmar Barrios," +
                    "Edwin Cardona," +
                    "Daniel Munoz," +
                    "Alfredo Morelos," +
                    "Cucho Hernandez";
}
