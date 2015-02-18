


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
    } else if (bombs.contains(this))
    {
      displayLosingMessage();
    } else if (countBombs(r, c)>0)
    {
      setLabel(str(countBombs(r, c))); // refer to recursive buttons in processing
    } else 
    {
      if (r == 0)
      {
        if (c == 0)
        {
          if (isValid(r+1, c) == true && !bombs.contains(buttons[r+1][c])) //down
          {
            buttons[r+1][c].mousePressed();
          } else if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) //right
          {
            mousePressed();
          } else if (isValid(r+1, c+1) == true && !bombs.contains(buttons[r+1][c+1])) //right down
          {
            mousePressed();
          }
        } else if (c == NUM_COLS)
        {
          if (isValid(r+1, c) == true && !bombs.contains(buttons[r+1][c])) //down
          {
            mousePressed();
          }
          if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) //left
          {
            mousePressed();
          }
          if (isValid(r+1, c-1) == true && !bombs.contains(buttons[r+1][c-1])) 
          {
            mousePressed();
          }
        } else
        {
          if (isValid(r+1, c) == true && !bombs.contains(buttons[r+1][c])) 
          {
            mousePressed();
          }
          if (isValid(r+1, c-1) == true && !bombs.contains(buttons[r+1][c-1])) 
          {
            mousePressed();
          }
          if (isValid(r+1, c+1) == true && !bombs.contains(buttons[r+1][c+1])) 
          {
            mousePressed();
          }
          if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) 
          {
            mousePressed();
          }
          if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) 
          {
            mousePressed();
          }
        }
      } else if (r == NUM_ROWS)
      {
        if (c == 0)
        {
          if (isValid(r-1, c) == true && !bombs.contains(buttons[r-1][c]))
          {
            mousePressed();
          }
          if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) 
          {
            mousePressed();
          }
          if (isValid(r, c+1) == true && !bombs.contains(buttons[r-1][c+1])) 
          {
            mousePressed();
          }
        } else if (c == NUM_COLS)
        {
          if (isValid(r-1, c) == true && !bombs.contains(buttons[r-1][c])) 
          {
            mousePressed();
          }
          if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) 
          {
            mousePressed();
          }
          if (isValid(r-1, c-1) == true && !bombs.contains(buttons[r-1][c-1])) 
          {
            mousePressed();
          }
        } else 
        {
          if (isValid(r-1, c) == true && !bombs.contains(buttons[r-1][c])) 
          {
            mousePressed();
          }
          if (isValid(r-1, c-1) == true && !bombs.contains(buttons[r-1][c-1])) 
          {
            mousePressed();
          }
          if (isValid(r-1, c+1) == true && !bombs.contains(buttons[r-1][c+1])) 
          {
            mousePressed();
          }
          if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) 
          {
            mousePressed();
          }
          if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) 
          {
            mousePressed();
          }
        }
      } else // middle buttons
      {
        if (isValid(r+1, c) == true && !bombs.contains(buttons[r+1][c])) //down
        {
          mousePressed();
        } else if (isValid(r, c+1) == true && !bombs.contains(buttons[r][c+1])) //right
        {
          mousePressed();
        } else if (isValid(r+1, c+1) == true && !bombs.contains(buttons[r+1][c+1])) //right down
        {
          mousePressed();
        }
        if (isValid(r, c-1) == true && !bombs.contains(buttons[r][c-1])) //left
        {
          mousePressed();
        }
        if (isValid(r-1, c) == true && !bombs.contains(buttons[r-1][c])) //above
        {
          mousePressed();
        } else if (isValid(r+1, c-1) == true && !bombs.contains(buttons[r+1][c-1])) //left down
        {
          mousePressed();
        } else if (isValid(r-1, c+1) == true && !bombs.contains(buttons[r-1][c+1])) //right above
        {
          mousePressed();
        } else if (isValid(r-1, c-1) == true && !bombs.contains(buttons[r-1][c-1])) //left above
        {
          mousePressed();
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



