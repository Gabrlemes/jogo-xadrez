package Xadrez.pieces;

import Xadrez.ChessPiece;
import Xadrez.Color;
import tabuleiro.Board;

public class Torre extends ChessPiece {
    public Torre(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "T";
    }
}
