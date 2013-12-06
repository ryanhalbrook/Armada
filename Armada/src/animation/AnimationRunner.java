package animation;

import element.Element;
/**
 * 
 * @author Yun
 * Class that runs the animations
 */
public class AnimationRunner implements Runnable{
	private AnimationQueue a;
	/**
	 * Constructor that creates a thread for animation
	 * @param aq AnimationQueue that contains the animations that need to be executed
	 */
	public AnimationRunner(AnimationQueue aq){
		a=aq;
		Thread t= new Thread(this);
		t.start();
	}
	@Override
	/**
	 * runs the animations.
	 * also manages the actor's targetability
	 */
	public void run() {
		Animation ani = a.remove();
		Element[] elist= null;
		while(ani!=null){
			Element[] temp = ani.getActors();
			if(elist==null){
				for(int i=0;i<temp.length;i++){
					if(temp!=null){
						temp[i].setTargetable(false);
					}
				}
			}
			else{
				for(int i=0;i<elist.length;i++){
					boolean diff=true;
					for(int j=0;j<temp.length;j++){
						
						if(temp!=null){
							temp[j].setTargetable(false);
						}
						if(temp[j].getID()==elist[i].getID()){
							diff=false;
						}
					}
					if(diff){
						elist[i].setTargetable(true);
					}
				}
			}
			ani.run();
			elist=temp;
			ani=a.remove();
		}
		if(elist!=null){
			for(int i=0;i<elist.length;i++){
				elist[i].setTargetable(true);
			}
		}
		
	}

}
