package miner.chessfun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class QueensPlacement extends AppCompatActivity {
    // Main program
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queens_placement);

        // Instantiate the class
        QueensPlacement objPrgm = new QueensPlacement();

        // Grab Input
        objPrgm.ClearChessBoard(objPrgm.ChessBoard);

        // Play the queen problem
        objPrgm.QueenBackTrack(objPrgm.ChessBoard, objPrgm.ChessNote(objPrgm.Square), 1);

        // Show answers
        objPrgm.PrintBoard(objPrgm.ChessBoard);

        // Exit program
        return;

    }


        /*//Image Test
        final ImageButton btn21 = (ImageButton) findViewById(R.id.r21);
        final ImageButton btn98 = (ImageButton) findViewById(R.id.r98);
        final Button ImageTst = (Button) findViewById(R.id.ImageTest);
        ImageTst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn98.setImageResource(R.drawable.ic_knight);
                btn21.setImageResource(R.drawable.ic_queen);
            }
        });*/

    // Debugging?
    Boolean DebugPrgm = true;

    // keep track of bad moves
    int BadMoves = 0;

    // representation of chess board 12 rows x 10 columns
    int [] ChessBoard = new int [120];

    // current moves
    int [] MoveSet = new int [65];

    // queen flag
    Boolean QueenFlag = true;

    // max move so far
    int MaxMoves = 8;
    int TotMoves = 0;
    int CacheHit = 0;

    // Starting square
    String Square = "B5";


    //
    // ChessBoardToStr - Initialize the chessboard.
    //
    public String ChessBoardToStr()
    {
        // Initialize variables
        String strTemp = "";

        // Initialize the chess board
        for (int i = 0; i < 120; i++)
        {
            // Inside board?
            if (ChessRow(i) >= 0 && ChessRow(i) <= 7 && ChessCol(i) >= 0 && ChessCol(i) <= 7)
                strTemp += String.valueOf(ChessBoard[i]);
        }

        // Return the result
        return(strTemp);
    }


    //
    // ClearChessBoard - Initialize the chessboard.
    //
    public void ClearChessBoard(int [] intChessBoard)
    {
        // Initialize the chess board
        for(int i = 0; i < 120; i++)
        {
            // Outside or inside board?
            if (ChessRow(i) < 0 || ChessRow(i) > 7 || ChessCol(i) < 0 || ChessCol(i) > 7)
                ChessBoard[i] = -1;
            else
                ChessBoard[i] = 0;
        }
    }


    //
    // ChessRow - Given a square, return which row.
    //
    int ChessRow (int Square)
    {
        return((Square / 10) - 2);
    }


    //
    // ChessCol - Given a square, return which column.
    //
    int ChessCol (int Square)
    {
        return((Square % 10) - 1);
    }


    //
    // ChessNote - given a String, return a square.
    //
    int ChessNote (String Notation)
    {

        // Define variables
        int Row;
        int Col;

        // Get row definition
        Row = (int)Notation.charAt(1);
        Row -= ChrToAsc("1");
        System.out.println(Row);

        // Get col definition
        Col = (int)Notation.charAt(0);
        Col -= ChrToAsc("A");
        System.out.println(Col);

        // Return result
        return (ChessSqu(Row, Col));

    }


    //
    // ChessSqu - given a col & row, return which square.
    //
    int ChessSqu (int Row, int Col)
    {
        return((21 + (Row * 10) + Col));
    }


    //
    // ChessUnNote - Given a square, return a String.
    //
    String ChessUnNote (int Square)
    {

        // Define variables
        int Row;
        int Col;
        String Temp;

        // Get col definition
        Col = ChessCol(Square);
        Col += ChrToAsc("A");

        // Get row definition
        Row = ChessRow(Square);
        Row += ChrToAsc("1");

        // Chess notation
        Temp = Character.toString ((char) Col) + Character.toString ((char) Row);

        // Return the result
        return (Temp);

    }


    //
    // ChrToAsc - convert a char to int
    //
    public int ChrToAsc(String input)
    {
        char [] chrTemp = new char [2];
        chrTemp = input.toCharArray();
        int intTemp = (int)chrTemp[0];
        return(intTemp);
    }


    //
    // PrintBoard - Show the board to stdout.
    //
    public void PrintBoard (int [] intChessBoard)
    {
        for (int i = 1; i <= 12; i++)
        {
            for (int j = 1; j <= 10; j++)
            {
                System.out.format("%2d", intChessBoard[(i-1) * 10 + (j-1)]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }


    //
    // QueenBackTrack - Solve the queen's chess problem
    //
    void QueenBackTrack (int [] intChessBoard, int intPlace, int intMove)
    {
        // Declare variables
        int [] intChoice = new int[9];

        // Show debugging info
        if (DebugPrgm)
        {
            System.out.println("QueenBackTrack");
            System.out.format("Place -> %d", intPlace);
            System.out.println(" ");
            System.out.format("Move -> %d", intMove);
            System.out.println(" ");
            System.out.println(" ");
        }

        // Show the total moves - on the status line
        TotMoves++;

        // Try the move
        intChessBoard[intPlace] = intMove;
        MoveSet[intMove] = intPlace;

        // Do not continue to search (esc key)
        if (!QueenFlag)
            return;

        // Tied or beat max moves value
        if (intMove >= MaxMoves)
        {
            MaxMoves = intMove;
        }

        // All done (same as esc)
        if (intMove == 8)
        {
            QueenFlag = false;
            return;
        }

        // Get the next best moves
        TryQueenMove(intChessBoard, intPlace, intChoice);

        // Try all the best moves in rank order
        for (int i = 1; i <= intChoice[0]; i++)
            if (QueenFlag)
                QueenBackTrack(intChessBoard, intChoice[i], intMove + 1);

        // Do not continue to search (esc key)
        if (!QueenFlag)
            return;

        // Erase a bad try
        intChessBoard[intPlace] = 0;
        MoveSet[intMove] = 0;
    }

    //
    // TryQueenMove - Find all the valid moves in the next column for b.t. program
    //
    void TryQueenMove (int [] intChessBoard, int intPlace, int [] intChoice)
    {
        // Declare variables
        int [] intSelectDir = new int[9];
        int col;

        // Initialize the move directions
        for(int i = 0; i < 8; i++)
            intSelectDir[i] = 21 + i;
        intSelectDir[8] = 21;

        // Initialize the choice selection
        for(int i = 0; i < 9; i++)
            intChoice[i] = 0;

        // What is the next col to try?
        col = intSelectDir[ChessCol(intPlace) + 1];

        // What are the valid moves in that column?
        for(int i = 1; i < 9; i++)
        {
            // Try the move
            intChessBoard[col + (i - 1) * 10] = 1;

            // Valid move?
            if (GoodQueenMove(intChessBoard))
            {
                intChoice[0]++;
                intChoice[intChoice[0]] = col + (i - 1) * 10;
            }

            // Undo the move
            intChessBoard[col + (i - 1) * 10] = 0;
        }

    }

    //
    //  GoodQueenMove - determines if any existing queen is checking any other queen.
    //
    Boolean GoodQueenMove(int [] intChessBoard)
    {
        // Declare variables
        int intCount;

        for(int i = 0; i < 8; i++)
        {
            // Check for one queen in each column
            intCount = 0;

            // Found a queen
            for(int j = 0; j < 8; j++)
                if (intChessBoard[ChessSqu(i, j)] > 0)
                    intCount++;

            // Checking queens
            if (intCount > 1)
                return(false);

        }

        // Check one half of the board (left/right diagonals)
        for(int i = 21; i < 29; i++)
        {
            // Local variable to loop
            int j;

            // Right diagonals
            intCount = 0;
            j = i;

            while (intChessBoard[j] != -1)
            {
                // Found a queen
                if (intChessBoard[j] > 0)
                    intCount++;

                // Next square
                j += 9;
            };

            // Checking queens
            if (intCount > 1)
                return(false);

            // Left diagonals
            intCount = 0;
            j = i;

            while (intChessBoard[j] != -1)
            {
                // Found a queen
                if (intChessBoard[j] > 0)
                    intCount++;

                // Next square
                j += 11;
            };

            // Checking queens
            if (intCount > 1)
                return(false);

        }


        // Check one half of the board (right/left diagonals)
        for(int i = 91; i < 99; i++)
        {
            // Local variable to loop
            int j;

            // Right diagonals
            intCount = 0;
            j = i;

            while (intChessBoard[j] != -1)
            {
                // Found a queen
                if (intChessBoard[j] > 0)
                    intCount++;

                // Next square
                j -= 9;
            };

            // Checking queens
            if (intCount > 1)
                return(false);


            // Left diagonals
            intCount = 0;
            j = i;

            while (intChessBoard[j] != -1)
            {
                // Found a queen
                if (intChessBoard[j] > 0)
                    intCount++;

                // Next square
                j -= 11;
            };

            // Checking queens
            if (intCount > 1)
                return(false);
        }

        // Valid move
        return(true);
    }



}


