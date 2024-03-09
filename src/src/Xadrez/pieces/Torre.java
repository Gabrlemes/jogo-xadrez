package Xadrez.pieces;

import Xadrez.ChessPiece;
import Xadrez.Color;
import tabuleiro.Board;
import tabuleiro.Position;

public class Torre extends ChessPiece {
    public Torre(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "T";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0,0);

        //movendo para cima.

        p.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPeace(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }
}
