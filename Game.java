package com.company;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Game
{
    private int turnNum;
    private Board board;
    private Player black, white;

    public Game()
    {
        this.turnNum = 1;
        this.board = new Board();
        this.black = new Player();
        this.white = new Player();

        board.initializeBoard();
    }

    private int letterReformatToInt(char c)
    {
        c = Character.toUpperCase(c);
        int result = 0;

        switch (c)
        {
            case 'A':
                result = 0;
                break;

            case 'B':
                result = 1;
                break;

            case 'C':
                result = 2;
                break;

            case 'D':
                result = 3;
                break;

            case 'E':
                result = 4;
                break;

            case 'F':
                result = 5;
                break;

            case 'G':
                result = 6;
                break;

            case 'H':
                result = 7;
                break;

            default:
                result = -1;
                break;
        }

        return result;
    }

    private int getUserInputCol()
    {
        Scanner userInput = new Scanner(System.in);
        char letter = ' ';
        int result = -1;

        System.out.print("Enter a letter: ");

        try
        {
            letter =  userInput.next().charAt(0);

            result = letterReformatToInt(letter);
        }
        catch(InputMismatchException e)
        {
            System.out.println("Incorrect input!");
            return getUserInputCol();
        }

        return result;
    }

    private int getUserInputRow()
    {
        Scanner userInput = new Scanner(System.in);
        int result = -1;

        System.out.print("Enter a number: ");

        try
        {
            result =  userInput.nextInt();

            if(result < 1 || result > 8)
                return  - 1;
        }
            catch(InputMismatchException e)
            {
                System.out.println("Incorrect input!");
                return getUserInputRow();
            }

        return result - 1;
    }

    private boolean whitePawnMovement(int fromRow, int fromCol, int toRow, int toCol)
    {
        WhitePawn piece = (WhitePawn) board.getPiece(fromRow, fromCol);

        if(! piece.move(board, toRow, toCol))
        {
            System.out.println("Move was against rules!");

            return false;
        }

        return  true;
    }

    private boolean blackPawnMovement(int fromRow, int fromCol, int toRow, int toCol)
    {
        BlackPawn piece = (BlackPawn) board.getPiece(fromRow, fromCol);

        if(! piece.move(board, toRow, toCol))
        {
            System.out.println("Move was against rules!");

            return false;
        }

        return  true;
    }

    private boolean whiteQueenMovement(int fromRow, int fromCol, int toRow, int toCol)
    {
        WhiteQueen piece = (WhiteQueen) board.getPiece(fromRow, fromCol);

        if(! piece.move(board, toRow, toCol))
        {
            System.out.println("Move was against rules or you have to skip!");

            return false;
        }

        return true;
    }

    private boolean blackQueenMovement(int fromRow, int fromCol, int toRow, int toCol)
    {
        BlackQueen piece = (BlackQueen) board.getPiece(fromRow, fromCol);

        if(! piece.move(board, toRow, toCol))
        {
            System.out.println("Move was against rules or you have to skip!");

            return false;
        }

        return true;
    }

    private void whiteTurn(Piece skippingPiece)
    {
        int fromRow = 0, fromCol = 0, toRow = 0, toCol = 0;

        System.out.println("White:");

        if(skippingPiece == null)
        {
            System.out.println("Select a piece: ");

            fromCol = getUserInputCol();
            fromRow = getUserInputRow();
        }

        else
        {
            board.printBoard();

            System.out.println("You can skip again");

            fromRow = skippingPiece.getRow();
            fromCol = skippingPiece.getCol();
        }

        System.out.println("Move piece: ");

        toCol = getUserInputCol();
        toRow = getUserInputRow();

        if(fromRow == -1 || fromCol == -1 || toRow == -1 || toCol == -1)
        {
            System.out.println("Incorrect input!");

            whiteTurn(skippingPiece);
        }

        else
        {
            Piece selectedPiece = board.getPiece(fromRow, fromCol);

            if(selectedPiece instanceof WhitePawn)
            {
                if (!whitePawnMovement(fromRow, fromCol, toRow, toCol))
                    whiteTurn(skippingPiece);

                if(selectedPiece.didSkip())
                {
                    black.lostPiece();

                    if((((WhitePawn) selectedPiece).skipPossible(board)))
                        whiteTurn(selectedPiece);
                }

            }

            else if(selectedPiece instanceof WhiteQueen)
            {
                if (!whiteQueenMovement(fromRow, fromCol, toRow, toCol))
                    whiteTurn(skippingPiece);

                if(selectedPiece.didSkip())
                {
                    black.lostPiece();

                    if(((WhiteQueen) selectedPiece).skipPossible(board))
                        whiteTurn(selectedPiece);
                }
            }

            else
            {
                System.out.println("Invalid piece selection");
                whiteTurn(null);
            }
        }
    }


    private void blackTurn(Piece skippingPiece)
    {
        int fromRow = 0, fromCol = 0, toRow = 0, toCol = 0;

        System.out.println("Black: ");
        if(skippingPiece == null)
        {
            System.out.println("Select a piece: ");

            fromCol = getUserInputCol();
            fromRow = getUserInputRow();
        }

        else
        {
            board.printBoard();

            System.out.println("You can skip again");

            fromRow = skippingPiece.getRow();
            fromCol = skippingPiece.getCol();
        }

        System.out.println("Move your piece: ");

        toCol = getUserInputCol();
        toRow = getUserInputRow();

        if(fromRow == -1 || fromCol == -1 || toRow == -1 || toCol == -1)
        {
            System.out.println("Incorrect input!");

            blackTurn(skippingPiece);
        }
        else
        {
            Piece selectedPiece = board.getPiece(fromRow, fromCol);

            if(selectedPiece instanceof BlackPawn)
            {
                if(! blackPawnMovement(fromRow, fromCol, toRow, toCol))
                   blackTurn(skippingPiece);

                if(selectedPiece.didSkip())
                {
                    white.lostPiece();

                    if(((BlackPawn) selectedPiece).skipPossible(board))
                        blackTurn(selectedPiece);
                }
            }

            else if(selectedPiece instanceof BlackQueen)
            {
                if(! blackQueenMovement(fromRow, fromCol, toRow, toCol))
                    blackTurn(skippingPiece);

                if(selectedPiece.didSkip())
                {
                    white.lostPiece();

                    if(((BlackQueen) selectedPiece).skipPossible(board))
                        blackTurn(selectedPiece);
                }
            }

            else
            {
                System.out.println("Invalid piece selection");
                blackTurn(null);
            }
        }
    }

    public void gameLoop()
    {
        while(! white.lost() && ! black.lost())
        {
            board.printBoard();

            if(turnNum % 2 != 0)
                whiteTurn(null);

            else
                blackTurn(null);

            turnNum++;
        }

        if(black.lost())
            System.out.println("White won!");

        else
            System.out.println("Black won!");
    }
}
