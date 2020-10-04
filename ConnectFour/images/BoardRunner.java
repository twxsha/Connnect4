import java.awt.*;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class BoardRunner
{

    static class vsPlayer implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            Board window = new Board();
            window.setTitle( "Connect Four" );
            window.setBounds( 0, 0, 700, 625 );
            window.setResizable( false );
            Dimension dim = new Dimension( 86, 50 );
            window.makeButtons( dim );
            window.init();
            window.setVisible( true );
        }
    }


    static class vsComputer implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            ComputerPlayer player = new ComputerPlayer();
        }
    }


    public static void main( String[] args ) throws IOException
    {
        Dimension dim = new Dimension( 300, 150 );

        JFrame frame = new JFrame( "Connect Four" );
        frame.setVisible( true );
        frame.setBounds( 0, 0, 700, 625 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JPanel panel = new JPanel();
        panel.setBackground( Color.BLACK );

        // Title: connect 4
        JLabel label2 = new JLabel( "Connect 4!" );
        label2.setFont( new Font( "Serif Italic", 4, 80 ) );
        label2.setForeground( Color.white );
        panel.add( label2 );

        // adds message to select a mode
        JLabel label = new JLabel( "Select a Mode:" );
        label.setFont( new Font( "Serif Italic", 1, 55 ) );
        label.setForeground( Color.lightGray );
        panel.add( label );
        frame.add( panel );

        // button for player vs player mode
        JButton button = new JButton( "Player Vs. Player" );
        button.setPreferredSize( dim );
        panel.add( button );
        button.addActionListener( new vsPlayer() );

        // button for player vs. computer mode
        JButton button2 = new JButton( "Player Vs. Computer" );
        button2.setPreferredSize( dim );
        panel.add( button2 );
        button2.addActionListener( new vsComputer() );

        // adds instructions
        JLabel labelxx = new JLabel(
            "Instructions: Click the buttons at the "
                + "top to drop a coin into the respective column. " );
        JLabel labelx1 = new JLabel(
            "If you get 4 coins in a row horizontally, "
                + "vertically, or diaonally, you win." );

        labelxx.setFont( new Font( "Serif Italic", 1, 15 ) );
        labelxx.setForeground( Color.WHITE );
        labelx1.setFont( new Font( "Serif Italic", 1, 15 ) );
        labelx1.setForeground( Color.WHITE );
        panel.add( labelxx );
        panel.add( labelx1 );

        frame.add( panel );
    }

}
