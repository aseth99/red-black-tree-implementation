
public class RedBlackTree {
	
	
    /**
     * Root node of the red black tree
     */
    private Node root = null;

    /**
     * Size of the tree
     */
    private int size = 0;

    /**
     * Search the tree to find if the value is contained
     * @param value     {@code int} the value to be checked
     * @return          {@code boolean} If contains, return {@code true}, otherwise return {@code false}
     */
    public boolean contains(int value, Node curnode) {
        // TODO: Lab 2 Part 2-1 -- find an integer from the tree
    	if (curnode == null){
    		return false;
    	}
    	//System.out.println("here");
    	//System.out.println(curnode.value);
    	//System.out.println(value);
    	Integer x = curnode.value;
    	Integer y = value;
    	if (x.equals(y)){
    		System.out.println("equals!!!!");
    		return true;
    	}
    	if (curnode.value == value){
    		System.out.println("equals!");
    		return true;
    	}
    	
    	if (curnode.value > value){
    		System.out.println(curnode + " " + curnode.lChild + " " + curnode.lChild.parent);
    		if(curnode.lChild.value != null){
    			return contains(value, curnode.lChild);
    		}
    	}
    	if (curnode.value < value){
    		System.out.println(curnode + " " + curnode.rChild.value + " " + curnode.rChild.parent);
    		if(curnode.rChild.value != null){
    			return contains(value, curnode.rChild);
    		}
    	}
        return false;
    }

    /**
     * Insert an integer to the tree
     * @param data      {@code int} New element to be inserted
     */
    public void insert(int value) {
    	System.out.println(value);
        // TODO: Lab 2 Part 2-2 -- insert an integer into the tree
    
    	if(root == null){
			root = new Node(value);
			root = root.fixInsColor(root);
		}
    	else if(root != null){
    		root = root.addNode(value, root);
    		//root = root.fixInsColor(root);
    		}
    	
    	}
    
    /**
     * Get the size of the tree
     * @return          {@code int} size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Cast the tree into a string
     * @return          {@code String} Printed format of the tree
     */
    @Override public String toString() {
        // TODO: Lab 2 Part 2-3 -- print the tree, where each node contains both value and color
        // You can print it by in-order traversal
    	traverse(root);
        return null;
    }
    public RedBlackTree.Node traverse(Node currentNode){
		//System.out.println("traverse");
		//System.out.format("%d    %s\n",currentNode.value,currentNode.color);
		System.out.println(currentNode + " parent:" + currentNode.parent + " LC:" + currentNode.lChild + " RC:" + currentNode.rChild);
    	if (currentNode.lChild!=null){
			//System.out.println(currentNode.lChild + " --> " + currentNode);
			traverse(currentNode.lChild);
		}
		
		if (currentNode.rChild!=null){
			//System.out.println(currentNode + " <-- " + currentNode.rChild);
			traverse(currentNode.rChild);
		}
		return currentNode;
	}
    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        for (int i = 0; i < 10; i++)
            rbt.insert((int) (Math.random() * 200));
		//rbt.insert(49);
		//rbt.insert(48);
		//rbt.insert(13);
		//rbt.insert(93);
		//rbt.insert(27);
		//rbt.insert(37);
		//rbt.insert(82);
		//rbt.insert(65);
		//rbt.insert(97);
		//rbt.insert(90);


