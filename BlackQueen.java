package com.company;

import java.util.ArrayList;

public class BlackQueen extends Piece
{

    public BlackQueen(int row, int col)
    {
        super(row, col);
    }

    public boolean canSkipRightUp(Board board)
    {
        int row = getRow() - 1, col = getCol() + 1;
        Piece currPiece = null;

        while(row > 0 && col < 7)
        {
            currPiece = board.getPiece(row, col);

            if(currPiece instanceof BlackPawn || currPiece instanceof BlackQueen)
                break;

            if(currPiece instanceof WhitePawn || currPiece instanceof WhiteQueen)
            {
                if(board.getPiece(row - 1, col + 1) == null)
                    return true;

                else
                    break;
            }

            row--;
            col++;
        }

        return false;
    }

    public boolean canSkipRightDown(Board board)
    {
        int row = getRow() + 1, col = getCol() + 1;
        Piece currPiece = null;

        while(row < 7 && col < 7)
        {
            currPiece = board.getPiece(row, col);

            if(currPiece instanceof BlackPawn || currPiece instanceof BlackQueen)
                break;

            if(currPiece instanceof WhitePawn || currPiece instanceof WhiteQueen)
            {
                if(board.getPiece(row + 1, col + 1) == null)
                    return true;

                else
                    break;
            }

            row++;
            col++;
        }

        return false;
    }

    public boolean canSkipLeftUp(Board board)
    {
        int row = getRow() - 1, col = getCol() - 1;
        Piece currPiece = null;

        while(row > 0 && col > 0)
        {
            currPiece = board.getPiece(row, col);

            if(currPiece instanceof BlackPawn || currPiece instanceof BlackQueen)
                break;

            if(currPiece instanceof WhitePawn || currPiece instanceof WhiteQueen)
            {
                if(board.getPiece(row - 1, col - 1) == null)
                    return true;

                else
                    break;
            }

            row--;
            col--;
        }

        return false;
    }

    public boolean canSkipLeftDown(Board board)
    {
        int row = getRow() + 1, col = getCol() - 1;
        Piece currPiece = null;

        while(row < 7 && col > 0)
        {
            currPiece = board.getPiece(row, col);

            if(currPiece instanceof BlackPawn || currPiece instanceof BlackQueen)
                break;

            if(currPiece instanceof WhitePawn || currPiece instanceof WhiteQueen)
            {
                if(board.getPiece(row + 1, col + 1) == null)
                    return true;

                else
                    break;
            }

            row++;
            col--;
        }

        return false;
    }

    public boolean skipPossible(Board board)
    {
        return canSkipLeftDown(board) || canSkipLeftUp(board) || canSkipRightDown(board) || canSkipRightUp(board);
    }

    public boolean canMove(Board board)
    {
        ArrayList<Piece> possibleMoves = new ArrayList<>();
        int rowDown = getRow() + 1, rowUp = getRow() - 1, colLeft = getCol() - 1, colRight = getCol() + 1, fullSpaces = 0;

        if(skipPossible(board))
            return true;

        if(rowDown >= 0) {
            if(colLeft >= 0)
                possibleMoves.add(board.getPiece(rowDown, colLeft));

            if(colRight < 8)
                possibleMoves.add(board.getPiece(rowDown, colRight));
        }

        if(rowUp < 8)
        {
            if(colLeft >= 0)
                possibleMoves.add( board.getPiece(rowUp, colLeft));

            if(colRight < 8)
                possibleMoves.add(board.getPiece(rowUp, colRight));
        }

        for (Piece currPiece : possibleMoves)
        {
            if(currPiece != null)
                fullSpaces++;
        }

        if(fullSpaces == possibleMoves.size())
            return false;

        return true;
    }

    private boolean correctSkip(Board board, int toRow, int toCol)
    {
        Piece skipped = null;

        if(canSkipLeftDown(board) && toRow > getRow() && toCol < getCol())
        {
            if(toRow - getRow() == getCol() - toCol)
            {
                skipped = board.getPiece(toRow - 1, toCol + 1);

                if(skipped instanceof WhitePawn || skipped instanceof WhiteQueen)
                    return true;
            }
        }

        if(canSkipRightUp(board) && toRow < getRow() && toCol > getCol())
        {
            if(getRow() - toRow ==  toCol - getCol())
            {
                skipped = board.getPiece(toRow + 1, toCol - 1);

                if(skipped instanceof WhitePawn || skipped instanceof WhiteQueen)
                    return true;
            }
        }

        if(canSkipRightDown(board) && toRow > getRow() && toCol > getCol())
        {
            if(toRow - getRow() ==  toCol - getCol())
            {
                skipped = board.getPiece(toRow - 1, toCol - 1);

                if(skipped instanceof WhitePawn || skipped instanceof WhiteQueen)
                    return true;
            }
        }

        if(canSkipLeftUp(board) && toRow < getRow() && toCol < getCol())
        {
            if(getRow() - toRow ==  getCol() - toCol)
            {
                skipped = board.getPiece(toRow + 1, toCol + 1);

                if(skipped instanceof WhitePawn || skipped instanceof WhiteQueen)
                    return true;
            }
        }

        return false;
    }

    public boolean correctMove(Board board, int toRow, int toCol)
    {
        if(correctSkip(board, toRow, toCol))
            return true;

        int checkRow = toRow, checkCol = toCol;

        int incrementRow = (getRow() - toRow);
        incrementRow /=  Math.abs(incrementRow);

        int incrementCol = (getCol() - toCol);
        incrementCol /=  Math.abs(incrementCol);

        while(checkRow != getRow() && checkCol != getCol())
        {
            if(board.getPiece(checkRow, checkCol) != null)
                return false;

            checkRow += incrementRow;
            checkCol += incrementCol;
        }

        if(checkRow == getRow() && checkCol == getCol() && ! skipPossible(board))
            return true;

        return false;
    }

    public boolean move(Board board, int toRow, int toCol)
    {
        if(canMove(board) && correctMove(board, toRow, toCol))
        {
            if(skipPossible(board))
            {
                int incrementRow = (getRow() - toRow);
                incrementRow /=  Math.abs(incrementRow);

                int incrementCol = (getCol() - toCol);
                incrementCol /=  Math.abs(incrementCol);

                board.movePiece(getRow(), getCol(), toRow, toCol);
                board.removePiece(toRow + incrementRow, toCol + incrementCol);

                skipped();

                return true;
            }

            board.movePiece(getRow(), getCol(), toRow, toCol);

            return true;
        }

        System.out.println("Piece can't move, move is against rules or piece must skip!");

        return false;
    }
}
