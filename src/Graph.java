import java.util.Arrays;
/**
 * @author Casey
 *
 */

/**
 * A doubly linked list implementation for graph objects
 * @author Casey
 */
class gNode{
	/**
	 * NAME refers to the identity of the object stored in the list
	 */
	gVert NAME;
	/**
	 * next and previous refer to the direction of doublely 
	 * linked list connections
	 */
	gNode next, previous;
	/**
	 * The singular node in a linked list implementation
	 * @param name the object being stored, in this implementation a vertex for 
	 * 		a graph
	 * @param n the next node in the list
	 * @param prev the previous node in the list
	 */
	gNode(gVert name, gNode n, gNode prev){
		NAME = name;
		next = n;
		previous = prev;
	}
	
	
}

/**
 * A standard stack implementation for graph ojects
 * @author Casey
 */
class gStack{
	/**
	 * size are how many objects stored in the stack
	 */
	int size;
	/**
	 * The head is the primary unit being operated on in the stack object
	 */
	gNode head;
	/**
	 * The prototype stack object. Head are stacked items and size are how
	 * many items are stacked.
	 */
	public gStack(){
		head = null;
		size = 0;
	}
	/**
	 * push an existing gNode object onto an existing stack object.
	 * This is the "low level" push function.
	 * @param node
	 */
	public void push(gNode node){
		//if the head is null just add the vertex to it.
		if(this.head==null){
			this.head = node;
		}
		else{
			//otherwise point the node towards the current head and set this 
			//		as the new head.
			node.next = this.head;
			this.head = node;
		}
		size++;
	}
	/**
	 * push a vertex object onto an existing stack object
	 * @param node the node to be added to the stack, a gVert object
	 */
	public void push(gVert node){
		//just turn the vertex into a gNode and make this "easy"
		//it would likely be better form to make a different function
		//name for the "low level" function.
		gNode Node = new gNode(node, null, null);
		this.push(Node);
	}
	/**
	 * Pops the top of the stack and provides the vertex on top.
	 * @return The most recently added vertex.
	 */
	public gVert pop(){
		//find the vertex object at the top of the stack
		gVert pop = this.head.NAME;
		//set  the head of the stack to be the next item and decrement the size
		this.head = this.head.next;
		this.size--;
		return pop;
	}
}

/**
 * A standard queue implementation for graph objects
 * @author Casey
 */
class gQueue{
	/**
	 * how many items are currently enqueued
	 */
	int size;
	/**
	 * The current top of the queue
	 */
	gNode head;
	/**
	 * The last item in the queue, this allows for easy insertion at the end of the queue.
	 */
	gNode previous_add;
	/**
	 * The initialization for a new queue of size 0, head is 0, previous_add
	 * is currenly set to the head;
	 */
	gQueue(){
		size = 0;
		head = null;
		previous_add = head;
	}
	
	/**
	 * "Low level" function to add an item to the currently existing queue
	 * if the queue is empty the head will be set to the input node
	 * otherwise increment the size and add the node to be the next node
	 * after the previously_add[-ed] node.
	 * always increment the size
	 * @param node the node to be added to the queue
	 */
	public void enqueue(gNode node){
		//if the queue is empty set the head to the top and keep the
		//previous add pointed to the head
		if(this.head == null){
			this.head = node;
			this.previous_add = this.head;
		}
		else{
			//otherwise just point the previous_add element towards the new node
			//and set previous_add as the new node
			this.previous_add.next = node;
			this.previous_add = node;
		}
		this.size++;
	}
	/**
	 * The "High level" function that queues a vertex into the current queue
	 * @param node the vertex object to be added to the queue
	 */
	public void enqueue(gVert node){
		//creates a linked list node to hold the vertex
		gNode Node = new gNode(node, null, null);
		this.enqueue(Node);
	}
	/**
	 * Dequeues the head of the queue and returns the vertex. 
	 * Decrements queue size
	 * @return	the next vertex to be served from the queue
	 */
	public gVert dequeue(){
		//
		gVert dq = this.head.NAME;
		this.head = this.head.next;
		this.size--;
		return dq;
	}
}

/**
 * A class designed to operate all vertex operations using
 * an Adjacency List implementation for flow, undirected, and directed graphs
 * @author Casey
 *
 */
class gVert{

	int NAME, MLIST = 0, FLOWINLIST = 0; int[] COST, CAPACITY, FLOWIN;
	gVert[] NEIGHBORS, FlowFrom, FlowOut;
	
