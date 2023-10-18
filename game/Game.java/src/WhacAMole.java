import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650; //50px for the text panel on top

    JFrame frame = new JFrame("Mario: Whac A Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();
    Timer setMolTimer;
    Timer setPlantTimer;
    int score = 0;

    WhacAMole() {
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("AR One Sans", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        // boarPanel.setBackground(Color.black);
        frame.add(boardPanel);

        //import plant image
        //plantIcon = new ImageIcon(getClass().getResource("./piranha.png"));
        Image plantImage = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        //Resize the plant image into the game
        plantIcon = new ImageIcon(plantImage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImage = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        //Resize the Mole image into the game
        moleIcon = new ImageIcon(moleImage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        score = 0;
        for (int i =0; i <9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            //tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile == currMoleTile) {
                        score += 10;
                        //count the player score
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                    else if (tile == currPlantTile) {
                        //Game over the game
                        textLabel.setText("Game Over: " + Integer.toString(score));
                        setMolTimer.stop();
                        setPlantTimer.stop();
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                        }
                    }
        }});
        }

        setMolTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                //if tile occupied by plant
                if (currPlantTile == tile) return;

                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currPlantTile != null) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                if(currMoleTile == tile) return;

                currPlantTile = tile;
                currPlantTile.setIcon(plantIcon);
            }
        });

         setMolTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);
    }
}