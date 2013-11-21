package src; import src.view.*;
import java.util.*;
import java.io.*;

public class GameStateChange implements Serializable {
    String message = "Armada";
    ArrayList<Object> outdatedClients = null;
    
    int x1, y1;
    int x2, y2;
    int x3;
    
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