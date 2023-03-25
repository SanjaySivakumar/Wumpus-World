//Sai Charan Todupunoori CS2 Mr.Tully, period 2
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class WWorld extends Application
{
    //Public static final attributes
    public static final int PLAYING = 0;
    public static final int DEAD = 1;
    public static final int WON = 2;
 
    //private attributes
    private int status;
    private WumpusPlayer player;
    private int  h = 0;
    private WumpusMap map;
    private GraphicsContext gc;
    private int ww=0;
    private int r=0;
    private int f=0;
    private int ll=0;
    private int a=0;
    Image arrow,black,breeze,deadWumpus,Floor,gold,ladder,pit,playerDown,playerLeft,playerRight,playerUp,stench,wumpus;
    private Image arrowImg;
    private Image blackImg;
    private Image breezeImg;
    private Image deadWumpusImg;
    private Image floorImg;
    private Image goldImg;
    private Image ladderImg;
    private Image playerDownImg;
    private Image playerLeftImg;
    private Image playerRightImg;
    private Image playerUpImg;
    private Image pitImg;
    private Image stenchImg;
    private Image wumpusImg;
    private int LadderRow;
    private boolean stop = false;
    private boolean cheat = false;
 
    public boolean hasArrow = true;
    public boolean hasGold = true;
 
 
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Wumpus World");
        arrowImg = new Image("File:Images/arrow.gif");
        blackImg = new Image("File:Images/black.gif");
        breezeImg = new Image("File:Images/breeze.gif");
        deadWumpusImg = new Image("File:Images/deadWumpus.gif");
        floorImg = new Image("File:Images/Floor.gif");
        goldImg = new Image("File:Images/gold.gif");
        ladderImg = new Image("File:Images/ladder.gif");
        pitImg = new Image("File:Images/pit.gif");
        playerDownImg = new Image("File:Images/playerDown.png");
        playerLeftImg = new Image("File:Images/playerLeft.png");
        playerRightImg = new Image("File:Images/playerRight.png");
        playerUpImg  = new Image("File:Images/playerUp.png");
        stenchImg  = new Image("File:Images/stench.gif");
        wumpusImg  = new Image("File:Images/wumpus.gif");
        Group group = new Group();
        primaryStage.show();
        Canvas canvas = new Canvas(500, 700);
 
        canvas.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String s = event.getCharacter();
                System.out.println(status);
                if (status == PLAYING) {
                    if (stop == false) {
 
                        if (s.equals("c") && map.getSquare(player.getRowPosition(), player.getColPosition()).isLadder() && player.getGold() == true) {
                            status = WON;
                            reset();
                        }
 
 
                        if (s.equals("d")) {
                            player.setDirection(1);
                            player.setRowPosition(player.getRowPosition() + 1);
                            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                        }
                        if (s.equals("a")) {
                            player.setDirection(WumpusPlayer.WEST);
                            if (player.getRowPosition() - 1 >= 0)
                                player.setRowPosition(player.getRowPosition() - 1);
                            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                        }
 
                        if (s.equalsIgnoreCase("w")) {
                            player.setDirection(WumpusPlayer.NORTH);
                            player.setColPosition(player.getColPosition() - 1);
                            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
                        }
 
                        if (s.equalsIgnoreCase("s")) {
                            player.setDirection(WumpusPlayer.SOUTH);
 
                            player.setColPosition(player.getColPosition() + 1);
                            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
 
 
                        }
                        if (s.equalsIgnoreCase("p") && map.getSquare(player.getRowPosition(), player.getColPosition()).isGold()) {
                            System.out.println("step 1");
                            map.getSquare(player.getRowPosition(), player.getColPosition()).setGold(false);
                            System.out.println("step 2");
 
                            System.out.println("step 3");
                            player.setGold(true);
                            gc.drawImage(goldImg, 10, 10);
                            hasGold = false;
                        } else {
                            System.out.println("step 4");
                            hasGold = false;
                        }
                    }
 
                }
 
                if (s.equalsIgnoreCase("n")) {
                    reset();
                }
 
                if(s.equalsIgnoreCase("*"))
                {
                 cheat =!cheat;
                }
                paint(canvas.getGraphicsContext2D());
 
            }
        });
        group.getChildren().add(canvas);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        reset();
        paint(canvas.getGraphicsContext2D());
      canvas.requestFocus();
        primaryStage.show();
    }
 
    //calls launch
    public static void main(String[] args)
    {
        launch(args);
    }
 
    public  void paint(GraphicsContext gc)
    {
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,500,700);
        gc.setFill(Color.BLACK);
        gc.fillRect(0,500,500,300);
        gc.setFill(Color.WHITE);
        gc.fillRect(200,500,20,300);
        gc.setFill(Color.RED);
        gc.setFont(new Font("Manjari",30));
        gc.fillText("Inventory",5,525);
        gc.fillText("Messages",240,525);
        for(int r = 0; r < 10; r++ )
        {
            for(int c = 0; c < 10; c++)
            {
                gc.drawImage(floorImg,r*50,c*50);
            }
        }
 
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
 
                if (map.getSquare(x, y).isPit())
                   gc.drawImage(pitImg, x * 50, y * 50);
 
                if (map.getSquare(x, y).isBreeze())
                    gc.drawImage(breezeImg, x * 50, y * 50);
 
                if (map.getSquare(x, y).isWumpus())
                   gc.drawImage(wumpusImg, x * 50, y * 50);
 
                if (map.getSquare(x, y).isDeadWumpus())
                    gc.drawImage(deadWumpusImg, x * 50, y * 50);
 
                if(map.getSquare(x,y).isGold())
                {
                    gc.drawImage(goldImg, x * 50, y * 50);
 
                }
                if (map.getSquare(x, y).isStench())
                   gc.drawImage(stenchImg, x * 50, y * 50);
 
 
 
                if(map.getSquare(x,y).isVisited()==false && cheat== false)
                {
                    gc.drawImage(blackImg,x*50 ,y*50);
                }
 
                if (map.getSquare(x, y).isLadder()) {
                    gc.drawImage(ladderImg, x * 50, y * 50);
 
                }
                if (map.getSquare(x, y).isGold()  )
                   gc.drawImage(goldImg, x * 50, y * 50);
 
 
            }
            if(hasGold == true)
            {
                gc.drawImage(goldImg,20,500);
            }
        }
 
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c ++) {
                if (map.getSquare(c, r).isBlack() == true) {
                   gc.drawImage(black, c * 50, r * 50);
                }
            }
 
        }
 
        if (!map.getSquare(player.getRowPosition(), player.getColPosition()).isBreeze() && !map.getSquare(player.getRowPosition(), player.getColPosition()).isStench() && !map.getSquare(player.getRowPosition(), player.getColPosition()).isGold()  && !map.getSquare(player.getRowPosition(), player.getColPosition()).isLadder() && !map.getSquare(player.getRowPosition(), player.getColPosition()).isDeadWumpus()) {
 
        }
        gc.setFill(Color.BLUE);
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isBreeze()) {
            gc.fillText("You feel a breeze",250,580,175);
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isStench() || map.getSquare(player.getRowPosition(), player.getColPosition()).isDeadWumpus()) {
            gc.fillText("You smell a stench", 250, 650);
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isGold() && player.getGold() == false) {
            gc.fillText("You see a glimmer", 250,620,200);
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isLadder() ) {
            gc.fillText("You bump into a ladder", 250,630,200);
 
        }
        if (h == 1) {
            if (player.getGold() == false) {
                gc.drawImage(floorImg, player.getRowPosition() * 50, player.getColPosition() * 50);
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isBreeze()) {
                   gc.drawImage(breezeImg, player.getRowPosition() * 50, player.getColPosition() * 50);
                }
                if (map.getSquare(player.getRowPosition(), player.getColPosition()).isStench()) {
                   gc.drawImage(stenchImg, player.getRowPosition() * 50, player.getColPosition() * 50);
                }
                h--;
            }
        }
 
        if (r == 5) {
           gc.drawImage(deadWumpusImg, f * 50, ll * 50);
            map.getSquare(f, ll).setDeadWumpus(true);
            map.getSquare(f, ll).setWumpus(true);
        }
        if (status ==PLAYING && player.isArrow() == true)
        {
            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
        }
        if (status == PLAYING && player.getGold() == true)
        {
            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
        }
        if ( map.getSquare(player.getRowPosition(), player.getColPosition()).isPit()) {
 
            status = DEAD;
            System.out.println("dd"+status);
            gc.setFill(Color.BLUE);
            gc.fillText("You fell down a pit,Game Over.(N for new game)", 200, 550,300);
        }
 
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isWumpus()) {
            status =DEAD;
            gc.setFill(Color.BLUE);
            gc.fillText("You are eaten . Game Over.(n for new game)", 250, 550,200);
        }
 
        if (map.getSquare(player.getRowPosition(),player.getColPosition()).isGold() && map.getSquare(player.getRowPosition(),player.getColPosition()).isLadder())
        {
            status= WON;
            gc.setFill(Color.BLUE);
            gc.fillText("You Win.(n for new game)", 150,650,300);
        }
        System.out.println("aaaaaa"+player.getDirection());
        if (player.getDirection() == player.NORTH) {
            gc.drawImage(playerUpImg, player.getRowPosition() * 50, player.getColPosition() * 50);
        } else if (player.getDirection() == player.EAST) {
            gc.drawImage(playerRightImg, player.getRowPosition() * 50, player.getColPosition() * 50);
        } else if (player.getDirection() == player.SOUTH) {
            gc.drawImage(playerDownImg, player.getRowPosition() * 50, player.getColPosition() * 50);
        } else if (player.getDirection() == player.WEST) {
            gc.drawImage(playerLeftImg, player.getRowPosition() * 50, player.getColPosition() * 50);
        }
 
        if(player.getGold()==true)
        {
            gc.drawImage(goldImg,50,550);
            System.out.println("I have gold");
        }
    }
 
    public void reset()
    {
        status = PLAYING;
        map = new WumpusMap();
        player = new WumpusPlayer();
        player.setGold(false);
        player.setRowPosition(map.getLadderC());
        player.setColPosition(map.getLadderR());
    }
}

