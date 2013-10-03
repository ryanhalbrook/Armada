/*
 * Will most likely be a part of every Ship and Planet. It should be responsible for displaying the bars such as Hull and Engine.  The colors should also be different depending on alliance
 * Likely extends Element
 * the .draw() should also be called by whatever class it is a part of
 */
public class StatusBar extends Element {
    DynamicElement client; // The client is the item that this status bar represents.
    Rectangle gridRect; // The position and size of this element.
    
    public StatusBar(DynamicElement client) {
        calcGridRect();
    }
    
    public void draw() {
        calcGridRect();
        int hull = client.getHull();
        int engines = client.getEngine();
        
        // Drawing code goes here.
    }
    
    /**
    Calculate the grid rect based on the location of the "client". Keeps the status
    bar next to the client.
    **/
    private void calcGridRect() {
    
    }
}
