package tabuleiro;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 && columns < 1) {
            throw new BoardException("Erro em criar o tabuleiro: mínimo de 1 linha e 1 coluna para a criação.");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    //posição não encontrada no tabuleiro.

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Posição não encontrada no tabuleiro.");
        }
        return pieces[row][column];
    }
    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posição não encontrada no tabuleiro.");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    //
    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("já existe uma peça na posição: " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    //removedor de peças

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("posição inexistente no tabuleiro. ");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    //precaução contra tabuleiros menosres que 1

    private boolean positionExists(int row, int column){
        return row >= 0 && row < rows && columns >= 0 && column < columns;
    }
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position){
        if (!positionExists(position)) {
            throw new BoardException("posição não encontrada.");
        }
        return piece(position) != null;
    }
}
