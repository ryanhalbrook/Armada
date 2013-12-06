package animation;

/**
 * 
 * @author Yun
 * Class that stores the animations
 */
public class AnimationQueue {
	/**
	 * Node that contains animation
	 */
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
	/**
	 * default constructor.
	 * creates an empty queue.
	 */
	public AnimationQueue(){
		first=null;
		last=null;
	}
	/**
	 * adds a new element to queue
	 * @param a Animation instance
	 */
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
	/**
	 * dequeues an element
	 * @return Animation
	 */
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
