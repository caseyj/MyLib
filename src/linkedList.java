/**
 * 
 * @author Casey
 *
 */

class llnode{
	llnode NEXT, PREVIOUS;
	int NAME;
	public llnode(int name, llnode previous, llnode next){
		NEXT = next;
		NAME = name;
		PREVIOUS = previous;
	}	
}

/**
 * 
 * @author Casey
 *
 */
public class linkedList {
	llnode head;
	int size;
	
	/**
	 * 
	 */
	public linkedList(){
		size = 0;
		head = null;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void newNode(int name){
		size++;
		if(this.size==0){
			head = new llnode(name, null, null);
		}
		else{
			llnode temp = this.head;
			while(temp.NEXT != null){
				temp = temp.NEXT;
			}
			temp.NEXT = new llnode(name, temp, null);
		}
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	public llnode removeNode(llnode target){
		llnode pr = target.PREVIOUS;
		llnode ne = target.NEXT;
		pr.NEXT = ne;
		ne.PREVIOUS = pr;
		target.NEXT = null;
		target.PREVIOUS = null;
		this.size--;
		return target;
	}
	
	public int[] prioritize(){
		int[] sortable = new int[this.size];
		
		return sortable;
	}
	
}


