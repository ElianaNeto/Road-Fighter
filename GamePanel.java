import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import  java.util.Random;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements  ActionListener
{

    static final  int SCREEN_WIDTH = 350;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 50; 
    static final int DELAY = 130; 

    int xCar = 100;
     int yCar = 450;
     int coinsEatean;
     int bestScore;
     int coinX;
     int coinY;
     int xOC;
     int yOC;
     int teste1 ;
     int teste2;
     
     int xOCb;
     int yOCb;
     
     char direction ;
     boolean running = false;
     boolean coli = false;

     Timer time;
     Random random;
    
     
    String imagePath = "imagens/red.png";
    String imagePath1 = "imagens/estrada.jpg";
    String imagePath2 = "imagens/yellow.png";
    String coinPath = "imagens/money.png";
    String diamondPath = "imagens/diamond.png";
    String bluePath = "imagens/blue.png";
    String exploPath = "imagens/explosion.png";


    Image car;
    Image car1;
    Image blueCar;
    Image estrada;
    Image coin;
    Image diamond;
    Image explosion;
    
    ImageIcon carIcon ;
    ImageIcon blueCarIcon;
    ImageIcon coinIcon;
    ImageIcon exploIcon;
    ImageIcon estradaIcon;
    ImageIcon yellowCarIcon ;
    
    
        private ArrayList <Rectangle> line;

        private int lineSpace;
        private int lineSpeed;
    
    
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        carIcon = new ImageIcon(imagePath);
        estradaIcon = new ImageIcon(imagePath1);
        yellowCarIcon = new ImageIcon(imagePath2);
        coinIcon = new ImageIcon(coinPath);
        blueCarIcon = new ImageIcon(bluePath);
        exploIcon = new ImageIcon(exploPath);

        car =  carIcon.getImage();
        car1 = yellowCarIcon.getImage();
        estrada =  estradaIcon.getImage();
        coin = coinIcon.getImage();
        blueCar = blueCarIcon.getImage();
        explosion = exploIcon.getImage();
        
        line = new ArrayList<Rectangle>();

        lineSpace = 50;
        lineSpeed = 25;
        teste1 = SCREEN_WIDTH/2;
        
        
        startGame();
        
    }
    
    public void startGame(){
        newCoin();
        newOCar();
        running = true;
        coinsEatean = 0;
        xCar = 100;
        yCar = 450;
        time = new Timer(DELAY, this);
        time.start();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        
    }
    
    
    
    public void draw(Graphics g){
        
        Graphics2D g2d = (Graphics2D) g;
 
        g.drawImage(estrada,0,0, SCREEN_WIDTH , SCREEN_HEIGHT,null);
        g.setColor(Color.white);

        for(Rectangle rect:line){
            g.fillRect(rect.x, rect.y,rect.width,rect.height);   
        }
           
            
        g.drawImage(car,xCar,yCar,  UNIT_SIZE , UNIT_SIZE,null);
        g.drawImage(car1,xOC,yOC,  UNIT_SIZE, UNIT_SIZE,null);        
        g.drawImage(coin, coinX, coinY, UNIT_SIZE,UNIT_SIZE,null);
        g.drawImage(blueCar, xOCb, yOCb,UNIT_SIZE ,UNIT_SIZE,null);
            

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("SCORE: "+coinsEatean+" BEST: "+bestScore, (SCREEN_WIDTH - metrics.stringWidth("SCORE: "+coinsEatean+"BEST: "+bestScore))/2, g.getFont().getSize());
           
        if (!running) {
            gameOver(g);
        }
    }
    
    public void addLine (Boolean first ){
        int x = SCREEN_WIDTH/2;
        int y = 0;
        int width = 4;
        int height = 25;
        
        if (first){
            line.add(new Rectangle(x,y-(line.size()*lineSpace),width,height));
            
        }
        else {
            line.add(new Rectangle(x,line.get(line.size()-1).y-lineSpace,width,height));
        }
    }
    
    public void newCoin(){
        
        coinX = (random.nextInt(5))*UNIT_SIZE;
        coinY =  - random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        if (coinX == 0 || coinX == 50) {
                coinX = 100;
        }
    }
    
    public void newOCar() {
        
        xOC = (random.nextInt(5))*UNIT_SIZE ;
        yOC =  - random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        
        xOCb = (random.nextInt(5))*UNIT_SIZE ;
        yOCb = - random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        
        if(xOC == 0 || xOC == 50 ){
            xOC = 100; 
        }
            
        if ((xOCb == 0 || xOCb == 50)) {
            xOCb = 100;
        }
              
    }
    
    public void move(){
      
       Rectangle rect;
       Rectangle rect1;

        for(int i = 0; i<line.size();i++)
            {
                rect1 = line.get(i);
                rect1.y+=lineSpeed;
            }
             
            coinY += UNIT_SIZE;
            
            yOC += UNIT_SIZE;
            
            yOCb += UNIT_SIZE;
            teste1 +=lineSpeed;
               
    }
    
    public void checkCoin(){
        if((xCar == coinX) && (yCar == coinY)) {
            coinsEatean ++;
            newCoin();
        }
        else if (coinY > SCREEN_HEIGHT) {
            newCoin();
        }
        
    }
    
    public void  checkBest ()
    {
        if(bestScore < coinsEatean)
            bestScore = coinsEatean;
    } 
    public void checkCollisions (){
        
      
        if(((xCar == xOC) && (yCar == yOC)) || (xCar == xOCb) && (yCar == yOCb)) {
           running = false;
        }
        else if (yOC > SCREEN_HEIGHT || yOCb > SCREEN_HEIGHT) {
            newOCar();
        }
       
        if (xCar < 100) {
            running = false;
        }
      
        if (xCar > 200) {
            running = false;
        }
        
        
        if (yCar < 0) {
            running = false;
        }
        
        
        if (yCar > (SCREEN_HEIGHT)) {
            running = false;
        }
        
        if (!running) {
            time.stop();
        }
        
    }
    
    public void gameOver(Graphics g){
   
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);

        g.drawImage(explosion, xCar , yCar,100,100, this);
        time.stop();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkCoin();
            checkCollisions();
            checkBest();
            addLine(true);
        }
        repaint();  
    }
    
    
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public  void keyPressed (KeyEvent e ){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                        direction = 'L';
                        xCar = xCar - UNIT_SIZE;
                    break;
                
                case KeyEvent.VK_RIGHT:
                        direction = 'R';
                        xCar = xCar + UNIT_SIZE;
                    break;
                
                case KeyEvent.VK_UP:
                        direction = 'U';
                        yCar = yCar - UNIT_SIZE;
                    break;

                case KeyEvent.VK_DOWN:
                        direction = 'D';
                        yCar = yCar + UNIT_SIZE;
                    break;
                    
                case KeyEvent.VK_SPACE:
                        startGame();
                    break;
            } 
        }
    }   
}
