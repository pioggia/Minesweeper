import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.bezier.guido.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Minesweeper extends PApplet {



public final static int NUM_ROWS = 20;
public final static int NUM_COLS = 20;
public final static int MINE_FIELD = (int)(Math.random()*50+30);
private MSButton[][] buttons; //2d array of minesweeper buttons
private ArrayList <MSButton> bombs; //ArrayList of just the minesweeper buttons that are mined
public void setup ()
{
  size(400, 400);
  textAlign(CENTER, CENTER);

  // make the manager
  Interactive.make( this );
  buttons = new MSButton[NUM_ROWS][NUM_COLS];
  for (int i = 0; i<NUM_ROWS; i++)
  {
    for (int a= 0; a<NUM_COLS; a++)
    {
      buttons[i][a] = new MSButton(i, a);
    }
  }
  bombs = new ArrayList <MSButton>();
  setBombs();
}
public void setBombs()
{
  for (int i = 0; i<MINE_FIELD; i++)
  {
    int x = (int)(Math.random()*20);
    int y = (int)(Math.random()*20);
    if (bombs.contains(buttons[x][y]) == false)
    {
      bombs.add(buttons[x][y]);
    }
  }
  System.out.println(bombs.size() + " mines");
}

public void draw ()
{
  background( 0 );
  if (isWon())
    displayWinningMessage();
}
public boolean isWon()
{
  for(int i=0; i<bombs.size(); i++)
  {
    if(bombs.get(i).isMarked() == false)
    {
      return false;
    }
  }
  return true;
}
public void displayLosingMessage()
{
  for(int i = 0; i<bombs.size(); i++)
  {
    bombs.get(i).clicked = true;
  }
  String perso = new String ("hai perso");
  for(int i = 0; i<perso.length(); i++)
  {
    buttons[PApplet.parseInt(NUM_ROWS/2)][3+i].setLabel(perso.substring(i, i+1));
  }
}
public void displayWinningMessage()
{
  String vinto = new String("hai vinto");
  for(int i = 0; i<vinto.length(); i++)
  {
    buttons[PApplet.parseInt(NUM_ROWS/2)][3+i].setLabel(vinto.substring(i, i+1));
  }
}

public class MSButton
{
  private int r, c;
  private float x, y, width, height;
  private boolean clicked, marked;
  private String label;

  public MSButton ( int rr, int cc )
  {
    width = 400/NUM_COLS;
    height = 400/NUM_ROWS;
    r = rr;
    c = cc; 
    x = c*width;
    y = r*height;
    label = "";
    marked = clicked = false;
    Interactive.add( this ); // register it with the manager
  }
  public boolean isMarked()
  {
    return marked;
  }
  public boolean isClicked()
  {
    return clicked;
  }
  // called by manager

  public void mousePressed ()
  {
    clicked = true;
    if (mouseButton == RIGHT) // right click to flag a mine
    {
      marked = !marked;
    } 
    else if (bombs.contains(this))
    {
      displayLosingMessage();
    } 
    else if (countBombs(r, c)>0)
    {
      setLabel(str(countBombs(r, c))); 
    }
    else 
    {
        for (int a = r-1; a<=r+1; a++)
        {
           for (int b = c-1; b<=c+1; b++)
           {
               if (isValid(a, b) == true && buttons[a][b].isClicked() == false)
              {
                  buttons[a][b].mousePressed();
              }
           }
        }
    }
  }
  public void draw() 
  {    
    if (marked)
      fill(0);
    else if ( clicked && bombs.contains(this) ) 
      fill(255, 0, 0);
    else if (clicked)
      fill( 200 );
    else 
      fill( 100 );

    rect(x, y, width, height);
    fill(0);
    text(label, x+width/2, y+height/2);
  }
  public void setLabel(String newLabel)
  {
    label = newLabel;
  }
  public boolean isValid(int r, int c)
  {
    if (r<=NUM_ROWS && c<=NUM_ROWS)
    {
      return true;
    }
    return false;
  }
  public int countBombs(int row, int col)
  {
    int numBombs = 0;
    for (int a = row-1; a<=row+1; a++)
    {
      for (int b = col-1; b<=col+1; b++)
      {
        if (isValid(a, b) == true && bombs.contains(buttons[a][b]))
        {
          numBombs++;
        }
      }
    }
    return numBombs;
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Minesweeper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
