package Xadrez.pieces;

import Xadrez.ChessPiece;
import Xadrez.Color;
import tabuleiro.Board;

public class Rei extends ChessPiece {
    public Rei(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
