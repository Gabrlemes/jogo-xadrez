package Xadrez.pieces;

import Xadrez.ChessPiece;
import Xadrez.Color;
import tabuleiro.Board;
import tabuleiro.Position;

public class Cavalo extends ChessPiece {
    public Cavalo(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "C";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);




        return mat;
    }
}