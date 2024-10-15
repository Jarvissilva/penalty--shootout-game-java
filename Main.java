import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PenaltyShootoutGame extends JFrame {
    private JLabel goalkeeperLabel;
    private JLabel resultLabel;
    private JLabel ballLabel;
    private int goalkeeperPosition;
    private JButton shootLeftButton, shootCenterButton, shootRightButton, playAgainButton;

    public PenaltyShootoutGame() {
        setTitle("Penalty Shootout Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        JLabel goalBackground = new JLabel(new ImageIcon("img/goal.png"));
        goalBackground.setLayout(null);
        add(goalBackground, BorderLayout.CENTER);

        goalkeeperLabel = new JLabel(new ImageIcon("img/goalkeeper.png"));
        goalkeeperLabel.setBounds(250, 70, 150, 300);
        goalBackground.add(goalkeeperLabel);

        resultLabel = new JLabel("Take a shot!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 20));
        resultLabel.setForeground(Color.BLUE);
        add(resultLabel, BorderLayout.NORTH);

        ImageIcon ballIcon = new ImageIcon("img/ball.png"); // Ensure this image has a transparent background
        Image ballImage = ballIcon.getImage();
        Image scaledBallImage = ballImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ballLabel = new JLabel(new ImageIcon(scaledBallImage));
        ballLabel.setBounds(-100, -100, 50, 50);
        ballLabel.setOpaque(false); // Ensure the ball label does not have a background
        goalBackground.add(ballLabel, Integer.valueOf(2)); // Set Z-index higher than goalkeeper

        JPanel shootPanel = new JPanel();
        shootLeftButton = new JButton("Shoot Left");
        shootCenterButton = new JButton("Shoot Center");
        shootRightButton = new JButton("Shoot Right");

        shootPanel.add(shootLeftButton);
        shootPanel.add(shootCenterButton);
        shootPanel.add(shootRightButton);

        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> resetGame());
        playAgainButton.setVisible(false);
        shootPanel.add(playAgainButton);

        add(shootPanel, BorderLayout.SOUTH);

        shootLeftButton.addActionListener(new ShootListener(0));
        shootCenterButton.addActionListener(new ShootListener(1));
        shootRightButton.addActionListener(new ShootListener(2));

        goalkeeperPosition = 1;
    }

    private void moveGoalkeeper() {
        Random random = new Random();
        goalkeeperPosition = random.nextInt(3);

        switch (goalkeeperPosition) {
            case 0:
                goalkeeperLabel.setBounds(100, 70, 150, 300);
                break;
            case 1:
                goalkeeperLabel.setBounds(250, 70, 150, 300);
                break;
            case 2:
                goalkeeperLabel.setBounds(400, 70, 150, 300);
                break;
        }
        goalkeeperLabel.repaint();
    }

    private class ShootListener implements ActionListener {
        private int playerPosition;

        public ShootListener(int position) {
            this.playerPosition = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            shootLeftButton.setEnabled(false);
            shootCenterButton.setEnabled(false);
            shootRightButton.setEnabled(false);

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayBall(playerPosition);
                    if (playerPosition == goalkeeperPosition) {
                        resultLabel.setText("SAVE! The goalkeeper saved your shot.");
                    } else {
                        resultLabel.setText("GOAL! You scored.");
                    }
                    playAgainButton.setVisible(true);
                }
            });
            timer.setRepeats(false);
            timer.start();

            moveGoalkeeper();
        }
    }

    private void resetGame() {
        resultLabel.setText("Take a shot!");
        playAgainButton.setVisible(false);

        shootLeftButton.setEnabled(true);
        shootCenterButton.setEnabled(true);
        shootRightButton.setEnabled(true);

        ballLabel.setBounds(-100, -100, 50, 50); 
        ballLabel.repaint();

        goalkeeperLabel.setBounds(250, 70, 150, 300); 
        goalkeeperLabel.repaint();  
    }

    private void displayBall(int playerPosition) {
        switch (playerPosition) {
            case 0:
                ballLabel.setBounds(100, 200, 50, 50);
                break;
            case 1:
                ballLabel.setBounds(250, 200, 50, 50);
                break;
            case 2:
                ballLabel.setBounds(400, 200, 50, 50);
                break;
        }
        ballLabel.repaint();
    }

    public static void main(String[] args) {
        PenaltyShootoutGame game = new PenaltyShootoutGame();
        game.setVisible(true);
    }
}
