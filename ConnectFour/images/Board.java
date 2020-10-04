import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// ALWAYS KEEP UPDATED AND DOCUMENT AS QUICKLY AS POSSIBLE
// MAIN DEVELOPER:

public class Board extends JFrame implements ActionListener
{
    JFrame frame = new JFrame( "Connect 4 Board" );

    JLabel label = new JLabel( "" );

    private JButton one = new JButton( "one" );

    private JButton two = new JButton( "two" );

    private JButton three = new JButton( "three" );

    private JButton four = new JButton( "four" );

    private JButton five = new JButton( "five" );

    private JButton six = new JButton( "six" );

    private JButton seven = new JButton( "seven" );

    private boolean fillsquare = false;

    private ArrayList<Location> coinsred = new ArrayList<Location>();

    private ArrayList<Location> coinsyellow = new ArrayList<Location>();

    private int count = 0;

    private int count2 = 0;

    private int row = 5;

    private int col;

    private int lastmove;

    // red is 1 & yellow is 2
    static int[][] coins = new int[6][7];

    private boolean computerVsPlayer = false;

    ComputerPlayer computerPlayer = null;

    Container window;


    public Board()
    {
        window = getContentPane();
        FlowLayout fl = new FlowLayout();
        window.setLayout( fl );
    }


    public Board( boolean isComputerVsPlayer )
    {
        this();
        this.computerVsPlayer = isComputerVsPlayer;
    }


    public void makeButtons( Dimension dim )
    {
        JButton[] buttons = new JButton[7];
        buttons[0] = one;
        buttons[1] = two;
        buttons[2] = three;
        buttons[3] = four;
        buttons[4] = five;
        buttons[5] = six;
        buttons[6] = seven;
        for ( JButton x : buttons )
        {
            x.setPreferredSize( dim );
            x.setText( "" );
        }
    }


    public void paint( Graphics g )
    {
        super.paint( g );
        Color clr = new Color( 80, 175, 230 );
        g.setColor( clr );

        Graphics g2 = (Graphics2D)g;

        for ( int y = 0; y < 6; y++ )
        {
            for ( int x = 0; x < 7; x++ )
            {
                g.setColor( clr );
                g2.drawRect( 35 + ( 90 * x ), 90 + ( 85 * y ), 90, 85 );
                g2.fillRect( 35 + ( 90 * x ), 90 + ( 85 * y ), 90, 85 );

            }
            for ( int x = 0; x < 7; x++ )
            {
                g.setColor( Color.WHITE );

                g2.fillOval( 39 + ( 90 * x ), 93 + ( 85 * y ), 82, 78 );
            }
        }
        if ( fillsquare )
        {
            ArrayList<Location> coins = coinsred;
            Color clrRed = new Color( 250, 70, 80 );

            g.setColor( clrRed );
            for ( int z = 0; z < coins.size(); z++ )
            {
                g2.fillOval( 39 + ( 90 * coins.get( z ).getRow() ),
                    93 + ( 85 * coins.get( z ).getCol() ),
                    82,
                    78 );
            }

            coins = coinsyellow;
            Color clrYel = new Color( 250, 250, 50 );
            g.setColor( clrYel );
            for ( int z = 0; z < coins.size(); z++ )
            {
                g2.fillOval( 39 + ( 90 * coins.get( z ).getRow() ),
                    93 + ( 85 * coins.get( z ).getCol() ),
                    82,
                    78 );
            }
        }
        checkForResults();
    }


    public void init()
    {
        window.add( one );
        window.add( two );
        window.add( three );
        window.add( four );
        window.add( five );
        window.add( six );
        window.add( seven );
        one.addActionListener( this );
        two.addActionListener( this );
        three.addActionListener( this );
        four.addActionListener( this );
        five.addActionListener( this );
        six.addActionListener( this );
        seven.addActionListener( this );
    }


