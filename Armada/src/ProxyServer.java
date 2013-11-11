import java.net.*;
import java.io.*;
import java.util.*;
public class ProxyServer extends GameServer {
    static final int PORT = 1730;
    static final String SYSTEM_ID = "ARMADA-AF3FD2";
    static final String QUIT_STRING = "QUIT_GAME";
    Socket connection = null;
    InputStream istream = null;
    OutputStream ostream = null;
    ObjectOutputStream oostream = null;
    ObjectInputStream oistream = null;
    ChangeListener a = null;
    int x = 250;
    
    ArrayList<Object> clients = new ArrayList<Object>();
    ArrayList<GameStateChange> changes = new ArrayList<GameStateChange>();
    
    public void setChangeListener(ChangeListener a) {
        this.a = a;
    }
    
    public void disconnectNetwork() {
        try {
            oostream.close();
            oistream.close();
            ostream.close();
            istream.close();
            
        } catch (IOException e) {
            System.out.println("Failed to close network");
            e.printStackTrace();
        }
        System.out.println("Client: Shutdown successful");
    }
    
    private void configureNetwork(String ip, int port) {
    GameStateChange c = null;
        try {
            connection = new Socket(ip, port);
            istream = connection.getInputStream();
            ostream = connection.getOutputStream();
            oostream = new ObjectOutputStream(ostream);
            oistream = new ObjectInputStream(istream);
            c = (GameStateChange)oistream.readObject(); 
            System.out.println("c info: " + c.toString());
            if (c == null) {
                System.out.println("c is null!!!!");
            }
            if (c.getMessage() == null) {
                System.out.println("message is null!!!");
            }
            System.out.println("Debugging info: " + c.getMessage());
            System.out.println("Connection phase 1 Success");
            
            if (c == null || !(c.getMessage().equals(SYSTEM_ID))) {
                System.out.println("Client: ID Mismatch");
                disconnectNetwork();
                       
            } else {
                System.out.println("ID match");
                System.out.println(c.getMessage());
                c = new GameStateChange(null, SYSTEM_ID);
                oostream.writeObject(c);
            }
            
        } catch (Exception e) {
            System.out.println("Failed to configure network");
            e.printStackTrace();
        }
        System.out.println("Client Success!");
        Thread t = new Thread(new Loop());
        t.start();
    }
    
    public ProxyServer(String ip) {
        configureNetwork(ip, PORT);
    }
    
    public void registerClient(Object client) {
        
    }
    
    private synchronized ArrayList<GameStateChange> getChanges() {
        return changes;
    }
    public synchronized void commitChange(Object client, GameStateChange change) {
        System.out.println("Adding change");
        changes.add(change);
        
    }
    public synchronized ArrayList<GameStateChange> getChanges(Object client) {
        return changes;
    }
    
    public synchronized int getSize() {
        return changes.size();
    }
    
    public synchronized GameStateChange getGameStateChange(int index) {
        if (index < changes.size()) {
            return changes.get(index);
        }
        return null;
    }
    
    public synchronized void addGameStateChange(GameStateChange gsc) {
        System.out.println("Adding Change: " + gsc.getMessage());
        changes.add(gsc); 
    }
    
    public synchronized void removeGameStateChange(int index) {
        if (index < changes.size()) {
            changes.remove(index);
        }
    }
    
    private class Loop implements Runnable {
        public void run() {
            GameStateChange c = null;
        
            boolean quit = false;
        
            while (!quit) {
        
                try {
            
                    Thread.sleep(100);
                    x--;
                    if (x % 50 == 0) System.out.println("Client Side Loop Running");
                    //if (x == 0) break;
                    for (GameStateChange change : getChanges()) {
                        System.out.println("Client: Sending a change");
                        oostream.writeObject(change);
                    
                        if (change.getMessage().equals(QUIT_STRING)) break;
                        changes.remove(change);
                    }
                    if (oistream.available() > 0) c = (GameStateChange)oistream.readObject();
                    if (c!= null) addGameStateChange(c);
                    if (a != null) a.changeOccurred();
                    if (c == null) continue;
                
                    if (c.getMessage().equals(QUIT_STRING)) {
                        System.out.println("Exiting server loop");
                        break;
                    } else {
                        System.out.println("Message: " + c.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();   
                    //quit = true;
                }
            }
            System.out.println("About to disconnect");
            disconnectNetwork();
        }
    }
}
