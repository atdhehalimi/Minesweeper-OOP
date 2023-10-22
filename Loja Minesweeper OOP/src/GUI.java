import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame {

    Date startDate = new Date();                        // perdoret qe te mat kohen       
    Date endDate;                                       //perdoret ne animimin e mesazhit te fitores nga poshte larte
    public boolean resetter = false;                    // perdoret per percaktimin e riperseritjes se lojes kur shtypim smileyn

    // hapesira dhe koordinata x dhe y e pixeleve
    int space = 5;                                      
    public int mx;
    public int my;
    // numri i kutive fqinje me mina
    int nei = 0;   
    
    
    Random rand = new Random();                         // perdoret per percaktimin e rastesishem te minave
// koordinatat dhe gjatesia e smiley-t
    public int smileyX = 605;
    public int smileyY = 5;
    public int smileyCenterX = smileyX + 35;
    public int smileyCenterY = smileyY + 35;

    // koordinatat e timerit

    public int timeX = 1130;
    public int timeY = 5;
    // sekondat qe perdoren ne timer
    public int sec = 0;
// koordinatatt dhe lloji i mesazhit ne rast fitoreje apo humbjeje
    public int vicMesX = 750;
    public int vicMesY = -50;
    String vicMes = "";
// koordinatat dhe gjatesia e flamurit

    public int flaggerX = 445;
    public int flaggerY = 5;
    public int flaggerCenterX = flaggerX + 35;
    public int flaggerCenterY = flaggerY + 35;
    //tregon a e kemi shtypur flamurin apo jo
    public boolean flagger = false;

    // boolean per ndryshimin e qendrimit te smiley-t
    public boolean happiness = true;
//boolean qe tregon gjendjen e lojes
    public boolean victory = false;
    public boolean defeat = false;
// arrayt qe perdoren: numri i minave, numri i fqinjeve me mina, boolean qe tregon nese paketa eshte hapur,boolean qe tregon nese paketa ka flamur
    int[][] mines = new int[16][9];
    int[][] neighbours = new int[16][9];
    boolean[][] revealed = new boolean[16][9];
    boolean[][] flagged = new boolean[16][9];

    public GUI() {
       // Paraqitja e dritares
        this.setTitle("Minesweeper");
        this.setSize(1286, 829);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        for (int i = 0; i < 16; i++) {

            for (int j = 0; j < 9; j++) {
//me ane te gjenerimit te rastesishem percaktohen minat ne paketa te bdryshme
                if (rand.nextInt(100) < 20) {
                    mines[i][j] = 1;
                } else {
                    mines[i][j] = 0;
                }
                revealed[i][j] = false; // tregon paketat e hapura apo te mbyllura nese do te ishte true te gjithe paketat do te hapeshin, paketat hapen kur klikohen
            }

        } 
// Kater for loop qe perdoren per kontrollim e minave 8 paketa fqinje dhe qe tregon numrin e pergjithshem te tyre 
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                nei = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {
                        if (!(m == i && n == j)) {
                            if (isN(i, j, m, n) == true) {
                                nei++;
                            }
                        }

                    }
                }
                neighbours[i][j] = nei;
            }
        }
