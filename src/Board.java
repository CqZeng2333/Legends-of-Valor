
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
	 * Return a string of the board
	 */
	@Override
	public String toString() {
		final String ANSI_RESET = "\033[0m";
		final String ANSI_BLUE = "\033[0;34m";
		final String ANSI_YELLOW = "\033[0;33m";
		String board = "";
		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.col; j++) {
				board = board + "+---";
			}
			board = board + "+\n";
			
			for (int j = 0; j < this.col; j++) {
				if (this.board[i][j].hasHero()) {
					board = board + "| "+ ANSI_BLUE + "H" + ANSI_RESET + " ";
				}
				else if (this.board[i][j].getMark() == 'M') {
					board = board + "| "+ ANSI_YELLOW + this.board[i][j].getMark() + ANSI_RESET + " ";
				}
				else {
					board = board + "| " + this.board[i][j].getMark() + " ";
				}
			}
			board = board + "|\n";
		}
		for (int j = 0; j < this.col; j++) {
			board = board + "+---";
		}
		board = board + "+\n";
		return board;
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
