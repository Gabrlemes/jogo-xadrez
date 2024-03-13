package Xadrez;

import tabuleiro.Board;
import tabuleiro.Piece;
import tabuleiro.Position;
import Xadrez.pieces.*;

import java.util.ArrayList;
import java.util.List;


public class ChessMatch {

    private Board board;
    private int turn;
    private Color currentPlayer;

    private List<Piece> pieceOnBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                   mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPieces != null) {
            pieceOnBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if(!board.thereIsAPiece(position)) {
            throw new ChessExeption("não existe peça na posição de origem. ");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
            throw new ChessExeption("Está peça não é sua. ");
        }

        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessExeption("não existe moviemtno possivel para esta peça no momento.");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessExeption("a peça escolhida não pode mover para este destino.");
        }
    }

    //troca de jogador.

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        pieceOnBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('a', 2, new Peao(board, Color.WHITE));
        placeNewPiece('b', 2, new Peao(board, Color.WHITE));
        placeNewPiece('c', 2, new Peao(board, Color.WHITE));
        placeNewPiece('d', 2, new Peao(board, Color.WHITE));
        placeNewPiece('e', 2, new Peao(board, Color.WHITE));
        placeNewPiece('f', 2, new Peao(board, Color.WHITE));
        placeNewPiece('g', 2, new Peao(board, Color.WHITE));
        placeNewPiece('h', 2, new Peao(board, Color.WHITE));
        placeNewPiece('h', 1, new Torre(board, Color.WHITE));
        placeNewPiece('a', 1, new Torre(board, Color.WHITE));
        placeNewPiece('e', 1, new Rei(board, Color.WHITE));

        placeNewPiece('a', 7, new Peao(board, Color.WHITE));
        placeNewPiece('b', 7, new Peao(board, Color.WHITE));
        placeNewPiece('c', 7, new Peao(board, Color.WHITE));
        placeNewPiece('d', 7, new Peao(board, Color.WHITE));
        placeNewPiece('e', 7, new Peao(board, Color.WHITE));
        placeNewPiece('f', 7, new Peao(board, Color.WHITE));
        placeNewPiece('g', 7, new Peao(board, Color.WHITE));
        placeNewPiece('h', 7, new Peao(board, Color.WHITE));
        placeNewPiece('h', 8, new Torre(board, Color.WHITE));
        placeNewPiece('a', 8, new Torre(board, Color.WHITE));
        placeNewPiece('e', 8, new Rei(board, Color.WHITE));
    }
}
