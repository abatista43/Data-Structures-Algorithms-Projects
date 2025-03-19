import java.util.Stack;

public class PercolationDFS extends PercolationDefault {
   
    public PercolationDFS(int size) {
        super(size);
    }

    @Override
    public void search(int row, int col) {
        if (! inBounds(row,col)) return;
        if (isFull(row, col) || !isOpen(row, col)){
			    return;
		    }

        int[] rowDelta = {-1,1,0,0};
        int[] colDelta = {0,0,-1,1};
        Stack<int[]> stack = new Stack<>();
        myGrid[row][col] = FULL;
        stack.push(new int[]{row,col});
        while (stack.size() != 0){
            int[] coords = stack.pop();
            for(int k=0; k < rowDelta.length; k++) {
                    row = coords[0] + rowDelta[k];
                    col = coords[1] + colDelta[k];
                    if (inBounds(row, col) && myGrid[row][col] == OPEN) {
                        stack.push(new int[]{row,col});
                        myGrid[row][col] = FULL;
                    }
            }    
        }
    }
}

// if (inBounds(row, col) && isOpen(row, col)) {