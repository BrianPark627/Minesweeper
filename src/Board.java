import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JFrame implements ActionListener{

    private int row;
    private int col;
    Block[][] block;
    JButton[][] button;
    Container container;
    Boolean firstClick;
    String secs;

    public Board(int row, int col) {
        super("Minesweeper");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        firstClick = true;
        this.row = row;
        this.col = col;

        container = new Container();
        block = new Block[row+2][col+2];
        button = new JButton[row][col];
        container.setLayout(new GridLayout(row, col, 0, 0));

        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                block[x+1][y+1] = new Block();
                button[x][y] = new JButton();
                button[x][y].addActionListener(this);
                container.add(button[x][y]);
            }
        }
        add(container, BorderLayout.CENTER);
        setVisible(true);

    }

    private void setBomb(int x, int y) {
        int bombs = 10;
        while (bombs != 0) {
            int r = (int) (Math.random() * 9);
            int c = (int) (Math.random() * 9);

            if (block[r+1][c+1].isBomb() == false
                    && (r+1 != x && c+1 != y)
                    && (r+1 != x-1 && c+1 != y-1)
                    && (r+1 != x-1 && c+1 != y)
                    && (r+1 != x-1 && c+1 != y+1)
                    && (r+1 != x && c+1 != y-1)
                    && (r+1 != x && c+1 != y+1)
                    && (r+1 != x+1 && c+1 != y-1)
                    && (r+1 != x+1 && c+1 != y)
                    && (r+1 != x+1 && c+1 != y+1)){
                block[r+1][c+1].setMine();
                bombs--;
            }
        }
    }

    private void setNumbers(){
        for (int x = 1; x < row+1; x++){
            for (int y = 1; y < col + 1; y++) {
                int count = 0;
                if (block[x][y].isBomb())
                    block[x][y].setValue(10);
                else {
                    if (block[x - 1][y - 1] != null && block[x - 1][y - 1].isBomb())
                        count++;
                    if (block[x - 1][y] != null && block[x - 1][y].isBomb())
                        count++;
                    if (block[x - 1][y + 1] != null && block[x - 1][y + 1].isBomb())
                        count++;
                    if (block[x][y - 1] != null && block[x][y - 1].isBomb())
                        count++;
                    if (block[x][y + 1] != null && block[x][y + 1].isBomb())
                        count++;
                    if (block[x + 1][y - 1] != null && block[x + 1][y - 1].isBomb())
                        count++;
                    if (block[x + 1][y] != null && block[x + 1][y].isBomb())
                        count++;
                    if (block[x + 1][y + 1] != null && block[x + 1][y + 1].isBomb())
                        count++;
                    block[x][y].setValue(count);
                }
            }
        }
    }
    public void time(){
        JPanel tools = new JPanel();
        tools.setPreferredSize(new Dimension(600, 50));
        this.add(tools, BorderLayout.SOUTH);
        JLabel time = new JLabel();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int secondsPassed = 0;
            public void run() {
                if (secondsPassed < 999) {
                    ++secondsPassed;
                    String timee = Integer.toString(secondsPassed);
                    time.setText("             " + timee);
                    secs = timee;
                }
            }
        };
        time.setFont(new Font("serif", Font.PLAIN, 24));
        timer.scheduleAtFixedRate(task, 1000, 1000);
        tools.add(time);
    }

    public void clearZeros(ArrayList<Point> remainder) {
        if (remainder.size() == 0){
            return;
        }

        else {
            int x = remainder.get(0).x;
            int y = remainder.get(0).y;
            remainder.remove(0);
            if (x > 0 && y>0 && button[x-1][y-1].isEnabled()) {
                button[x-1][y-1].setText(block[x][y].getStringValue());
                button[x-1][y-1].setEnabled(false);
                if (block[x][y].getValue() == 0) {
                    Point a = new Point(x-1, y-1);
                    remainder.add(a);
                }
            }
            if (x > 0  && button[x-1][y].isEnabled()) {
                button[x-1][y].setText(block[x][y+1].getStringValue());
                button[x-1][y].setEnabled(false);
                if (block[x][y+1].getValue() == 0) {
                    Point a = new Point(x-1, y);
                    remainder.add(a);
                }
            }

            if (x > 0 && y <8  && button[x-1][y+1].isEnabled()) {
                button[x - 1][y + 1].setText(block[x][y+2].getStringValue());
                button[x - 1][y + 1].setEnabled(false);
                if (block[x][y+2].getValue() == 0) {
                    Point a = new Point(x-1, y+1);
                    remainder.add(a);
                }
            }

            if (y >0  && button[x][y-1].isEnabled()) {
                button[x][y - 1].setText(block[x + 1][y].getStringValue());
                button[x][y - 1].setEnabled(false);
                if (block[x + 1][y].getValue() == 0) {
                    Point a = new Point(x, y-1);
                    remainder.add(a);
                }
            }
            if (y < 8 && button[x][y+1].isEnabled()) {
                button[x][y + 1].setText(block[x + 1][y + 2].getStringValue());
                button[x][y + 1].setEnabled(false);
                if (block[x + 1][y + 2].getValue() == 0) {
                    Point a = new Point(x, y+1);
                    remainder.add(a);
                }
            }
            if (y > 0 && x < 8  && button[x+1][y-1].isEnabled()) {
                button[x + 1][y - 1].setText(block[x + 2][y].getStringValue());
                button[x + 1][y - 1].setEnabled(false);
                if (block[x + 2][y].getValue() == 0) {
                    Point a = new Point(x+1, y-1);
                    remainder.add(a);
                }
            }

            if (x < 8  && button[x+1][y].isEnabled()) {
                button[x + 1][y].setText(block[x + 2][y + 1].getStringValue());
                button[x + 1][y].setEnabled(false);
                if (block[x + 2][y + 1].getValue() == 0) {
                    Point a = new Point(x+1, y);
                    remainder.add(a);
                }
            }

            if (x <8 && y < 8  && button[x+1][y+1].isEnabled()) {
                button[x + 1][y + 1].setText(block[x + 2][y + 2]
                        .getStringValue());
                button[x + 1][y + 1].setEnabled(false);
                if (block[x + 2][y + 2].getValue() == 0) {
                    Point a = new Point(x+1, y+1);
                    remainder.add(a);
                }
            }
            clearZeros(remainder);
        }
    }


    public void actionPerformed(ActionEvent e){
        for (int x = 0; x < button.length; x++){
            for (int y = 0; y< button[0].length; y++){
                if (firstClick && e.getSource().equals(button[x][y])){
                    firstClick = false;
                    setBomb(x+1,y+1);
                    setNumbers();
                    time();
                    button[x][y].setEnabled(false);
                    Point a = new Point(x,y);
                    ArrayList<Point> remainder = new ArrayList<Point>();
                    remainder.add(a);
                    clearZeros(remainder);
                }
                else if (e.getSource().equals(button[x][y])){
                    if (block[x+1][y+1].isBomb()){
                        Image bomb = new ImageIcon(this.getClass().getResource("images.jpg")).getImage();
                        for (int a = 0; a < 9; a++){
                            for (int b = 0; b < 9; b++){
                                if (block[a+1][b+1].isBomb())
                                    button[a][b].setIcon(new ImageIcon(bomb));
                            }
                        }
                        lose();
                    }
                    else if (block[x+1][y+1].getValue()==0){
                        button[x][y].setText(block[x+1][y+1].getStringValue());
                        button[x][y].setEnabled(false);
                        Point a = new Point(x,y);
                        ArrayList<Point> remainder = new ArrayList<Point>();
                        remainder.add(a);
                        clearZeros(remainder);
                        win();
                    }
                    else{
                        button[x][y].setText(block[x+1][y+1].getStringValue());
                        button[x][y].setEnabled(false);
                        win();
                    }
                }
            }
        }
    }

    public void win(){
        boolean completed = true;
        for (int x = 0; x < button.length; x++){
            for (int y =0; y < button[0].length; y++){
                if(block[x+1][y+1].isBomb()==false && button[x][y].isEnabled())
                    completed= false;
            }
        }
        if(completed){
            JOptionPane.showMessageDialog(this, "You win!  " +  "Time completed in: " + secs);
            System.exit(0);
        }
    }

    public void lose(){
        for(int x = 0; x < 9; x++){
            for ( int y= 0; y <9; y++){
                button[x][y].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(this, "You Lose!");
        System.exit(0);
    }
    
    public static void main (String[] args){
        Board board = new Board(9,9);
    }
}