// Inicializimi i klaseve Board,move dhe click
        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

    }

    public class Board extends JPanel {
// board perdoret per krijimin e dritares
        public void paintComponent(Graphics g) {
            g.setColor(Color.black);
            g.fillRect(0, 0, 1280, 800);
            
            // konstruktimi i paketave te cilat marrin ngjyre te hirit fillimisht te bardhe nese hapen dhe te kuqe nese paket a ka bombe
            for (int i = 0; i < 16; i++) {

                for (int j = 0; j < 9; j++) {
                    g.setColor(Color.gray);
//if(mines[i][j]==1){g.setColor(Color.red);}
                    if (revealed[i][j] == true) {
                        g.setColor(Color.white);
                        if (mines[i][j] == 1) {
                            g.setColor(Color.red);
                        }
                    }

                    // ne momentin qe e levizim mausin ne nje pakete te caktuar dhe nuk e klikojme ajo mer rngjyre te kuqe
                    if (mx >= space + i * 80 && mx < space + i * 80 + 80 - 2 * space && my >= space + 80 + j * 80 + 26 && my <= space + 80 + j * 80 + 80 - 2 * space) {
                        g.setColor(Color.red);
                    }
                    
                    // konstrukton paketat
                    g.fillRect(space + i * 80, space + 80 + j * 80, 80 - 2 * space, 80 - 2 * space);

                    // poshte paraqiten variantet e hapjes se paketes
                    if (revealed[i][j] == true) {
                        g.setColor(Color.black);
                        //paketa nuk ka mina
                        // ne baze te numrit te bombave ne paketat fqinje vendoset numri i bombave me nje ngjyre te vecante
                        if (mines[i][j] == 0 && neighbours[i][j] != 0) {   
                            if (neighbours[i][j] == 1) {
                                g.setColor(Color.blue);
                            } else if (neighbours[i][j] == 2) {
                                g.setColor(Color.green);
                            } else if (neighbours[i][j] == 3) {
                                g.setColor(Color.red);
                            } else if (neighbours[i][j] == 4) {
                                g.setColor(new Color(0, 0, 128));
                            } else if (neighbours[i][j] == 5) {
                                g.setColor(new Color(178, 34, 34));
                            } else if (neighbours[i][j] == 6) {
                                g.setColor(new Color(72, 209, 204));
                            } else if (neighbours[i][j] == 7) {
                                g.setColor(Color.black);
                            } else if (neighbours[i][j] == 8) {
                                g.setColor(Color.darkGray);
                            }
                            
                            //paraqitja e numrit te fqinjeve me mina me ane te tekstit
                            g.setFont(new Font("Tahoma", Font.BOLD, 40));
                            g.drawString(Integer.toString(neighbours[i][j]), i * 80 + 27, j * 80 + 80 + 55);
                            
                        //paketa ka mina dhe vizatimi i mines
                        } else if (mines[i][j] == 1) {
                            g.fillRect(i * 80 + 10 + 20, j * 80 + 80 + 20, 20, 40);
                            g.fillRect(i * 80 + 20, j * 80 + 80 + 30, 40, 20);
                            g.fillRect(i * 80 + 5 + 20, j * 80 + 80 + 25, 30, 30);
                            g.fillRect(i * 80 + 38, j * 80 + 80 + 15, 4, 50);
                            g.fillRect(i * 80 + 15, j * 80 + 80 + 38, 50, 4);

                        }

                    }

                    // zhvendosja e flamurave ne kuti te deshiruara
                    if (flagged[i][j] == true) {
                        g.setColor(Color.darkGray);
                        g.fillRect(i * 80 + 32 + 5, j * 80 + 80 + 15, 5, 40);
                        g.fillRect(i * 80 + 20 + 5, j * 80 + 80 + 50, 30, 10);
                        g.setColor(Color.red);
                        g.fillRect(i * 80 + 16 + 5, j * 80 + 80 + 15, 20, 15);
                        g.setColor(Color.darkGray);
                        g.drawRect(i * 80 + 16 + 5, j * 80 + 80 + 15, 20, 15);
                        g.drawRect(i * 80 + 17 + 5, j * 80 + 80 + 16, 18, 13);
                        g.drawRect(i * 80 + 18 + 5, j * 80 + 80 + 17, 16, 11);

                    }

                }
            }
// krijimi i smiley i cili ndryshon ne rast te humbjes
            g.setColor(Color.yellow);
            g.fillOval(smileyX, smileyY, 70, 70);
            g.setColor(Color.BLACK);
            g.fillOval(smileyX + 15, smileyY + 20, 10, 10);
            g.fillOval(smileyX + 45, smileyY + 20, 10, 10);
            //perdoret gjate tere lojes
            if (happiness == true) {
                g.fillRect(smileyX + 20, smileyY + 50, 30, 5);
                g.fillRect(smileyX + 17, smileyY + 45, 5, 5);
                g.fillRect(smileyX + 48, smileyY + 45, 5, 5);
            //perdoret ne rast humbjeje
            } else {
                g.fillRect(smileyX + 20, smileyY + 45, 30, 5);
                g.fillRect(smileyX + 17, smileyY + 50, 5, 5);
                g.fillRect(smileyX + 48, smileyY + 50, 5, 5);
            }

            // Vizatimi i flamurit
            g.setColor(Color.darkGray);
            g.fillRect(flaggerX + 32, flaggerY + 15, 5, 40);
            g.fillRect(flaggerX + 20, flaggerY + 50, 30, 10);
            g.setColor(Color.red);
            g.fillRect(flaggerX + 16, flaggerY + 15, 20, 15);
            g.setColor(Color.darkGray);
            g.drawRect(flaggerX + 16, flaggerY + 15, 20, 15);
            g.drawRect(flaggerX + 17, flaggerY + 16, 18, 13);
            g.drawRect(flaggerX + 18, flaggerY + 17, 16, 11);
            // ngjyra e rrethit qe mbeshtjelle flamurin ndryshon ne rast se metoda e flamurit eshte akivizuar
            if (flagger == true) {
                g.setColor(Color.red);
            }
            g.drawOval(flaggerX, flaggerY, 70, 70);
            g.drawOval(flaggerX + 1, flaggerY + 1, 68, 68);
            g.drawOval(flaggerX + 2, flaggerY + 2, 66, 66);

            //konstruktimi i timerit
            
            g.setColor(Color.BLACK);
            g.fillRect(timeX, timeY, 140, 70);
            // koha leviz ne menyre normale derisa lojtari humbet apo fiton
            if (defeat == false && victory == false) {
                sec = (int) ((new Date().getTime() - startDate.getTime()) / 1000);
            }
            // koha ndalet kur te arrije 999 sekonda
            if (sec > 999) {
                sec = 999;
            }
            // koha normale eshte me ngjyre te bardhe por ne rast fitoreje apo humbjeje kthehet ne te gjelbert ose te kuqe    
            g.setColor(Color.WHITE);
            if (victory == true) {
                g.setColor(Color.GREEN);
            } else if (defeat == true) {
                g.setColor(Color.red);
            }

            g.setFont(new Font("Tahoma", Font.BOLD, 80));
            //largimi i numrit nga e djathta nese eshte me pak se 10 apo 100
            if (sec < 10) {
                g.drawString("00" + Integer.toString(sec), timeX, timeY + 65);
            } else if (sec < 100) {
                g.drawString("0" + Integer.toString(sec), timeX, timeY + 65);
            } else {
                g.drawString(Integer.toString(sec), timeX, timeY + 65);

            }
            


            // mesahi qe del ne rast fitoreje apo humbjeje kthehet ne te gjelbert ose te kuqe 

            if (victory == true) {
                g.setColor(Color.GREEN);
                vicMes = "Fitove";

            } else if (defeat == true) {
                g.setColor(Color.red);
                vicMes = "Humbe";

            }
            // forme e animimit te mesazhit me ane te ndryshimit te koordinates y duke perdorur ndryshimin kohor
            if (victory == true || defeat == true) {
                vicMesY = -50 + (int) (new Date().getTime() - endDate.getTime()) / 10;
                if (vicMesY > 70) {
                    vicMesY = 70;
                }

                g.drawString(vicMes, vicMesX, vicMesY);
            }
            repaint();
        }

    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent me) {

        }

        @Override
        public void mouseMoved(MouseEvent me) {
            // ndryshon koordinatat e x dhe y ne baze te levizjes se mausit
            mx = me.getX();
            my = me.getY();
            //     System.out.println("Mouse x position "+mx+" mouse y position "+my);
            repaint();
            repaint();

        }

    }

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            // koordinatat perseriten per saktesi
            mx = me.getX();
            my = me.getY();
            
            if (defeat == false) { // ne rast humbjeje nuk mund e hapen paketa tjera

                if (inBoxX() != -1 && inBoxY() != -1) { // kur klikon ne kuti
                    System.out.println("Mausi klikoi ne [" + inBoxX() + "][" + inBoxY() + "]" + " kutia ka " + neighbours[inBoxX()][inBoxY()] + " fqinj ");

                    // vendosja e flamureve ne paketat e deshiruara dhe heqja e yre ne rast gabimi
                    if (flagger == true && revealed[inBoxX()][inBoxY()] == false) {
                        if (flagged[inBoxX()][inBoxY()] == false) {
                            flagged[inBoxX()][inBoxY()] = true;
                        } else {
                            flagged[inBoxX()][inBoxY()] = false;
                        }

                    } else {
                        if (flagged[inBoxX()][inBoxY()] == false) {
                            revealed[inBoxX()][inBoxY()] = true;
                        }
                    }

                } else {
                    System.out.println("Mausi nuk klikoi ne tabele");
                }

            }
            // ne rast te klikimit te smiley-t restartohet loja
            if (inSmiley() == true) {
                resetAll();
            }
            // aktivizimi apo deaktivizimi i flamureve
            if (inFlagger() == true) {
                if (flagger == false) {
                    flagger = true;
                    System.out.println("Flamuri u aktivizua");

                } else {
                    flagger = false;
                    System.out.println("Flamuri u deaktivizua");
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }

    }

    public void checkVictoryStatus() {
//perdoret per transmetimin e mesazhit perfundimtar
        
        if (defeat == false) {//kontrollimi i humbjes 
            for (int i = 0; i < 16; i++) {

                for (int j = 0; j < 9; j++) {
                    if (mines[i][j] == 1 && revealed[i][j] == true) {
                        defeat = true;
                        happiness = false;
                        endDate = new Date();
                    }

                }
            }
        }
        //kontrollimi i fitores
        if (totalBoxesRevealed() >= 144 - totalMines() && victory == false) {
            victory = true;
            endDate = new Date();

        }

    }