    public void actionPerformed( ActionEvent e )
    {
        Object src = e.getSource();
        fillsquare = true;
        if ( src == one )
        {
            col = 0;
            lastmove = 0;
        }
        else if ( src == two )
        {
            col = 1;
            lastmove = 1;
        }
        else if ( src == three )
        {
            col = 2;
            lastmove = 2;
        }
        else if ( src == four )
        {
            col = 3;
            lastmove = 3;
        }
        else if ( src == five )
        {
            col = 4;
            lastmove = 4;
        }
        else if ( src == six )
        {
            col = 5;
            lastmove = 5;
        }
        else
        {
            col = 6;
            lastmove = 6;
        }
        boolean colFull = false;

        while ( ( row >= 0 ) && ( col >= 0 ) && coins[row][col] > 0 )
        {
            row--;
            if ( row < 0 )
            {
                colFull = true;
                break;
            }

        }

        if ( colFull )
        {
            JOptionPane.showMessageDialog( frame,
                "This column is full.\nTry Again!" );
            row = 5;
            count--;
            count2--;
        }
        else
        {
            Location loc = new Location( col, row );
            if ( count % 2 == 0 ) // red
            {
                coins[row][col] = 1;
                coinsred.add( loc );
                row = 5;

            }
            else // yellow
            {
                coins[row][col] = 2;
                coinsyellow.add( loc );
                row = 5;
            }
        }
        count2++;
        count++;
        repaint();

        if ( computerVsPlayer == true && count % 2 != 0 )
        {
            this.computerPlayer.addCoins();
        }
    }


    public void addCoin( int col )
    {
        if ( col == 0 )
        {
            one.doClick();
        }
        else if ( col == 1 )
        {
            two.doClick();
        }
        else if ( col == 2 )
        {
            three.doClick();
        }
        else if ( col == 3 )
        {
            four.doClick();
        }
        else if ( col == 4 )
        {
            five.doClick();
        }
        else if ( col == 5 )
        {
            six.doClick();
        }
        else
        {
            seven.doClick();
        }
    }


    // returns 1 if red has 4 in a row and 2 if yellow has 4 in a row
    // (vertically)
    private int CheckVerticalWinner( int[][] board )
    {

        for ( int col = 0; col < 7; col++ )
        {
            for ( int row = 5; row > 0; row-- )
            {
                int count = 0;

                while ( board[row][col] != 0 && row > 0
                    && board[row][col] == board[row - 1][col] )
                {
                    count++;
                    row--;
                    // System.out.println("VERT "+vCount); //test
                    if ( count == 3 )
                    {
                        return board[row][col];
                    }
                }
            }
        }
        return 0;
    }


    // returns 1 if red has 4 in a row and 2 if yellow has 4 in a row (horiz)
    private int CheckHorizontalWinner( int[][] board )
    {

        for ( int row = 5; row > 0; row-- )
        {
            for ( int col = 0; col < 6; col++ )
            {
                int count = 0;
                while ( board[row][col] != 0 && col < 6
                    && board[row][col] == board[row][col + 1] )
                {
                    count++;
                    col++;
                    // System.out.println("Horiz "+hCount); //test
                    if ( count == 3 )
                    {
                        return board[row][col];
                    }
                }
            }
        }
        return 0;
    }


    private int CheckLDiagonalWinner( int[][] board )
    {

        for ( int row = 5; row > 2; row-- )
        {
            for ( int col = 0; col < 4; col++ )
            {
                int count = 0;
                int tempCol = col;
                int tempRow = row;
                while ( board[row][col] != 0 && col < 6 && row > 0
                    && board[row][col] == board[row - 1][col + 1] )
                {
                    count++;
                    col++;
                    row--;
                    // System.out.println("diag "+ count); //test
                    if ( count == 3 )
                    {
                        return board[row][col];
                    }
                }
                col = tempCol;
                row = tempRow;
            }
        }
        return 0;
    }


