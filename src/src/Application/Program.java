package Application;

import Xadrez.ChessExeption;
import Xadrez.ChessMatch;
import Xadrez.ChessPiece;
import Xadrez.ChessPosition;
import tabuleiro.Board;
import tabuleiro.Position;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.println("origem: ");
                ChessPosition source = UI.readChessPosition(sc);

                System.out.println();
                System.out.println("destino: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performanceMove(source, target);
            }
            catch (ChessExeption e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }
    }
}
