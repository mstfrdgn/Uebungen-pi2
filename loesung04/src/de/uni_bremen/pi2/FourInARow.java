package de.uni_bremen.pi2;

import java.util.ArrayList;
import java.util.List;

// Die Konstanten beider Aufzählungstypen lassen sich direkt verwenden.
import static de.uni_bremen.pi2.Player.*;
import static de.uni_bremen.pi2.Result.*;

/* @Author Mustafa Erdogan */

public class FourInARow
{
    /** Das Spielfeld. */
    private final Player[][] field;
    private final int depth;


    /**
     * Konstruktor.
     * @param field Das Spielfeld. Muss quadratisch sein.
     * @param depth Die maximale Suchtiefe.
     */
    public FourInARow(final Player[][] field, final int depth)
    {
        this.field = field;
        this.depth = depth;
        // Erweitern
    }

    /**
     * Führt den menschlichen Zug aus. Es wird erwartet, dass die übergebenen
     * Koordinaten gültig sind und das bezeichnete Feld noch frei ist. Dies
     * wird nicht überprüft.
     * @param row Die Zeile, in der ein Stein platziert wird.
     * @param column Die Spalte, in der ein Stein platziert wird.
     * @return Das Ergebnis des Zugs.
     */
    public Result humanMove(int row, int column)
    {
        field[row][column] = HUMAN;
        return checkResult(HUMAN);

        // Ersetzen
    }

    /**
     * Lässt den Computer seinen Zug machen.
     * @return Das Ergebnis des Zugs.
     */
    public Result computerMove()
    {
        Move bestMove = minimax(depth, COMPUTER, Integer.MIN_VALUE, Integer.MAX_VALUE).move;
        field[bestMove.getRow()][bestMove.getColumn()] = COMPUTER;
        return checkResult(COMPUTER);

        // Ersetzen
    }
    private Result checkResult(Player player) {
        if (hasWon(player)) {
            return (player == HUMAN) ? HUMAN_WON : COMPUTER_WON;
        }
        if (isDraw()) {
            return DRAW;
        }
        return CONTINUE;
    }

    private boolean hasWon(Player player) {
        int size = field.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (checkLine(player, row, col, 1, 0) || // Horizontal
                        checkLine(player, row, col, 0, 1) || // Vertical
                        checkLine(player, row, col, 1, 1) || // Diagonal down-right
                        checkLine(player, row, col, 1, -1))  // Diagonal up-right
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkLine(Player player, int row, int col, int dRow, int dCol) {
        int count = 0;
        int size = field.length;

        for (int i = 0; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < size && c >= 0 && c < size && field[r][c] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }

    private boolean isDraw() {
        for (Player[] row : field) {
            for (Player cell : row) {
                if (cell == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }


    private List<Move> getAvailableMoves() {
        List<Move> moves = new ArrayList<>();
        for (int row = 0; row < field.length; row++) {
            for (int column = 0; column < field[row].length; column++) {
                if (field[row][column] == EMPTY) {
                    moves.add(new Move(row, column, 0));
                }
            }
        }
        return moves;
    }

    public int evaluate() {
        if (hasWon(COMPUTER)) return 100;
        if (hasWon(HUMAN)) return -100;
        return 0;
    }

    private MinimaxResult minimax(int depth, Player player, int alpha, int beta) {
        List<Move> availableMoves = getAvailableMoves();


        if (depth == 0 || availableMoves.isEmpty() || hasWon(COMPUTER) || hasWon(HUMAN)) {
            return new MinimaxResult(null, evaluate());
        }

        Move bestMove = null;
        int bestScore = (player == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : availableMoves) {
            field[move.getRow()][move.getColumn()] = player;
            int score = minimax(depth - 1, (player == COMPUTER) ? HUMAN : COMPUTER, alpha, beta).score;
            field[move.getRow()][move.getColumn()] = EMPTY;

            if (player == COMPUTER) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                alpha = Math.max(alpha, score);
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                beta = Math.min(beta, score);
            }
            if (beta <= alpha) break;
        }
        return new MinimaxResult(bestMove, bestScore);
    }


    private static class MinimaxResult {
        final Move move;
        final int score;

        MinimaxResult(Move move, int score) {
            this.move = move;
            this.score = score;
        }

    }



    /**
         * Die Darstellung des Spielfelds.
         * @return Die Darstellung als mehrzeilige Zeichenkette.
         */
    @Override
    public String toString()
    {
        final StringBuilder string = new StringBuilder();
        String separator = "";
        for (final Player[] row : field) {
            string.append(separator);
            separator = "\n";
            for (final Player player : row) {
                string.append(player);
            }
        }
        return string.toString();
    }
}
