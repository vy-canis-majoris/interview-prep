package HashSet;

 class LinkedList<T> {
	private Node<T> head;
	private Node<T> tail;

	public Node<T> getHead() {
		return head;
	}
	public void setHead(Node<T> head) {
		this.head = head;
	}
	public Node<T> getTail() {
		return tail;
	}
	public void setTail(Node<T> tail) {
		this.tail = tail;
	}

	 void insert(Node<T> newNode){
		if(head == null){
			head = newNode;
			tail = newNode;
		}else{
			tail.setNext(newNode); 
			newNode.setPrevious(tail);
			tail = tail.getNext();
		}
	}
	
	 void delete(Node<T> element){
		if(element.getPrevious()!=null){
			if(element.getNext()!=null){ 
				element.getPrevious().setNext(element.getNext());
				element.getNext().setPrevious(element.getPrevious());
			}else{
				element.getPrevious().setNext(null);
				tail = element.getPrevious();
			}
		}else{
			if(element.getNext()!=null){
				head = element.getNext();
				element.getNext().setPrevious(null);
			}else{
				head = null;
				tail = null;
			}	
		}
		
	}

}
