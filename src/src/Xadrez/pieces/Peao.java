package Xadrez.pieces;

import Xadrez.ChessPiece;
import Xadrez.Color;
import tabuleiro.Board;
import tabuleiro.Position;

    public class Peao extends ChessPiece {
        public Peao(Board board, Color color) {
            super(board, color);
        }

        @Override
        public String toString() {
            return "P";
        }

        private boolean canMove(Position position) {
            ChessPiece p = (ChessPiece) getBoard().piece(position);
            return p == null || p.getColor() != getColor();
        }

        @Override
        public boolean[][] possibleMoves() {
            boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

            Position p = new Position(0, 0);

            //movendo para cima.
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            //movendo para baixo.
            p.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            //movendo para esquerda.
            p.setValues(position.getRow(), position.getColumn() - 1);
            if (getBoard().positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            //movendo para direita.
            p.setValues(position.getRow(), position.getColumn() + 1);
            if (getBoard().positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            return mat;
        }
    }
