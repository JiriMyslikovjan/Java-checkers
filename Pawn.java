package com.company;

public interface Pawn
{
    public boolean canSkipLeft(Board board);
    public boolean canSkipRight(Board board);
    public boolean canMove(Board board);
    public boolean correctMove(Board board, int toRow, int toCol);
    public boolean move(Board board, int toRow, int toCol);
    public boolean skipPossible(Board board);

}
