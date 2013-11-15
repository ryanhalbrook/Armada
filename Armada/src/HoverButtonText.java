import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;



public class HoverButtonText {

	private Button b;
	private String txt;
	private Rectangle r;
	//private WordUtils w;
	
	public HoverButtonText(Button inB){
		b=inB;
		r=new Rectangle();
		//txt="A WHOLE BUNCH OF YES";
	}
	public HoverButtonText(Button inB, String str){
		b=inB;
		r=new Rectangle();
		txt=str;
	}
	
	public void setText(String str){
		txt=str;
	}
	
	public void draw(Graphics g){
		if(txt==null)return;
		if(txt=="")return;
		if(b.isIn(b.getGameController().getCurrentX(), b.getGameController().getCurrentY())){
			
			r.setLocation(b.getGameController().getCurrentX(), b.getGameController().getCurrentY());
			g.setColor(new Color(25,125,175, 150));
			g.fillRect((int)r.getX()-(g.getFontMetrics().stringWidth(txt) + 30), (int)r.getY()-30, g.getFontMetrics().stringWidth(txt) + 30, 30);
			g.setColor(Color.WHITE);
			g.drawString(txt, (int)r.getX()-(g.getFontMetrics().stringWidth(txt)  + 15), (int)r.getY()-10);
		}
	}
	
}
