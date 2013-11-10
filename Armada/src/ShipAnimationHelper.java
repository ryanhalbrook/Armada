
public class ShipAnimationHelper extends DynamicAnimationHelper {
	public ShipAnimationHelper(Ship s){
		super(s);
	}
	public void board(Ship att,Ship target){
		BoardingAnimation ba = new BoardingAnimation(att,target);
	}
	public void board(Ship att,Ship target,int mode){
		BoardingAnimation ba = new BoardingAnimation(att,target,mode);
	}
	public void dock(Ship s,Planet p){
		DockAnimation da = new DockAnimation(s,p);
	}
	public void dock(Ship s,Planet p,int mode){
		DockAnimation da = new DockAnimation(s,p,mode);
	}
	public void moveTo(int x, int y){
		MoveAnimation ma = new MoveAnimation((Ship)(this.getE()),x,y);
	}
}
