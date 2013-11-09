public class GameController extends ViewLayerController {

    private ArmadaEngine engine;
    private DynamicSizeBroadcast dsb;
    static final int GRID_WIDTH = 38400; // The width of the grid in pixels.
    static final int GRID_HEIGHT = 21600; // The height of the grid in pixels.
    
    // Should eventually be dynamic through the observer pattern.
    static final int PANEL_WIDTH = 960;
    static final int PANEL_HEIGHT = 540;
    
    private BoundingRectangle viewRegion = new BoundingRectangle(0, 0, 500,500); //The entire grid is 2000 by 2000 pixels. This is the region that the user sees.
    
    public GameController(ArmadaEngine engine, DynamicSizeBroadcast dsb) {
        super(null);
        this.dsb = dsb;
        this.engine = engine;
        viewLayer = new ViewLayer(new BoundingRectangle(0,0, dsb));
        //viewLayer.addSublayer(hudSystem.getViewLayer());
        //viewLayer.addSublayer(gridSystem.getViewLayer());
    }
    
    public DynamicSizeBroadcast getViewSize() {
        return dsb;
    }
    
    public int getViewWidth() {
        return dsb.getWidth();
    }
    
    public int getViewHeight() {
        return dsb.getHeight();
    }
    
    public void update() {
		viewRegion.setWidth(getViewWidth());
		viewRegion.setHeight(getViewHeight());
	}
    
    /**
        Moves the viewing region. Stops at boundaries.
        @param x The number of pixels to move in the x direction.
        @param y The number of pixels to move in the y direction.
    */
    public void moveViewRegion(int x, int y) {
        viewRegion.setX(viewRegion.getX()+x);
        viewRegion.setY(viewRegion.getY()+y);
        if (viewRegion.getX() < 0) viewRegion.setX(0);
        if (viewRegion.getY() < 0) viewRegion.setY(0);
        if (viewRegion.getX() + getViewWidth() > GRID_WIDTH) {
            viewRegion.setX(GRID_WIDTH - getViewWidth());
        }
        if (viewRegion.getY() + getViewHeight() > GRID_HEIGHT) {
            viewRegion.setY(GRID_HEIGHT - getViewHeight());
        }
    }
    
    public BoundingRectangle getViewRegion() {
        return viewRegion;
    }
}