import java.util.*;
public abstract class GameServer {
    public void registerClient(Object client){};
    public void commitChange(Object client, GameStateChange change){};
    public ArrayList<GameStateChange> getChanges(Object client){ return null;};
    public void setChangeListener(ChangeListener cl) {};
    
    public synchronized int getSize() {
        return 0;
    }
    
    public synchronized GameStateChange getGameStateChange(int index) {
        return null;
    }
    
    public synchronized void addGameStateChange(GameStateChange gsc) {
    }
    
    public synchronized void removeGameStateChange(int index) {
    }
}