package com.company;

public interface Queen
{
    public boolean canSkipRightUp(Board board);
    public boolean canSkipRightDown(Board board);
    public boolean canSkipLeftUp(Board board);
    public boolean canSkipLeftDown(Board board);
    public boolean canMove(Board board);
    public boolean correctMove(Board board, int toRow, int toCol);
    public boolean move(Board board, int toRow, int toCol);
    public boolean skipPossible(Board board);
}
