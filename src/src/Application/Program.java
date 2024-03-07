package Application;

import Xadrez.ChessMatch;
import Xadrez.ChessPiece;
import Xadrez.ChessPosition;
import tabuleiro.Board;
import tabuleiro.Position;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.println("origem: ");
            ChessPosition source = UI.readChessPosition(sc);

            System.out.println();
            System.out.println("destino: ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece capturedPiece = chessMatch.performanceMove(source, target);

        }
    }
}