    private int CheckRDiagonalWinner( int[][] board )
    {

        for ( int row = 5; row > 0; row-- )
        {
            for ( int col = 6; col > 0; col-- )
            {
                int count = 0;
                int tempCol = col;
                int tempRow = row;
                while ( col > 0 && row > 0 && board[row][col] != 0
                    && board[row][col] == board[row - 1][col - 1] )
                {
                    count++;
                    col--;
                    row--;
                    // System.out.println("diag "+ dCount); //test
                    if ( count == 3 )
                    {
                        return board[row][col];
                    }
                }
                col = tempCol;
                row = tempRow;
            }
        }
        return 0;
    }


    // if all slots are filled and no one has won yet
    public boolean noSlots( int[][] board )
    {
        for ( int col = 0; col < 7; col++ )
        {
            for ( int row = 5; row >= 0; row-- )
            {
                if ( board[row][col] == 0 )
                {
                    return false;
                }
            }

        }
        return true;
    }


    public void updateConsole()
    {
        for ( int r = 0; r < 6; r++ )
        {
            for ( int c = 0; c < 7; c++ )
            {
                System.out.print( coins[r][c] );
            }
            System.out.println();
        }
    }


    public void checkForResults()
    {
        // checks for a vertical 4 in a row
        switch ( CheckVerticalWinner( coins ) )
        {
            case 1:
                JOptionPane.showMessageDialog( frame, "Red Wins!" );
                resetGame();
                break;
            case 2:
                JOptionPane.showMessageDialog( frame, "Yellow Wins!" );
                resetGame();
                break;
            default:
                break;
        }

        // checks for a horizontal 4 in a row
        switch ( CheckHorizontalWinner( coins ) )
        {
            case 1:
                JOptionPane.showMessageDialog( frame, "Red Wins!" );
                resetGame();
                break;
            case 2:
                JOptionPane.showMessageDialog( frame, "Yellow Wins!" );
                resetGame();
                break;
            default:
                break;
        }
        // checks for a diagonal (with a positive slope) 4 in a row
        switch ( CheckLDiagonalWinner( coins ) )
        {
            case 1:
                JOptionPane.showMessageDialog( frame, "Red Wins!" );
                resetGame();
                break;
            case 2:
                JOptionPane.showMessageDialog( frame, "Yellow Wins!" );
                resetGame();
                break;
            default:
                break;
        }

        // checks for diagonal (with a negative slope) 4 in a row
        switch ( CheckRDiagonalWinner( coins ) )
        {
            case 1:
                JOptionPane.showMessageDialog( frame, "Red Wins!" );
                resetGame();
                break;
            case 2:
                JOptionPane.showMessageDialog( frame, "Yellow Wins!" );
                resetGame();
                break;
            default:
                break;
        }

        // checks if whole game board is full in which case, games becomes a
        // draw
        if ( noSlots( coins ) )
        {
            JOptionPane.showMessageDialog( frame, "Draw!" );
            resetGame();
        }
    }


    public void resetGame()
    {
        fillsquare = false;
        coinsred = new ArrayList<Location>();
        coinsyellow = new ArrayList<Location>();
        count = 0;
        count2 = 0;
        row = 5;
        col = 0;
        coins = new int[6][7];
        repaint();
    }


    public int getCount()
    {
        return count2;
    }


    public int getLastMove()
    {
        return lastmove;
    }


    public boolean isColumnFull( int col )
    {
        for ( int r = 0; r < coins.length; r++ )
        {
            if ( coins[r][col] == 0 )
            {
                return false;
            }
        }
        return true;
    }


    public void setComputerPlayer( ComputerPlayer cp )
    {
        this.computerPlayer = cp;
    }


    public static void main( String[] args )
    {
        Board window = new Board();
        window.setTitle( "Connect Four" );
        window.setBounds( 0, 0, 700, 625 );
        window.setDefaultCloseOperation( EXIT_ON_CLOSE );
        window.setResizable( false );

        Dimension dim = new Dimension( 86, 50 );

        window.makeButtons( dim );
        window.init();

        window.setVisible( true );
    }
}