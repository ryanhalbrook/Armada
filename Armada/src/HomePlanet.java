
public class HomePlanet extends Planet {
	

	public HomePlanet(int a, int b, int w, int h, String img, int r, int maxH,
			int maxE, int s, int all, int t, int weap) {
		super(a, b, w, h, img, r, maxH, maxE, s, all, t, weap);
		// TODO Auto-generated constructor stub
	}
	
	public HomePlanet(int alliance){
		super();
		maxHull=1000;
		hull=maxHull;
		if(alliance == 1){
			x=Grid.GRID_WIDTH/2;
			y=200;
			width=100;
			height=width;
		}
		if(alliance == 2){
			x=Grid.GRID_WIDTH/2;
			y=Grid.GRID_HEIGHT - 200;
			width=100;
			height=width;
		}
		this.alliance = alliance;
	}

}
