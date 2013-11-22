package game.server; 
import game.ChangeListener;
import game.GameStateChange;

import java.util.*;
public interface GameServer {
    public void registerClient(Object client);
    public void commitChange(Object client, GameStateChange change);
    public ArrayList<GameStateChange> getChanges(Object client);
    public void setChangeListener(ChangeListener cl);
    public void disconnectNetwork();
    public int getSize();
    public GameStateChange getGameStateChange(int index);
    public void addGameStateChange(GameStateChange gsc);
    public void removeGameStateChange(int index);
}