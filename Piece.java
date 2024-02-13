package com.company;

public class Piece
{
    private int row, col;
    private boolean afterSkip;

    public Piece(int row, int col)
    {
        this.row = row;
        this.col = col;
        this.afterSkip = false;
    }

    public void skipped()
    {
        afterSkip = true;
    }

    public boolean didSkip()
    {
        if(afterSkip)
        {
            afterSkip = false;

            return true;
        }

        return false;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCoordinates(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

}
