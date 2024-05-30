package Xadrez.pieces;

import Xadrez.ChessMatch;
import Xadrez.ChessPiece;
import Xadrez.Color;
import tabuleiro.Board;
import tabuleiro.Position;

    public class Peao extends ChessPiece {

        private ChessMatch chessMatch;

        public Peao(Board board, Color color, ChessMatch chessMatch) {
            super(board, color);
            this.chessMatch = chessMatch;
        }

//        private boolean canMove(Position position) {
//            ChessPiece p = (ChessPiece) getBoard().piece(position);
//            return p == null || p.getColor() != getColor();
//        }

        @Override
        public boolean[][] possibleMoves() {
            boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

            Position p = new Position(0, 0);

            //movendo para cima.
            if (getColor() == Color.WHITE) {
                p.setValues(position.getRow() - 1, position.getColumn());
                if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                    mat[p.getRow()][p.getColumn()] = true;
                }
                //primeiro moviemento 2 casas a frente.
                p.setValues(position.getRow() - 2, position.getColumn());
                Position p2 = new Position(position.getRow() - 1, position.getColumn());
                if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                    mat[p.getRow()][p.getColumn()] = true;
                }

                p.setValues(position.getRow() -1, position.getColumn() -1);
                if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                    mat[p.getRow()][p.getColumn()] = true;
                }

                p.setValues(position.getRow() -1, position.getColumn() +1);
                if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                    mat[p.getRow()][p.getColumn()] = true;
                }

                // #specialmove en passant branco
                if (position.getRow() == 3) {
                    Position left = new Position(position.getRow(), position.getColumn() - 1);
                    if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                        mat[left.getRow() - 1][left.getColumn()] = true;
                    }
                    Position right = new Position(position.getRow(), position.getColumn() + 1);
                    if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                        mat[right.getRow() - 1][right.getColumn()] = true;
                    }
                }
            }
            else {
                p.setValues(position.getRow() +1, position.getColumn());
                if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                    mat[p.getRow()][p.getColumn()] = true;
                }
                //primeiro moviemento 2 casas a frente.
                p.setValues(position.getRow() +2, position.getColumn());
                Position p2 = new Position(position.getRow() + 1, position.getColumn());
                if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                    mat[p.getRow()][p.getColumn()] = true;
                }

                p.setValues(position.getRow() +1, position.getColumn() -1);
                if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                    mat[p.getRow()][p.getColumn()] = true;
                }

                p.setValues(position.getRow() +1, position.getColumn() +1);
                if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                    mat[p.getRow()][p.getColumn()] = true;

                    // #specialmove en passant preto
                    if (position.getRow() == 4) {
                        Position left = new Position(position.getRow(), position.getColumn() - 1);
                        if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                            mat[left.getRow() + 1][left.getColumn()] = true;
                        }
                        Position right = new Position(position.getRow(), position.getColumn() + 1);
                        if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                            mat[right.getRow() + 1][right.getColumn()] = true;
                        }
                    }
                }

            }
            return mat;
        }
        @Override
        public String toString() {
            return "P";
        }
    }