//metode qe kthen te numrin e pergjithem te minave
    public int totalMines() {
        int total = 0;
        for (int i = 0; i < 16; i++) {

            for (int j = 0; j < 9; j++) {
                if (mines[i][j] == 1) {
                    total++;
                }
            }

        }

        return total;

    }
//metode qe kthen te numrin e pergjithem kutive te hapura

    public int totalBoxesRevealed() {
        int total = 0;
        for (int i = 0; i < 16; i++) {

            for (int j = 0; j < 9; j++) {
                if (revealed[i][j] == true) {
                    total++;
                }
            }

        }

        return total;

    }
    //metode restarton lojen me ane te klikimit te smileyt
    public void resetAll() {
       // resetimi i variablave ne forma fillestare 
        flagger = false;
        resetter = true;
        vicMesY = -50;
        vicMes = "";
        startDate = new Date();
        happiness = true;
        victory = false;
        defeat = false;
        // mbyll te gjithe paketat dhe heq flamurat
        for (int i = 0; i < 16; i++) {

            for (int j = 0; j < 9; j++) {

                if (rand.nextInt(100) < 20) {
                    mines[i][j] = 1;
                } else {
                    mines[i][j] = 0;
                }
                revealed[i][j] = false; // tregon paketat e hapura apo te mbyllura nese do te ishte true te gjithe paketat do te hapeshin, paketat hapen kur klikohen
                flagged[i][j] = false;
            }

        }
// numri i fqinjeve
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                nei = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {
                        if (!(m == i && n == j)) {
                            if (isN(i, j, m, n) == true) {
                                nei++;
                            }
                        }

                    }
                }
                neighbours[i][j] = nei;
            }
        }
