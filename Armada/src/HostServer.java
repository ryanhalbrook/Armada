import java.util.*;
import java.net.*;
import java.io.*;

public class HostServer implements GameServer {
    static final int PORT = 1730;
    static final String SYSTEM_ID = "ARMADA-AF3FD2";
    static final String QUIT_STRING = "QUIT_GAME";
    ArrayList<Object> clients = new ArrayList<Object>();
    ArrayList<GameStateChange> changes = new ArrayList<GameStateChange>();
    ArrayList<GameStateChange> outGoingChanges = new ArrayList<GameStateChange>();
    ChangeListener connectionListener = null;
    Thread setupThread = null;
    boolean attemptConnection = true;
    
    boolean networkUp = true;
    
    ServerSocket clientListener;
    Socket connection = null;
    InputStream istream = null;
    OutputStream ostream = null;
    ObjectOutputStream oostream = null;
    ObjectInputStream oistream = null;
    
    ChangeListener a = null;
    int x = 250;
    public void setConnectionListener(ChangeListener a) {
        this.connectionListener = a;
    }
    public void setChangeListener(ChangeListener a) {
        this.a = a;
    }
    
    public void disconnectNetwork() {
        attemptConnection = false;
    networkUp = false;
        try {
            if (clientListener != null)
                clientListener.close();
            if (oostream != null)
                oostream.close();
            if (oistream != null)
                oistream.close();
            if (ostream != null)
                ostream.close();
            if (istream != null)
                istream.close();
            
        } catch (java.net.SocketException e) {
            e.printStackTrace();
            System.out.println("The server was forced to stop waiting.");
        }
        
        catch (IOException e) {
            System.out.println("Failed to close network");
            e.printStackTrace();
        }
        System.out.println("Server: Shutdown successful");
    }
    
    private void setupNetwork() {
        
        System.out.println("Setting up network");
        
        try {
            clientListener = new ServerSocket(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clientListener == null) return;
        while(attemptConnection) {
        try {
            clientListener.setSoTimeout(1000);
            connection = clientListener.accept();
            clientListener.close();
            attemptConnection = false;
            if (connection == null) return;
            
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
            
            System.out.println("Server Success");
            
        /*
        Thread t = new Thread(new Loop());
        t.start();
        */
        
        Thread read = new Thread(new ReadingLoop());
        read.start();
        Thread write = new Thread(new WritingLoop());
        write.start();
        System.out.println("Host Server Loop Started");
        if (connectionListener != null)
        connectionListener.changeOccurred();
        
        } catch(java.net.SocketTimeoutException e) {
            System.out.println("Accept timed out");
            
        } catch (Exception e) {
            e.printStackTrace();
            attemptConnection = false;
            System.out.println("Network setup failed");
        }
        }
        
    }
    
    public HostServer() {
        //setupNetwork();
        setupThread = new Thread(new ConnectionStarter());
        setupThread.start();
    }
    
    public void registerClient(Object client) {
        clients.add(client);
    }
    
    public synchronized void commitChange(Object client, GameStateChange change) {
        //changes.add(change);
        outGoingChanges.add(change);
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
    
    private class ConnectionStarter implements Runnable {
        public void run() {
            setupNetwork();
        }
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
                            /*
                            if (c == null) {
                                //System.out.println("c is null");
                                continue;
                            }
                            */
                        if (c.getMessage().equals(QUIT_STRING)) {
                            System.out.println("Exiting server loop");
                            break;
                        } else {
                            System.out.println("Message: " + c.getMessage());
                        }
                        
                        for (int i = 0; i < getOutGoingSize(); i++) {
                            GameStateChange change = getOutGoingGameStateChange(i);
                            System.out.println("Server: Sending a change");
                            oostream.writeObject(change);
                            if (change.getMessage().equals(QUIT_STRING)) break;
                            removeOutGoingGameStateChange(i);
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
            GameStateChange gsc = changes.get(index);
            changes.remove(gsc);
            return gsc;
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
    
    private synchronized int getOutGoingSize() {
        return outGoingChanges.size();
    }
    
    private synchronized GameStateChange getOutGoingGameStateChange(int index) {
        System.out.println("Getting an outgoing change");
        if (index < outGoingChanges.size()) {
            return outGoingChanges.get(index);
        }
        return null;
    }
    
    private synchronized void addOutGoingGameStateChange(GameStateChange gsc) {
        System.out.println("Adding Change: " + gsc.getMessage());
        changes.add(gsc); 
    }
    
    private synchronized void removeOutGoingGameStateChange(int index) {
        if (index < outGoingChanges.size()) {
            outGoingChanges.remove(index);
        }
    }
    
    private class WritingLoop implements Runnable {
        public void run() {
            GameStateChange c = null;
        
            boolean quit = false;
        
            while (!quit && networkUp) {
        
                try {
            
                    Thread.sleep(100);
                    x--;
                    if (x % 50 == 0) System.out.println("Client Side Loop Running");
                    //if (x == 0) break;
                    for (int i = 0; i < getOutGoingSize(); i++) {
                        GameStateChange change = getOutGoingGameStateChange(i);
                        System.out.println("Client: Sending a change");
                        oostream.writeObject(change);
                        if (change.getMessage().equals(QUIT_STRING)) break;
                        removeOutGoingGameStateChange(i);
                    }
                    
                } catch (EOFException e) {
                    e.printStackTrace();
                    quit = true;
                    networkUp = false;
                } catch (Exception e) {
                    e.printStackTrace();   
                    //quit = true;
                }
            }
            System.out.println("About to disconnect");
            networkUp = false;
            disconnectNetwork();
        }
    }
    
    
    private class ReadingLoop implements Runnable {
        public void run() {
            GameStateChange c = null;
        
            boolean quit = false;
        
            while (!quit && networkUp) {
        
                try {
            
                    Thread.sleep(100);
                    x--;
                    if (x % 50 == 0) System.out.println("Client Side Loop Running");
                    
                    if (oistream.available() > 0) 
                    System.out.println("Got a message");
                    System.out.println("CLIENT: Reading");
                    c = (GameStateChange)oistream.readObject();
                    System.out.println("CLIENT: Done Reading");
                    
                    if (c!= null) addGameStateChange(c);
                    if (a != null) a.changeOccurred();
                    if (c == null) continue;
                
                    if (c.getMessage().equals(QUIT_STRING)) {
                        System.out.println("Exiting server loop");
                        break;
                    } else {
                        System.out.println("Message: " + c.getMessage());
                    }
                    
                } catch (EOFException e) {
                    e.printStackTrace();
                    quit = true;
                    networkUp = false;
                } catch (Exception e) {
                    e.printStackTrace();   
                    //quit = true;
                }
            }
            networkUp = false;
            System.out.println("About to disconnect");
            disconnectNetwork();
        }
    }
    
}