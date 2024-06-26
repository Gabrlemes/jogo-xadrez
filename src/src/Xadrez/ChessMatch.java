package Xadrez;

import tabuleiro.Board;
import tabuleiro.Piece;
import tabuleiro.Position;
import Xadrez.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ChessMatch {

    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private ChessPiece promoted;
    private ChessPiece enPassantVulnerable;

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

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
    }

    public ChessPiece getPromoted(){
        return promoted;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                   mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }


    //movimentos possiveis
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

        //teste de auto-check
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessExeption("Você não pode se por em check");
        }

        ChessPiece movedPiece = (ChessPiece)board.piece(target);

        //movimento especial promoção
        promoted = null;
        if(movedPiece instanceof Peao) {
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
                promoted = (ChessPiece)board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if(testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        }
        else {
            nextTurn();
        }
        if (movedPiece instanceof Peao && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }
        return (ChessPiece) capturedPiece;
    }

    //peao virando rainha
    public ChessPiece replacePromotedPiece(String type){
        if (promoted == null) {
            throw new IllegalStateException("não há peça para ser promovida.");
        }
        if (!type.equals("B") && !type.equals("C") && !type.equals("T") & !type.equals("R")) {
            return promoted;
        }

        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        pieceOnBoard.remove(p);

        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, pos);
        pieceOnBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color){
        if (type.equals("B")) return new Bispo(board, color);
        if (type.equals("C")) return new Cavalo(board, color);
        if (type.equals("R")) return new Rainha(board, color);
        return new Torre(board, color);
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPieces != null) {
            pieceOnBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        //movimento especial torre direita
        if (p instanceof Rei && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position((source.getRow()), source.getColumn() + 3);
            Position targetT = new Position((source.getRow()), source.getColumn() + 1);
            ChessPiece torre = (ChessPiece)board.removePiece(sourceT);
            board.placePiece(torre, targetT);
            torre.increaseMoveCount();
        }

        //movimento especial torre esquerda
        if (p instanceof Rei && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position((source.getRow()), source.getColumn() - 4);
            Position targetT = new Position((source.getRow()), source.getColumn() - 1);
            ChessPiece torre = (ChessPiece)board.removePiece(sourceT);
            board.placePiece(torre, targetT);
            torre.increaseMoveCount();
        }
        if (p instanceof Peao) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                pieceOnBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p,source);

        if(capturedPiece != null) {
        board.placePiece(capturedPiece, target);
        capturedPieces.remove(capturedPiece);
        pieceOnBoard.add(capturedPiece);
        }

        //movimento especial torre direita
        if (p instanceof Rei && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position((source.getRow()), source.getColumn() + 3);
            Position targetT = new Position((source.getRow()), source.getColumn() + 1);
            ChessPiece torre = (ChessPiece)board.removePiece(targetT);
            board.placePiece(torre, sourceT);
            torre.decreaseMoveCount();
        }

        //movimento especial torre esquerda
        if (p instanceof Rei && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position((source.getRow()), source.getColumn() - 4);
            Position targetT = new Position((source.getRow()), source.getColumn() - 1);
            ChessPiece torre = (ChessPiece)board.removePiece(targetT);
            board.placePiece(torre, sourceT);
            torre.decreaseMoveCount();
        }
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

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece rei(Color color) {
        List<Piece> list = pieceOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof Rei) {
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("Nâo existe o rei " + color + ".");
    }

    //teste de check
    private boolean testCheck(Color color) {
        Position reiPosition = rei(color).getChessPosition().toPosition();
        List<Piece>opponentPieces = pieceOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p :opponentPieces) {
            boolean [][] mat = p.possibleMoves();
            if (mat[reiPosition.getRow()][reiPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    //teste de checkMate
    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = pieceOnBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i=0; i<board.getRows(); i++) {
                for (int j=0; j<board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        pieceOnBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('a', 2, new Peao(board, Color.BLACK, this));
        placeNewPiece('b', 2, new Peao(board, Color.BLACK, this));
        placeNewPiece('c', 2, new Peao(board, Color.BLACK, this));
        placeNewPiece('d', 2, new Peao(board, Color.BLACK, this));
        placeNewPiece('e', 2, new Peao(board, Color.BLACK, this));
        placeNewPiece('f', 2, new Peao(board, Color.BLACK, this));
        placeNewPiece('g', 2, new Peao(board, Color.BLACK, this));
        placeNewPiece('h', 2, new Peao(board, Color.BLACK, this));
        placeNewPiece('h', 1, new Torre(board, Color.BLACK));
        placeNewPiece('a', 1, new Torre(board, Color.BLACK));
        placeNewPiece('b', 1, new Cavalo(board, Color.BLACK));
        placeNewPiece('g', 1, new Cavalo(board, Color.BLACK));
        placeNewPiece('c', 1, new Bispo(board, Color.BLACK));
        placeNewPiece('f', 1, new Bispo(board, Color.BLACK));
        placeNewPiece('d', 1, new Rei(board, Color.BLACK, this));
        placeNewPiece('e', 1, new Rainha(board, Color.BLACK));

        placeNewPiece('a', 7, new Peao(board, Color.WHITE, this));
        placeNewPiece('b', 7, new Peao(board, Color.WHITE, this));
        placeNewPiece('c', 7, new Peao(board, Color.WHITE, this));
        placeNewPiece('d', 7, new Peao(board, Color.WHITE, this));
        placeNewPiece('e', 7, new Peao(board, Color.WHITE, this));
        placeNewPiece('f', 7, new Peao(board, Color.WHITE, this));
        placeNewPiece('g', 7, new Peao(board, Color.WHITE, this));
        placeNewPiece('h', 7, new Peao(board, Color.WHITE, this));
        placeNewPiece('h', 8, new Torre(board, Color.WHITE));
        placeNewPiece('a', 8, new Torre(board, Color.WHITE));
        placeNewPiece('b', 8, new Cavalo(board, Color.WHITE));
        placeNewPiece('g', 8, new Cavalo(board, Color.WHITE));
        placeNewPiece('c', 8, new Bispo(board, Color.WHITE));
        placeNewPiece('f', 8, new Bispo(board, Color.WHITE));
        placeNewPiece('d', 8, new Rei(board, Color.WHITE, this));
        placeNewPiece('e', 8, new Rainha(board, Color.WHITE));
    }
}
