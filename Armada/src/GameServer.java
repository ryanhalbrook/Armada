import java.util.*;
public abstract class GameServer {
    public void registerClient(Object client){};
    public void commitChange(Object client, GameStateChange change){};
    public ArrayList<GameStateChange> getChanges(Object client){ return null;};
    public void setChangeListener(ChangeListener cl) {};
}