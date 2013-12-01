package animation;


public class AnimationQueue {
	public class Node{
		public Object value;
		public Node next;
		public Node(Object o){
			value=o;
			next=null;
		}
	}
	private Node first;
	private Node last;
	public AnimationQueue(){
		first=null;
		last=null;
	}
	public void add(Animation a){
		if(last==null){
			last= new Node(a);
			first=last;
		}
		else{
			last.next=new Node(a);
			last=last.next;
		}
	}
	public Animation remove(){
		if(first==null){
			return null;
		}
		Node t = first;
		first=first.next;
		t.next=null;
		return (Animation)(t.value);
	}

}
