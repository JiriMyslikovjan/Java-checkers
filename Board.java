package com.company;

public class Board
{
    private Piece[][] board;

    public Board()
    {
        this.board = new Piece[8][8];
    }

    public Piece getPiece(int row, int col)
    {
        return board[row][col];
    }

    public void removePiece(int row, int col)
    {
        board[row][col] = null;
    }

    // Moves a piece and updates its coordinates

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol)
    {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = null;

        getPiece(toRow, toCol).setCoordinates(toRow, toCol);
    }

    public void queenUpgrade(int row, int col)
    {
        if(getPiece(row, col) instanceof WhitePawn)
            board[row][col] = new WhiteQueen(row, col);

        else
            board[row][col] = new BlackQueen(row, col);
    }

    public void initializeBoard()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if (i < 3)
                {
                    if (i % 2 == 0)
                    {
                        if(j % 2 != 0)
                            board[i][j] = new BlackPawn(i, j);

                        else
                            board[i][j] = null;
                    }

                    else
                    {
                        if(j % 2 == 0)
                            board[i][j] = new BlackPawn(i, j);

                        else
                            board[i][j] = null;

                    }
                }

                else if(i > 4)
                {
                    if(i % 2 == 0)
                    {
                        if(j % 2 != 0)
                            board[i][j] = new WhitePawn(i, j);

                        else
                            board[i][j] = null;
                    }

                    else
                    {
                        if(j % 2 == 0)
                            board[i][j] = new WhitePawn(i, j);

                        else
                            board[i][j] = null;
                    }
                }

                else
                    board[i][j] = null;
            }
        }
    }

    // Gets type of piece on board (0 == white pawn, 1 == white queen, 2 == black pawn, 3 == black queen, 4 == empty)

    private int pieceType(int row, int col)
    {
        Piece piece = getPiece(row, col);

        if(piece instanceof WhitePawn)
            return 0;

        if(piece instanceof WhiteQueen)
            return 1;

        if(piece instanceof BlackPawn)
            return 2;

        if(piece instanceof BlackQueen)
            return 3;

        return 4;

    }

    void printBoard()
    {
        int typeOfPiece = 0;

        System.out.println(" A  B  C  D  E  F  G  H ");

        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                typeOfPiece = pieceType(i, j);

                switch (typeOfPiece)
                {
                    case 0:
                        System.out.print("[w]");
                        break;

                    case 1:
                        System.out.print("[W]");
                        break;

                    case 2:
                        System.out.print("[b]");
                        break;

                    case 3:
                        System.out.print("[B]");
                        break;

                    default:
                        System.out.print("[ ]");
                        break;
                }
            }
            System.out.println(" " + (i + 1));
        }
    }
}

