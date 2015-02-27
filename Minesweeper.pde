
import de.bezier.guido.*;
public final static int NUM_ROWS = 20;
public final static int NUM_COLS = 20;
private MSButton[][] buttons; //2d array of minesweeper buttons
private ArrayList <MSButton> bombs; //ArrayList of just the minesweeper buttons that are mined

void setup ()
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
  int field = (int)(Math.random()*50+150);
  for (int i = 0; i<field; i++)
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
  return false;
}
public void displayLosingMessage()
{
  background(16, 21, 23);
  textSize(15);
  stroke(255);
  textAlign(CENTER);
  text("hai perso", 200, 200);
}
public void displayWinningMessage()
{
  background(23, 16, 19);
  textSize(15);
  stroke(255);
  textAlign(CENTER);
  text("hai vinto", 200, 200);
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
    if (keyPressed == true) 
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
        for(int a = r-1; a<=r+1; a++)
        {
            for(int b = c-1; b<=c+1; c++)
            {
                if(isValid(a, b) == true && buttons[a][b].isClicked() == false) // fix text offset, maybe an if statement somewhere throwing it off
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
    text(label, x+width/2, (y+height/2)+6);
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