//variabel qe tregon se resetimi eshte kryer
        resetter = false;
    }
//metode qe perdoret per te treguar nese kemi klikuar smileyn
    public boolean inSmiley() {
        int dif = (int) Math.sqrt(Math.abs(mx - smileyCenterX) * Math.abs(mx - smileyCenterX) + Math.abs(my - smileyCenterY) * Math.abs(my - smileyCenterY));
        if (dif < 35) {
            return true;
        }
        return false;
    }
//metode qe perdoret per te treguar nese kemi klikuar flamurin

    public boolean inFlagger() {
        int dif = (int) Math.sqrt(Math.abs(mx - flaggerCenterX) * Math.abs(mx - flaggerCenterX) + Math.abs(my - flaggerCenterY) * Math.abs(my - flaggerCenterY));
        if (dif < 35) {
            return true;
        }
        return false;
    }
//metode qe ne baze te kthen numrin horizintal te paketes ne array
    public int inBoxX() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {

                if (mx >= space + i * 80 && mx < i * 80 + 80 - space && my >= space + 80 + j * 80 + 26 && my <= j * 80 + 186 - space) {
                    return i;
                }
            }
        }
        return -1;
    }
//metode qe ne baze te kthen numrin vertikal te paketes ne array
    public int inBoxY() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (mx >= space + i * 80 && mx < i * 80 + 80 - space && my >= space + 80 + j * 80 + 26 && my <= j * 80 + 186 - space) {
                    return j;
                }
            }
        }
        return -1;

    }
//metode qe tregon nese nje pakete eshte pakete fqinje apo jo
    public Boolean isN(int mX, int mY, int cX, int cY) {
        if (mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1) {
            return true;
        }

        return false;
    }

}