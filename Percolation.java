import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF union;
    private boolean[] isOpenArray;
    private final int SIZE;
    private final int BEGINNING;
    private final int END;
    
    public Percolation(int n) {
        SIZE = n;
        BEGINNING = SIZE * SIZE;
        END = SIZE * SIZE + 1;
        union = new WeightedQuickUnionUF(SIZE * SIZE + 2);
        isOpenArray = new boolean[SIZE * SIZE];
    }    
    
    public void open(int row, int col) {
        if (row < 1 || row > SIZE) 
            throw new IndexOutOfBoundsException(Integer.toString(row));
        if (col < 1 || col > SIZE) 
            throw new IndexOutOfBoundsException(Integer.toString(col));
        isOpenArray[toIndex(row,col)] = true;
       
        connectToVirtualBottomNode(row, col);
        connectToVirtualTopNode(row, col);
        connectTopNode(row, col);
        connectBottomNode(row, col);
        connectRightNode(row, col);
        connectLeftNode(row, col);
        
    }
    
    private void connectTopNode(int row, int col) {
        if (row > 1 && isOpen(row -1, col)) {
            union.union(toIndex(row-1, col), toIndex(row, col));
        }
    }

    private void connectBottomNode(int row, int col) {
        if (row < SIZE && isOpen(row + 1, col)) {
            union.union(toIndex(row+1, col), toIndex(row, col));
        }
    }

    private void connectLeftNode(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            union.union(toIndex(row, col - 1), toIndex(row, col));
        }
    }

    private void connectRightNode(int row, int col) {
        if (col < SIZE && isOpen(row, col + 1)) {
            union.union(toIndex(row, col + 1), toIndex(row, col));
        }
    }
    
    private void connectToVirtualBottomNode(int row, int col) {
        if (row == SIZE) {
            union.union(END, toIndex(row, col));
        }
    }
    
    private void connectToVirtualTopNode(int row, int col) {
        if (row == 1) {
            union.union(BEGINNING, toIndex(row, col));
        }
    }
    
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > SIZE) 
            throw new IndexOutOfBoundsException(Integer.toString(row));
        if (col < 1 || col > SIZE) 
            throw new IndexOutOfBoundsException(Integer.toString(col));
        return isOpenArray[toIndex(row, col)];
    }
    public boolean isFull(int row, int col) {
        if (row < 1 || row > SIZE) 
            throw new IndexOutOfBoundsException(Integer.toString(row));
        if (col < 1 || col > SIZE) 
            throw new IndexOutOfBoundsException(Integer.toString(col));
        return union.connected(toIndex(row,col), BEGINNING);
    }
    public boolean percolates() {
        return union.connected(BEGINNING, END);
    }
    
    private int toIndex(int row, int col) {
        return (row - 1) * SIZE + (col - 1);
    }
}