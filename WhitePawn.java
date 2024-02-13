package com.company;

public class WhitePawn extends Piece implements Pawn
{
    public WhitePawn(int row, int col)
    {
        super(row, col);
    }

    public boolean canSkipLeft(Board board)
    {
        if(getRow() > 1 && getCol() > 1)
        {
            Piece toBeSkipped = board.getPiece(getRow() - 1, getCol() - 1);

            if(toBeSkipped instanceof BlackPawn || toBeSkipped instanceof BlackQueen)
            {
                if(board.getPiece(getRow() - 2, getCol() - 2) == null)
                    return true;
            }
        }
        return false;
    }

    public boolean canSkipRight(Board board)
    {
        if(getRow() > 1 && getCol() < 6)
        {
            Piece toBeSkipped = board.getPiece(getRow() - 1, getCol() + 1);

            if(toBeSkipped instanceof BlackPawn || toBeSkipped instanceof BlackQueen)
            {
                if(board.getPiece(getRow() - 2, getCol() + 2) == null)
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
        if(getRow() > 0 && getCol() > 0)
        {
            if(board.getPiece(getRow() - 1, getCol() - 1) == null)
                return true;
        }

        if(getRow() > 0 && getCol() < 7)
        {
            if(board.getPiece(getRow() - 1, getCol() + 1) == null)
                return true;
        }

        if(canSkipLeft(board) || canSkipRight(board))
            return true;

        return false;
    }

    public boolean correctMove(Board board, int toRow, int toCol)
    {
        if(toRow == getRow() - 1)
        {
            if(canSkipRight(board) || canSkipLeft(board))
            {
                System.out.println("You have must skip!");

                return false;
            }

            if(toCol == getCol() - 1 || toCol == getCol() + 1)
                return true;
        }

        if(toRow == getRow() - 2)
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
        if(canMove(board) && correctMove(board, toRow, toCol))
        {
            if(canSkipLeft(board) && getCol() > toCol)
            {
                board.removePiece(toRow + 1, toCol + 1);
                skipped();
            }

            else if(canSkipRight(board) && getCol() < toCol)
            {
                board.removePiece(toRow + 1, toCol - 1);
                skipped();
            }

            board.movePiece(getRow(), getCol(), toRow, toCol);

            if(getRow() == 0)
                board.queenUpgrade(getRow(), getCol());

            return true;
        }

        return false;
    }
}
