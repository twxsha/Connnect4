import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;


public class ComputerPlayer implements ActionListener
{
    public Board windowx;

    boolean first = true;


    public ComputerPlayer()
    {
        windowx = new Board( true );
        windowx.resetGame();
        windowx.setComputerPlayer( this );
        windowx.setTitle( "Connect Four" );
        windowx.setBounds( 0, 0, 700, 625 );
        windowx.setResizable( false );

        Dimension dim = new Dimension( 86, 50 );
        windowx.makeButtons( dim );
        windowx.init();

        windowx.setVisible( true );
    }

    public void addCoins()
    {

        System.out.println( "canWin = " + canWin() );
        if ( canWin() != -1 && !windowx.isColumnFull( canWin() ) )
        {
            windowx.addCoin( canWin() );
        }
        
        else if ( checkHorizontal() == -1 && checkVertical() == -1
            && checkRDiag() == -1 && checkLDiag() == -1 )
        {
            int coin = 6 - windowx.getLastMove();
            while ( coin > 0 && windowx.isColumnFull( coin ) )
            {
                coin--;
            }
            windowx.addCoin( coin );
        }
        else if ( checkVertical() != -1 && !windowx.isColumnFull( checkVertical() ) )
        {
            windowx.addCoin( checkVertical() );
        }
        else if ( checkRDiag() != -1 && !windowx.isColumnFull( checkRDiag() ) )
        {
            windowx.addCoin( checkRDiag() );
        }
        else if ( checkLDiag() != -1 && !windowx.isColumnFull( checkLDiag() ) )
        {
            windowx.addCoin( checkLDiag() );
        }
        else if ( checkHorizontal() != -1 )
        {
            windowx.addCoin( checkHorizontal() );
        }
        else if ( !windowx.isColumnFull( checkVertical() ) )
        {
            windowx.addCoin( checkVertical() );
        }
    }


    private int checkVertical()
    {
        for( int col = 0; col < 7; col++) 
        {
            for( int row = 5; row > 0; row-- ) 
            {
                int count = 0;

                while ( windowx.coins[row][col]!= 0  && row > 0 &&
                    windowx.coins[row][col] == windowx.coins[row-1][col] ) 
                {
                    count++;
                    row--;
                    if (count == 2 ) 
                    {
                        if( windowx.coins[row-1][col] == 2 )
                        {
                            print( row-3, col );
                            break;
                        }
                        print( col, row );
                        return col;
                    }
                }
            }
        }
        return -1;
    }

    public int checkHorizontal()
    {
        for ( int row = 5; row > 0; row-- )
        {
            for ( int col = 0; col < 6; col++ )
            {
                int count = 0;
                while ( windowx.coins[row][col] != 0 && col < 6
                    && windowx.coins[row][col] == windowx.coins[row][col
                        + 1] )
                {
                    count++;
                    col++;
                    if ( count == 2 )
                    {
                        print( row, col + 1 );
                        if ( col < 3 || windowx.coins[row][col - 3] == 2 )
                        {
                            break;
                        }
                        if ( windowx.coins[row][col - 3] == 0 )
                        {
                            return col - 3;
                        }
                    }
                    else if ( count == 1 )
                    {
                        print( row, col + 1 );
                        if ( col >= 3 && windowx.coins[row][col - 2] == 0
                            && windowx.coins[row][col - 3] == 1 )
                        {
                            return col - 2;
                        }

                        if ( col == 6 || windowx.coins[row][col + 1] == 2 )
                        {
                            break;
                        }
                        if ( windowx.coins[row][col + 1] == 0 )
                        {
                            return col + 1;
                        }
                    }
                }
            }
        }
        return -1;
    }


    public int checkLDiag()
    {

        for ( int row = 5; row > 2; row-- )
        {
            for ( int col = 0; col < 4; col++ )
            {
                int count = 0;
                int tempCol = col;
                int tempRow = row;
                while ( windowx.coins[row][col] != 0 && col < 6 && row > 0
                    && windowx.coins[row][col] == windowx.coins[row
                        - 1][col + 1] )
                {
                    count++;
                    col++;
                    row--;
                    // System.out.println("diag "+ count); //test
                    if ( count == 2 )
                    {
                        print( row, col + 1 );
                        if ( col < 7
                            && windowx.coins[row + 1][col + 1] == 0
                            && windowx.coins[row][col + 1] > 0 )
                        {
                            return col + 1;
                        }
                    }
                }
                col = tempCol;
                row = tempRow;
            }
        }
        return -1;
    }


    public int checkRDiag()
    {

        for ( int row = 5; row > 0; row-- )
        {
            for ( int col = 6; col > 0; col-- )
            {
                int count = 0;
                int tempCol = col;
                int tempRow = row;
                while ( col > 0 && row > 0 && windowx.coins[row][col] != 0
                    && windowx.coins[row][col] == windowx.coins[row
                        - 1][col - 1] )
                {
                    count++;
                    col--;
                    row--;
                    // System.out.println("diag "+ dCount); //test
                    if ( count == 2 )
                    {
                        print( row, col - 1 );
                        if ( col > 0
                            && windowx.coins[row - 1][col - 1] == 0
                            && windowx.coins[row][col - 1] > 0 )
                        {
                            System.out.println( col - 1 );
                            return col - 1;
                        }
                    }
                }
                col = tempCol;
                row = tempRow;
            }
        }
        return -1;
    }


    public int canWin()
    {
        for ( int col = 0; col < 7; col++ )
        {
            for ( int row = 5; row > 0; row-- )
            {
                int count = 0;

                while ( ( row > 0 && windowx.coins[row][col] == 2 )
                    && windowx.coins[row][col] == windowx.coins[row
                        - 1][col] )
                {
                    count++;
                    row--;
                    if ( count == 2 )
                    {
                        return col;
                    }
                }
            }
        }
        return -1;
    }


    @SuppressWarnings("static-access")
    public void print( int x, int y )
    {
        for ( int i = 0; i < windowx.coins.length; i++ )
        {
            for ( int j = 0; j < windowx.coins[0].length; j++ )
            {
                System.out.print( windowx.coins[i][j] + " " );
            }
            System.out.println();
        }
        System.out.println();
        System.out.println( x + " " + y );
        System.out.println();
    }

    public static void main( String[] args )
    {
        ComputerPlayer player = new ComputerPlayer();
        System.out.println( "ComputerPlayer() created..." );
        player.addCoins();

    }


    @Override
    public void actionPerformed( ActionEvent e )
    {
        addCoins();
    }

}