	/**
	 * 
	 * @param name
	 * @param n
	 */
	gVert(int name, int n){
		NAME = name;
		NEIGHBORS = new gVert[n];
		COST = new int[n];
		CAPACITY = new int[n];
		FLOWIN = new int[n];
		FlowFrom = new gVert[n];
		FlowOut = new gVert[n];
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public void add_Undirect_Neighbor_0(gVert a, gVert b){
		a.NEIGHBORS[MLIST] = b;
		b.NEIGHBORS[MLIST] = a;
		a.MLIST++; b.MLIST++;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param cost
	 */
	public void add_Undirect_Neighbor_Costly(gVert a, gVert b, int cost){
		a.NEIGHBORS[a.MLIST] = b;
		a.COST[a.MLIST] = cost;
		b.NEIGHBORS[b.MLIST] = a;
		b.COST[b.MLIST] = cost;
		a.MLIST++; b.MLIST++;
	}
	
	/**
	 * Creates a zero cost directed connection between two nodes a -> b; b-/-> a
	 * @param a node the connection is leaving from
	 * @param b node receiving the connection
	 */
	public void add_Direct_Neighbor_0(gVert a, gVert b){
		a.NEIGHBORS[MLIST] = b;
		a.MLIST++;
	}
	
	/**
	 * Creates a costly directed connection between two nodes a -> b; b-/-> a
	 * @param a node the connection is leaving from
	 * @param b node receiving the connection
	 * @param cost cost to move to that node
	 */
	public void add_Direct_Neighbor_Costly(gVert a, gVert b, int cost){
		a.NEIGHBORS[a.MLIST] = b;
		a.COST[a.MLIST] = cost;
		a.MLIST++; 
	}
	
	/**
	 * Creates a flow directed connection between two nodes a -> b; b--> a
	 * Also creates flow value between b->a
	 * @param a node the connection is leaving from
	 * @param b node receiving the connection
	 * @param cap how much flow may move along this edge
	 */
	public void add_Direct_FLOW_Neighbor_Costly(gVert a, gVert b, int cap){
		a.NEIGHBORS[a.MLIST] = b;
		a.COST[b.NAME] = 0;
		a.CAPACITY[b.NAME] = cap;
		a.MLIST++;
		b.FlowFrom[a.NAME] = a;
		b.FLOWIN[a.NAME] = cap;
		b.FLOWINLIST++;
	}
	
	
}

public class Graph {

	int SIZE;
	gVert[] VERTICES;
	int[] parent;
	/**
	 * 
	 * @param verts
	 */
	Graph(int verts){
		VERTICES = new gVert[verts];
		SIZE = verts;
	}
	
	/**
	 * A BFS designed for network flow operations
	 * @param start
	 * @param target
	 * @return
	 */
	public int[] FLOW_BFS(gVert start, gVert target){
		boolean[] seen = new boolean[this.SIZE];
		this.parent = new int[this.SIZE];
		Arrays.fill(parent, -1);
		gQueue queue = new gQueue();
		queue.enqueue(start);
		parent[start.NAME] = start.NAME;
		int[] M = new int[this.SIZE];
		M[start.NAME] = Integer.MAX_VALUE;
		while(queue.size > 0){
			gVert head = queue.head.NAME;
			for(int i = 0; i<head.MLIST; i++){
				gVert v = head.NEIGHBORS[i];
				if(!seen[v.NAME] && head.CAPACITY[head.NAME] - head.COST[i]>0 
						&&this.parent[v.NAME] == -1){
					this.parent[v.NAME] = head.NAME;
					M[v.NAME] = Math.min(M[head.NAME], head.CAPACITY[i]-head.COST[i]);
					if(v!=target){
						queue.enqueue(v);
					}
					else{
						return M;
					}
					seen[v.NAME] = true;
				}
			}
		}
		return M;
	}
	
	/**
	 * !!!!!!Must test this!!!!!
	 * @param start
	 * @param target
	 * @return
	 */
	public int EKflow(gVert start, gVert target){
		int flow = 0;
		while(true){
			// GCWOK approved!
			gQueue queue = new gQueue();
			queue.enqueue(start);
			//parent also used to identify all seen nodes
			int[] parent = new int[this.SIZE];
			Arrays.fill(parent, -1);
			while(queue.size>0){
				gVert current = queue.dequeue();
				for(int i = 0; i<current.MLIST; i++){
					if(parent[current.NEIGHBORS[i].NAME]!=-1 && current.CAPACITY[i]>current.COST[i]){
						parent[current.NEIGHBORS[i].NAME]= current.NAME;
						queue.enqueue(current.NEIGHBORS[i]);
					}
				}
			}
			if(parent[target.NAME]==-1){
				break;
			}
			else{
				int df = Integer.MAX_VALUE;
				for(int i = parent.length-1; i>=0; i = parent[i]){
						//the vertex that pumps flow into this vertex
						//gVert current = this.VERTICES[i];
						gVert in = this.VERTICES[parent[i]];
						df = Math.min(df, in.CAPACITY[i]-in.COST[i]);
				}
				for(int i = parent.length-1; i>=0; i = parent[i]){
					gVert current = this.VERTICES[i];
					gVert in = this.VERTICES[parent[i]];
					in.COST[current.NAME] += df;
					current.FLOWIN[in.NAME]-=df;
				}
			}
		}
		
		return flow;
	}
	
	/**
	 * to be implemented
	 * 
	 * !
	 * Ideas: use a BFS in order to accurately print out 
	 * 		S-B S-C B-D B-F C-T
	 * 		->write a separate program to create a feasible graph with these
	 * !
	 * 
	 * 
	 * @param start where the print should begin from 
	 */
	public void printFlow(gVert start){
		
		
	}
	/**
	 * To be implemented
	 */
	public void printUndirected(){
		
	}
	
	
}
