package com.company;

public class BlackPawn extends Piece implements Pawn
{
    public BlackPawn(int row, int col)
    {
        super(row, col);
    }

    public boolean canSkipLeft(Board board)
    {
        if(getRow() < 6 && getCol() > 1)
        {
            Piece toBeSkipped = board.getPiece(getRow() + 1, getCol() - 1);

            if(toBeSkipped instanceof WhitePawn || toBeSkipped instanceof WhiteQueen)
            {
                if(board.getPiece(getRow() + 2, getCol() - 2) == null)
                    return true;
            }
        }
        return false;
    }

    public boolean canSkipRight(Board board)
    {
        if(getRow() < 6 && getCol() < 6)
        {
            Piece toBeSkipped = board.getPiece(getRow() + 1, getCol() + 1);

            if(toBeSkipped instanceof WhitePawn || toBeSkipped instanceof WhiteQueen)
            {
                if(board.getPiece(getRow() + 2, getCol() + 2) == null)
                    return true;
            }
        }
        return false;
    }

    public boolean skipPossible(Board board)
    {
        return canSkipLeft(board) || canSkipRight(board);
    }

    public boolean canMove(Board board)
    {
        if(getRow() < 7 && getCol() > 0)
        {
            if(board.getPiece(getRow() + 1, getCol() - 1) == null)
                return true;
        }

        if(getRow() < 7 && getCol() < 7)
        {
            if(board.getPiece(getRow() + 1, getCol() + 1) == null)
                return true;
        }

        if(canSkipLeft(board) || canSkipRight(board))
            return true;

        return false;
    }

    public boolean correctMove(Board board, int toRow, int toCol)
    {
        if(toRow == getRow() + 1)
        {
            if(skipPossible(board))
            {
                System.out.println("You must skip!");

                return false;
            }

            if(toCol == getCol() - 1 || toCol == getCol() + 1)
                return true;
        }

        if(toRow == getRow() + 2)
        {
            if(canSkipLeft(board) && toCol == getCol() - 2)
                return true;

            if(canSkipRight(board) && toCol == getCol() + 2)
                return true;
        }

        return false;
    }

    public boolean move(Board board, int toRow, int toCol)
    {
        if (canMove(board) && correctMove(board, toRow, toCol))
        {
            if (canSkipLeft(board) && getCol() > toCol)
            {
                board.removePiece(toRow - 1, toCol + 1);
                skipped();
            }

            else if (canSkipRight(board) && getCol() < toCol)
            {
                board.removePiece(toRow - 1, toCol - 1);
                skipped();
            }

            board.movePiece(getRow(), getCol(), toRow, toCol);

            if(getRow() == 7)
                board.queenUpgrade(getRow(), getCol());

            return true;
        }

        return false;
    }
}
