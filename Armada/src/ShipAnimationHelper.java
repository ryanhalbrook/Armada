
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
}