        assert rbt.root.color == RedBlackTree.Node.BLACK;
        System.out.println(rbt.root);           // This helps to figure out the tree structure
        System.out.println(rbt);
        System.out.println(rbt.contains(97, rbt.root));
        //System.out.println(rbt.traverse(rbt.root));
    }


    /**
     * The {@code Node} class for {@code RedBlackTree}
     */
    private class Node {
        public static final boolean BLACK = true;
        public static final boolean RED = false;

        public Integer value;
        public boolean color = BLACK;
        public Node parent = null; 
        public Node lChild = null; 
        public Node rChild = null;

        public Node(Integer value) {             // By default, a new node is black with two NIL children
            this.value = value;
            if (value != null) {
                lChild = new Node(null);         // And the NIL children are both black
                lChild.parent = this;
                rChild = new Node(null);
                rChild.parent = this;
            }
        }
        
		public RedBlackTree.Node addNode(int value, Node root){
			
			if(root.value < value){
				if(root.rChild.value == null){
					root.rChild = new Node(value);
					root.rChild.parent = root;
					Node before = root.rChild.parent;
					
					
					root.rChild = fixInsColor(root.rChild);					
					
					Node flC = new Node(root.rChild.value);
					Node change = root.rChild.parent; 
					if(before != change){
						root = change;
						root.rChild = flC;
						root.rChild.parent = root;
					}
					

					return root;
				}else{
					root.rChild = root.addNode(value, root.rChild);
					return root;
				}
			}

			else if(root.value > value){
				if(root.lChild.value == null){
					root.lChild = new Node(value);
					root.lChild.parent = root;
					Node before = root.lChild.parent;
					
					root.lChild = fixInsColor(root.lChild);
					
					Node flC = new Node(root.lChild.value);
					Node change = root.lChild.parent; 
					if(before != change){
						root = change;
						root.lChild = flC;
						root.lChild.parent = root;
					}
					

					return root;
				}else{
					root.lChild = root.addNode(value, root.lChild);
					return root;
				}
			}
			return root;	
		}
		public RedBlackTree.Node fixInsColor(Node root){
			root.color = false;
			root = Case1(root);
			return root;
		}
		public RedBlackTree.Node Case1(Node root){

			if(root.parent == null){
				root.color = true;
			}
			else{
			root = Case2(root);
			}
			return root;
		}
		public RedBlackTree.Node Case2(Node root){
			if(root.parent.color == true){
				return root;
				}
			else{
				root = Case3(root);
				return root;
			}
		}
		public RedBlackTree.Node Case3(Node root){
			
				if((root.parent.parent != null) && (root.parent.parent.lChild != null) && (root.parent.parent.rChild != null)){
					if((root.parent.parent.lChild.color == false) && (root.parent.parent.rChild.color == false)){
						root.parent.parent.color = false;
						root.parent.parent.lChild.color = true;
						root.parent.parent.rChild.color = true;
						root.parent.parent = fixInsColor(root.parent.parent);
						return root;
					}
					else
					{ 
						root = Case4(root);
						return root;
					}
				}
				else
				{ 
					root = Case4(root);
					return root;
				}
			
			
		}
		public RedBlackTree.Node Case4(Node root){
			if(root.parent.parent != null){
				if(root.parent == (root.parent.parent.lChild)){
					if(root.parent.parent.rChild != null){
						if((root.parent.color == false) && (root.parent.parent.rChild.color == true)){
							if(root == (root.parent.rChild)){
								//System.out.println(root);
								root = RotateLeftParent(root);
								//System.out.println(root + " 13? " + root.parent + " 27? " +  root.parent.parent + " 48");
								//System.out.println(root.parent.parent.rChild);
								root = Case5(root);
							}
						}
					}
				}
				else if(root.parent == (root.parent.parent.rChild)){
						if(root.parent.parent.lChild != null){
							if((root.parent.color == false) && (root.parent.parent.lChild.color == true)){
								if(root == (root.parent.lChild)){
									root = RotateRightParent(root);
									//System.out.println(root + "case 4rla");
									//root = root.rChild;
									//System.out.println(root + "case 4rl");
									root = Case5(root);
								}
							}
						}
					}
				}
			return root;
			}
		
		public RedBlackTree.Node Case5(Node root){
			if((root.parent == (root.parent.parent.lChild)) && (root == (root.parent.lChild))){
					root.parent.color = true;
					root.parent.parent.color = false;
					root = RotateRightGP(root);
					//System.out.println(root + " parent:" + root.parent + " LC:" + root.lChild + " RC:" + root.rChild);
					return root;
				}
			else if ((root.parent == (root.parent.parent.rChild)) && (root == (root.parent.rChild))){		
				root.parent.color = true;
				root.parent.parent.color = false;
				root = RotateLeftGP(root);
				//System.out.println(root + " parent:" + root.parent + " LC:" + root.lChild + " RC:" + root.rChild);
				return root;
				}
			//System.out.println("only case 4? " + root);
			return root;
			}
				
		
		
		public RedBlackTree.Node RotateRightParent(Node root){
			
			Node gp = root.parent.parent;
			Node p = root.parent.parent.rChild;  //System.out.println(p + " og p");
			Node pr = root.parent.rChild;
			Node n = root; //System.out.println(n + " og node");
			Node nRC = root.rChild;
			Node nLC = root.lChild;
			Node u = root.parent.parent.lChild;
			root = p; //System.out.println(root + "p -->new n");
			root.color = false;
			root.parent = n;
			root.parent.rChild = root;
			root.parent.parent = gp;
			root.parent.parent.rChild = root.parent;
			
			root.rChild = pr;
			root.rChild.parent = root;
			root.lChild = nRC;
			root.lChild.parent = root;
			root.parent.lChild = nLC;
			root.parent.lChild.parent = root.parent;
			
			root.parent.parent.lChild = u;
			root.parent.parent.lChild.parent = root.parent.parent;
			//System.out.println(root + "lolzzz" + root.parent);
			return root;
		}
		public RedBlackTree.Node RotateLeftParent(Node root){
			Node gp = root.parent.parent;
			//System.out.println(root.parent.parent);
			Node p = root.parent.parent.lChild;  //System.out.println(p + " og p");
			Node pl = root.parent.lChild;
			Node n = root; //System.out.println(n + " og node");
			Node nRC = root.rChild;
			Node nLC = root.lChild;
			Node u = root.parent.parent.rChild;
			//System.out.println(root.parent.parent.rChild);
			//System.out.println(gp + " 48 " + p + " 13 " + pl + " null " + n + " 27 " + nRC + " null " + nLC);
			root = p; //System.out.println(root + "p -->new n");
			root.color = false;
			root.parent = n;
			root.parent.lChild = root;
			root.parent.parent = gp;
			root.parent.parent.lChild = root.parent;
			
			root.lChild = pl;
			root.lChild.parent = root;
			root.rChild = nLC;
			root.rChild.parent = root;
			root.parent.rChild = nRC;
			root.parent.rChild.parent = root.parent;
			
			root.parent.parent.rChild = u;
			root.parent.parent.rChild.parent = root.parent.parent;
			//System.out.println(root.parent.parent.rChild);
			
			//System.out.println(root + " 13? " + root.parent + " 27? " +  root.parent.parent + " 48");
			//System.out.println(root.parent.parent.lChild + " 27? " + root.parent.lChild + " 13? " +  root.parent.parent + " 48");
			
			return root;
			
		}
		public RedBlackTree.Node RotateRightGP(Node root){
			//System.out.println(root + "rrgp");
			Node gp = root.parent.parent;
			//System.out.println(root.parent.parent + "gp");
			Node p = root.parent;
			//System.out.println(root.parent + " p ");
			Node pr = root.parent.rChild;
			//System.out.println(root.parent.rChild + " pr");
			Node n = root;
			//System.out.println(root + " n");
			Node nRC = root.rChild;
			//System.out.println(root.rChild + " nrc");
			Node nLC = root.lChild;
			//System.out.println(root.lChild + " nlc");
			Node u = root.parent.parent.rChild;
			//Node ur = root.parent.parent.rChild.rChild;
			//Node ul = root.rChild.lChild;
			
			root = new Node(p.value);
			root.color = true;
			root.parent = gp.parent;
			//System.out.println(gp.parent + "lolz" + root);
			root.rChild = gp;
			root.rChild.color = false;
			root.rChild.parent = root;
			root.lChild = new Node(n.value);
			root.lChild.color = false;
			root.lChild.parent = root;
			
			root.lChild.rChild = nRC;
			root.lChild.rChild.parent = root.lChild;
			root.lChild.lChild = nLC;
			root.lChild.lChild.parent = root.lChild;
			
			root.rChild.lChild = p.rChild;
			if(pr != null){
				root.rChild.lChild.parent = root.rChild;
				}
			root.rChild.rChild = u;
			root.rChild.rChild.parent = root.rChild;
			
			return root.lChild;
		}
		public RedBlackTree.Node RotateLeftGP(Node root){

			//System.out.println(root + "  rlgp");
			Node gp = root.parent.parent;
			//System.out.println(root.parent.parent + "gp");
			Node p = root.parent;
			//System.out.println(root.parent + " p ");
			Node pl = root.parent.lChild;
			//System.out.println(root.lChild.lChild + " pr");
			Node n = root;
			//System.out.println(root + " n");
			Node nRC = root.rChild;
			//System.out.println(root.rChild + " nrc");
			Node nLC = root.lChild;
			//System.out.println(root.lChild + " nlc");
			Node u = root.parent.parent.lChild;
			//Node ur = root.parent.parent.rChild.rChild;
			//Node ul = root.rChild.lChild;
			
			root = new Node(p.value);
			root.color = true;
			root.parent = gp.parent;
			
			root.lChild = gp;
			root.lChild.color = false;
			root.lChild.parent = root;
			root.rChild = new Node(n.value);
			root.rChild.color = false;
			root.rChild.parent = root;
			
			root.rChild.rChild = nRC;
			root.rChild.rChild.parent = root.rChild;
			root.rChild.lChild = nLC;
			root.rChild.lChild.parent = root.rChild;
			
			root.lChild.rChild = pl;
			if(pl != null){
				root.lChild.rChild.parent = root.lChild;
				}
			root.lChild.lChild = u;
			root.lChild.lChild.parent = root.lChild;
			
			return root.rChild;
		}
        /**
         * Print the tree node: red node wrapped by "<>"; black node by "[]"
         * @return          {@code String} The printed string of the tree node
         */
        @Override public String toString() {
            if (value == null)
                return "";
            return (color == RED) ? "<" + value + ">" : "[" + value + "]";
        }
    }

}
