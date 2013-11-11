import java.util.*;
import java.net.*;
import java.io.*;

public class HostServer extends GameServer {
    static final int PORT = 1730;
    static final String SYSTEM_ID = "ARMADA-AF3FD2";
    static final String QUIT_STRING = "QUIT_GAME";
    ArrayList<Object> clients = new ArrayList<Object>();
    ArrayList<GameStateChange> changes = new ArrayList<GameStateChange>();
    
    ServerSocket clientListener;
    Socket connection = null;
    InputStream istream = null;
    OutputStream ostream = null;
    ObjectOutputStream oostream = null;
    ObjectInputStream oistream = null;
    
    ChangeListener a = null;
    int x = 250;
    
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
        System.out.println("Server: Shutdown successful");
    }
    
    private void setupNetwork() {
        System.out.println("Setting up network");
        try {
            clientListener = new ServerSocket(PORT);
            connection = clientListener.accept();
            clientListener.close();
            istream = connection.getInputStream();
            ostream = connection.getOutputStream();
            oostream = new ObjectOutputStream(ostream);
            oistream = new ObjectInputStream(istream);
            GameStateChange c = new GameStateChange(null, SYSTEM_ID);
            oostream.writeObject(c);
            c = (GameStateChange)oistream.readObject();
            if (!c.getMessage().equals(SYSTEM_ID)) {
                System.out.println("Server: ID mismatch");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
            System.out.println("Network setup failed");
        }
        System.out.println("Server Success");
        
        Thread t = new Thread(new Loop());
        t.start();
        System.out.println("Host Server Loop Started");
    }
    
    public HostServer() {
        setupNetwork();
    }
    
    public void registerClient(Object client) {
        clients.add(client);
    }
    
    public synchronized void commitChange(Object client, GameStateChange change) {
        changes.add(change);
    }
    
    public synchronized ArrayList<GameStateChange> getChanges(Object client) {
        /*
        for (GameStateChange c : changes) {
            if (c.isOutdatedClient(client)) {
                c.wasRecievedByClient(client);
                retChanges.add(c);
                    if (c.numOutdatedClients() == 0) changes.remove(c);
            }
        }
        */
        return changes;
    }
    
    private class Loop implements Runnable {
        public void run() {
            System.out.println("Entered Server Loop");
            GameStateChange c = null;
            
            boolean quit = false;
            
            while (!quit) {
                try {
                    Thread.sleep(100);
                        x--;
                        //System.out.println("x: "+x);
                        //if (x == 0) break;
                        if (oistream.available() > 0)
                            System.out.println("Got a message!");
                            c = (GameStateChange)oistream.readObject();
                            if (c!= null) addGameStateChange(c);
                            if (a!= null) a.changeOccurred();
                            if (c == null) {
                                //System.out.println("c is null");
                                continue;
                            }
                        if (c.getMessage().equals(QUIT_STRING)) {
                            System.out.println("Exiting server loop");
                            break;
                        } else {
                            System.out.println("Message: " + c.getMessage());
                        }
                        
                        for (int i = 0; i < getSize(); i++) {
                            GameStateChange change = getGameStateChange(i);
                            System.out.println("Server: Sending a change");
                            oostream.writeObject(change);
                            if (change.getMessage().equals(QUIT_STRING)) break;
                            changes.remove(change);
                        }
                        /*
                        for (GameStateChange change : changes) {
                            oostream.writeObject(change);
                            if (change.getMessage().equals(QUIT_STRING)) break;
                            changes.remove(change);
                
                        }
                        */
                } catch (java.io.EOFException e) {
                    e.printStackTrace();
                    quit = true;
                } catch (Exception e) {
                    e.printStackTrace();   
                }
            }   
            disconnectNetwork();
        }
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
    
}