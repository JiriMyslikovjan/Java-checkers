package com.company;

public class Player
{
    private boolean lost;
    private int numOfPieces;

    public Player()
    {
        this.lost = false;
        this.numOfPieces = 15;
    }

    public boolean lost()
    {
        return lost;
    }

    public int getNumOfPieces()
    {
        return numOfPieces;
    }

    public void lostPiece()
    {
        numOfPieces--;
        if(getNumOfPieces() == 0)
            this.lost = true;
    }
}
