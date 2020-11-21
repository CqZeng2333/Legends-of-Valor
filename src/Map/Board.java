package Map;

public abstract class Board {
	protected Tile[][] board;
	private int col;
	private int row;
	
	/*
	 * Create a blank board
	 */
	public Board() {
		this.col = this.row = 0;
	}
	/*
	 * Create a triangular board
	 */
	public Board(int row, int col) {
		board = new Tile[row][col];
		this.row = row;
		this.col = col;
	}
	/*
	 * Create a square board
	 */
	public Board(int size) {
		board = new Tile[size][size];
		this.row = size;
		this.col = size;
	}
	
	/*
	 * Get the row of board
	 */
	public int getRow() {
		return row;
	}
	/*
	 * Get the column of board
	 */
	public int getCol() {
		return col;
	}
	
	/*
	 * PreCondition:  index i, j for the position of a tile
	 * PostCondition: the symbol of the corresponding tile
	 */
	public Tile getAGrid(int i, int j) {
		return this.board[i][j];
	}
	/*
	 * PreCondition:  index i, j for the position of a tile, the symbol of the tile to put in this grid
	 * PostCondition: success 0, not success -1 because of error position
	 */
	public int setMark(int i, int j, char mark) {
		if (i >= this.row || j >= this.col) {
			return -1;
		}
		board[i][j].setMark(mark);;
		return 0;
	}
}
