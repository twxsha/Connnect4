
public class Location
{
    private int row, col;

    Board board = new Board();


    /**
     * 
     * @param r
     *            row
     * @param c
     *            col
     */
    public Location( int r, int c )
    {
        row = r;
        col = c;
    }


    /**
     * 
     * Accessing method
     * 
     * @return row
     */
    public int getRow()
    {
        return row;
    }


    /**
     * 
     * Accessing method
     * 
     * @return column
     */
    public int getCol()
    {
        return col;
    }


    /**
     * 
     * Checks if Location exists, based on if the location can exist by the
     * constraints of the board.
     * 
     * @return true or false
     */
    public boolean exists()
    {
        if ( row >= 0 && row <= 7 && col >= 0 && col <= 6 )
        {
            return true;
        }
        return false;
    }


    /**
     * 
     * Checks if the location is occupied by a player's coin.
     * 
     * @return true or false
     */
    public boolean taken()
    {
        if ( board.coins[row][col] != 0 )
        {
            return true;
        }
        return false;
    }

}