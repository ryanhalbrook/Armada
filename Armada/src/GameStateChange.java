import java.util.*;
import java.io.*;

public class GameStateChange implements Serializable {
    String message = "Armada";
    ArrayList<Object> outdatedClients = null;
    
    public GameStateChange(ArrayList<Object> clients, String message) {
        outdatedClients = clients;
        this.message = message;
    }
    
    public void wasRecievedByClient(Object client) {
        outdatedClients.remove(client);
    }
    public boolean isOutdatedClient(Object client) {
        if (outdatedClients.contains(client)) return true;
        return false;
    }
    public String getMessage() {
        return message;
    }
    public int numOutdatedClients() {
        return outdatedClients.size();
    }
}