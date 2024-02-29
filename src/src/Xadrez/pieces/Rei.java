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
}
