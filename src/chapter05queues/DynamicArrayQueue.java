/*Copyright (c) Dec 21, 2014 CareerMonk Publications and others.
 * E-Mail           	: info@careermonk.com 
 * Creation Date    	: 2015-01-10 06:15:46 
 * Last modification	: 2006-05-31 
               by		: Narasimha Karumanchi 
 * File Name			: DynamicArrayQueue.java
 * Book Title			: Data Structures And Algorithms Made In Java
 * Warranty         	: This software is provided "as is" without any 
 * 							warranty; without even the implied warranty of 
 * 							merchantability or fitness for a particular purpose. 
 * 
 */


package chapter05queues;

public class DynamicArrayQueue{ 
	// Array used to implement the queue.
	private int[] queueRep;
	private int size, front, rear;
	
	// Length of the array used to implement the queue.
	private static int CAPACITY = 16;	//Default Queue size
	
	public static int MINCAPACITY=1<<15; // power of 2

	// Initializes the queue to use an array of default length.
	public DynamicArrayQueue (){
		queueRep = new int [CAPACITY];
		size  = 0; front = 0; rear  = 0;
	}
	
	// Initializes the queue to use an array of given length.
	public DynamicArrayQueue (int cap){
		queueRep = new int [ cap];
		//Resetting the CAPACITY to current user provided cap value.
		CAPACITY = cap; 
		size  = 0; front = 0; rear  = 0;
	}
	
	// Inserts an element at the rear of the queue. This method runs in O(1) time.
	public void enQueue (int data)throws NullPointerException, IllegalStateException{  
		if (size == CAPACITY)
			expand();
		size++;
		queueRep[rear] = data;
		rear = (rear+1) % CAPACITY;
	}

	// Removes the front element from the queue. This method runs in O(1) time.
	public int deQueue () throws IllegalStateException{
		// Effects:   If queue is empty, throw IllegalStateException,
		// else remove and return oldest element of this
		if (size == 0)
			throw new IllegalStateException ("Queue is empty: Underflow");
		else {
			size--;
			int data = queueRep [ (front % CAPACITY) ];
			queueRep [front] = Integer.MIN_VALUE;
			front = (front+1) % CAPACITY;
			return data;
		}
	}

	// Checks whether the queue is empty. This method runs in O(1) time.
	public boolean isEmpty(){ 
		return (size == 0); 
	}
	
	// Checks whether the queue is full. This method runs in O(1) time.
	public boolean isFull(){ 
		return (size == CAPACITY); 
	}
	
	// Returns the number of elements in the queue. This method runs in O(1) time.
	public int size() {
		return size;
	}
	
	// Increases the queue size by double
	private void expand(){
		int length = size();
		int[] newQueue = new int[length<<1];  // or 2* length

		//This loop wont work for circular case when our rear will be pointing to the same location as that of front , i.e 
		// queue[5] = {1,2,3,4,5} , now our rear will be at (rear + 1) % CAPACITY
		// i.e 0 and loop condition will run only once.
// 		for(int i = front; i <= rear; i ++)
// 			newQueue[i-front] = queueRep[i%CAPACITY];
		
		// if front and  rear pointer are pointing at the exact same location i.e 0 , when only enqueue is done and not dequeue uptil capacity
        if(rear == 0){ 
            for(int i = 0 ; i < queueRep.length ; i++){
                newQueue[i] = queueRep[i];
            }          
        }
        
       // if front and  rear pointer are pointing at the exact same location but not at 0 , i.e dequeue() is also performed in between.		
        else{
            
            int j = 0, temp = front;
            //System.out.println("temp value : " + temp + " and rear " + rear);
                
            //we go only till one element before rear pointer , because there might be a issue of rear and front both pointer pointing at same location other than 0
            while(temp != rear - 1){
                newQueue[j] = queueRep[temp%CAPACITY];
                j++;
                temp = (temp+1) % CAPACITY;
                
            }
            
            newQueue[j] = queueRep[temp];
            
        }
        

		queueRep = newQueue;
		front = 0;
		//if we go uptil size - 1 then we will be pointing the latest element entered in our queue and not the latest free slot to enter 
		// the value
		//rear = size-1;
		//as size is incremented each time on enqueue and decremented each time at dequeue
		rear = size;
		CAPACITY *= 2;
	}
	
	// dynamic array operation: shrinks to 1/2 if more than than 3/4 empty
	@SuppressWarnings("unused")
	private void shrink() {
		int length = size;
		if(length <= MINCAPACITY || length <<2 >= length) 
			return;

		if(length<MINCAPACITY) length = MINCAPACITY;
		int[] newQueue=new int[length];
		System.arraycopy(queueRep,0,newQueue,0,length+1);
		queueRep=newQueue;
	}
	
	// Returns a string representation of the queue as a list of elements, with
	// the front element at the end: [ ... , prev, rear ]. This method runs in O(n)
	// time, where n is the size of the queue.	
	public String toString(){
		String result = "[";
		for (int i = 0; i < size; i++){
			result += Integer.toString(queueRep[ (front + i) % CAPACITY ]);
			if (i < size -1) {
				result += ", ";
			}
		}
		result += "]";
		return result;
	}
}
