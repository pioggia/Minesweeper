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
  //your code here
  return false;
}
public void displayLosingMessage()
{
  //your code here
}
public void displayWinningMessage()
{
  //your code here
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
    } else if (bombs.contains(this))
    {
      displayLosingMessage();
    } else if (countBombs(r, c)>0)
    {
      setLabel(str(countBombs(r, c)));
    } else 
    {
      if (r == 0)
      {
        if (c == 0)
        {
          if (isValid(r+1, c) == true && !bombs.contains(buttons[r+1][c])) //down
          {
            bombs[r+1][c].mousepressed();
          } else if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) //right
          {
            mousepressed();
          } else if (isValid(r+1, c+1) == true && !bombs.contains(buttons[r+1][c+1])) //right down
          {
            mousepressed();
          }
        } else if (c == NUM_COLS)
        {
          if (isValid(r+1, c) == true && !bombs.contains(buttons[r+1][c])) //down
          {
            mousepressed();
          }
          if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) //left
          {
            mousepressed();
          }
          if (isValid(r+1, c-1) == true && !bombs.contains(buttons[r+1][c-1])) 
          {
            mousepressed();
          }
        } else
        {
          if (isValid(r+1, c) == true && !bombs.contains(buttons[r+1][c])) 
          {
            mousepressed();
          }
          if (isValid(r+1, c-1) == true && !bombs.contains(buttons[r+1][c-1])) 
          {
            mousepressed();
          }
          if (isValid(r+1, c+1) == true && !bombs.contains(buttons[r+1][c+1])) 
          {
            mousepressed();
          }
          if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) 
          {
            mousepressed();
          }
          if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) 
          {
            mousepressed();
          }
        }
      } else if (r == NUM_ROWS)
      {
        if (c == 0)
        {
          if (isValid(r-1, c) == true && !bombs.contains(buttons[r-1][c]))
          {
            mousepressed();
          }
          if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) 
          {
            mousepressed();
          }
          if (isValid(r, c+1) == true && !bombs.contains(buttons[r-1][c+1])) 
          {
            mousepressed();
          }
        } else if (c == NUM_COLS)
        {
          if (isValid(r-1, c) == true && !bombs.contains(buttons[r-1][c])) 
          {
            mousepressed();
          }
          if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) 
          {
            mousepressed();
          }
          if (isValid(r-1, c-1) == true && !bombs.contains(buttons[r-1][c-1])) 
          {
            mousepressed();
          }
        } else 
        {
          if (isValid(r-1, c) == true && !bombs.contains(buttons[r-1][c])) 
          {
            mousepressed();
          }
          if (isValid(r-1, c-1) == true && !bombs.contains(buttons[r-1][c-1])) 
          {
            mousepressed();
          }
          if (isValid(r-1, c+1) == true && !bombs.contains(buttons[r-1][c+1])) 
          {
            mousepressed();
          }
          if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) 
          {
            mousepressed();
          }
          if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) 
          {
            mousepressed();
          }
        }
      } else // middle buttons
      {
        if (isValid(r+1, c) == true && !bombs.contains(buttons[r+1][c])) //down
        {
          mousepressed();
        } else if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) //right
        {
          mousepressed();
        } else if (isValid(r+1, c+1) == true && !bombs.contains(buttons[r+1][c+1])) //right down
        {
          mousepressed();
        }
        if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) //left
        {
          mousepressed();
        }
        if (isValid(r-1, c) == true && !bombs.contains(buttons[r-1][c])) //above
        {
          mousepressed();
        } else if (isValid(r+1, c-1) == true && !bombs.contains(buttons[r+1][c-1])) //left down
        {
          mousepressed();
        } else if (isValid(r-1, c+1) == true && !bombs.contains(buttons[r-1][c+1])) //right above
        {
          mousepressed();
        } else if (isValid(r-1, c-1) == true && !bombs.contains(buttons[r-1][c-1])) //left above
        {
          mousepressed();
        }
      }
      return numBombs;
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
      if (row == 0)
      {
        if (col == 0)
        {
          if (isValid(row+1, col) == true && bombs.contains(buttons[row+1][col])) //down
          {
            numBombs++;
          } else if (isValid(row, col+1) == true && bombs.contains(buttons[row][col+1])) //right
          {
            numBombs++;
          } else if (isValid(row+1, col+1) == true && bombs.contains(buttons[row+1][col+1])) //right down
          {
            numBombs++;
          }
        } else if (col == NUM_COLS)
        {
          if (isValid(row+1, col) == true && bombs.contains(buttons[row+1][col])) //down
          {
            numBombs++;
          }
          if (isValid(row, col-1) == true && bombs.contains(buttons[row][col-1])) //left
          {
            numBombs++;
          }
          if (isValid(row+1, col-1) == true && bombs.contains(buttons[row+1][col-1])) 
          {
            numBombs++;
          }
        } else
        {
          if (isValid(row+1, col) == true && bombs.contains(buttons[row+1][col])) 
          {
            numBombs++;
          }
          if (isValid(row+1, col-1) == true && bombs.contains(buttons[row+1][col-1])) 
          {
            numBombs++;
          }
          if (isValid(row+1, col+1) == true && bombs.contains(buttons[row+1][col+1])) 
          {
            numBombs++;
          }
          if (isValid(row, col-1) == true && bombs.contains(buttons[row][col-1])) 
          {
            numBombs++;
          }
          if (isValid(row, col+1) == true && bombs.contains(buttons[row][col+1])) 
          {
            numBombs++;
          }
        }
      } else if (row == NUM_ROWS)
      {
        if (col == 0)
        {
          if (isValid(row-1, col) == true && bombs.contains(buttons[row-1][col]))
          {
            numBombs++;
          }
          if (isValid(row, col+1) == true && bombs.contains(buttons[row][col+1])) 
          {
            numBombs++;
          }
          if (isValid(row, col+1) == true && bombs.contains(buttons[row-1][col+1])) 
          {
            numBombs++;
          }
        } else if (col == NUM_COLS)
        {
          if (isValid(row-1, col) == true && bombs.contains(buttons[row-1][col])) 
          {
            numBombs++;
          }
          if (isValid(row, col-1) == true && bombs.contains(buttons[row][col-1])) 
          {
            numBombs++;
          }
          if (isValid(row-1, col-1) == true && bombs.contains(buttons[row-1][col-1])) 
          {
            numBombs++;
          }
        } else 
        {
          if (isValid(row-1, col) == true && bombs.contains(buttons[row-1][col])) 
          {
            numBombs++;
          }
          if (isValid(row-1, col-1) == true && bombs.contains(buttons[row-1][col-1])) 
          {
            numBombs++;
          }
          if (isValid(row-1, col+1) == true && bombs.contains(buttons[row-1][col+1])) 
          {
            numBombs++;
          }
          if (isValid(row, col-1) == true && bombs.contains(buttons[row][col-1])) 
          {
            numBombs++;
          }
          if (isValid(row, col+1) == true && bombs.contains(buttons[row][col+1])) 
          {
            numBombs++;
          }
        }
      } else // middle buttons
      {
        if (isValid(row+1, col) == true && bombs.contains(buttons[row+1][col])) //down
        {
          numBombs++;
        } else if (isValid(row, col+1) == true && bombs.contains(buttons[row][col+1])) //right
        {
          numBombs++;
        } else if (isValid(row+1, col+1) == true && bombs.contains(buttons[row+1][col+1])) //right down
        {
          numBombs++;
        }
        if (isValid(row, col-1) == true && bombs.contains(buttons[row][col-1])) //left
        {
          numBombs++;
        }
        if (isValid(row-1, col) == true && bombs.contains(buttons[row-1][col])) //above
        {
          numBombs++;
        } else if (isValid(row+1, col-1) == true && bombs.contains(buttons[row+1][col-1])) //left down
        {
          numBombs++;
        } else if (isValid(row-1, col+1) == true && bombs.contains(buttons[row-1][col+1])) //right above
        {
          numBombs++;
        } else if (isValid(row-1, col-1) == true && bombs.contains(buttons[row-1][col-1])) //left above
        {
          numBombs++;
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
