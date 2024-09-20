package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static de.uni_bremen.pi2.Player.*;
import static de.uni_bremen.pi2.Result.*;

/**
 * @author simonberg
 */

public class FourInARowTest {

    /**
     * Erzeuge ein Spielfeld aus einem String. Zeilen werden durch '\n' getrennt,
     * wobei am Ende kein '\n' steht. '.' repräsentiert leeres Feld, 'X' Steine
     * der menschlichen Spieler*in und 'O' Steine des Computers.
     *
     * @param string Eine Zeichenkette in demselben Format, in dem auch die
     *               toString-Methode des Spiels das Spielfeld darstellt.
     * @return Ein Spielfeld mit den zur Eingabe passenden Belegungen.
     */
    private Player[][] asField(final String string) {
        String[] rows = string.split("\n");
        Player[][] field = new Player[rows.length][rows[0].length()];

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                switch (rows[i].charAt(j)) {
                    case 'X':
                        field[i][j] = HUMAN;
                        break;
                    case 'O':
                        field[i][j] = COMPUTER;
                        break;
                    default:
                        field[i][j] = EMPTY;
                        break;
                }
            }
        }
        return field;
    }

    /**
     * Test, ob der Computer den besten Zug zwischen 2 möglichen Zügen wählt mittels computerMove() bzw. indirekt auch den negaMax-Algorithmus
     */
    @Test
    public void computerOptimalMove() {
        Player[][] player = asField("X.OO\nOOXO\nOOO.\nOXOO");
        FourInARow fourInARow = new FourInARow(player, 1);
        fourInARow.computerMove();

        assertEquals("X.OO\nOOXO\nOOOO\nOXOO", fourInARow.toString());
    }

    /**
     * Test, ob der Computer auch sein 'O' in die Ecke des Feldes setzen kann
     */
    @Test
    public void computerOptimalMoveCorner() {
        Player[][] player = asField("..OO\nOXXO\nOXXO\nOXOX");
        FourInARow fourInARow = new FourInARow(player, 2);
        fourInARow.computerMove();

        assertEquals("O.OO\nOXXO\nOXXO\nOXOX", fourInARow.toString());

    }

    /**
     * Test, ob der menschliche Spieler sein Symbol richtig im Spielfeld setzt
     */
    @Test
    public void humanCanMove() {
        Player[][] player = asField("...XX\nOO...\n...XO\n..XX.\n..O.O");
        FourInARow fourInARow = new FourInARow(player, 1);
        fourInARow.humanMove(0, 0);

        assertEquals("X..XX\nOO...\n...XO\n..XX.\n..O.O", fourInARow.toString());

    }

    /**
     * Test, ob der menschliche Spieler gewinnt, falls 4 senkrecht, waagerecht, diagonal von oben links nach unten rechts oder
     * diagonal von links unten nach rechts oben gewinnt und entsprechend das richtige Resultat zurückgegeben wird
     */

    @Test
    public void humanWinsResult() {

        // senkrecht

        Player[][] field = asField("X....\nX....\nX....\n.....\n.....");
        FourInARow game = new FourInARow(field, 1);

        assertEquals(HUMAN_WON, game.humanMove(3, 0));


        // waagerecht

        field = asField("XXX..\n.....\n.....\n.....\n.....");
        game = new FourInARow(field, 1);

        assertEquals(HUMAN_WON, game.humanMove(0, 3));

        // downright

        field = asField("X....\n.X...\n..X..\n.....\n.....");
        game = new FourInARow(field, 1);

        assertEquals(HUMAN_WON, game.humanMove(3, 3));

        // upright

        field = asField("....X\n...X.\n..X..\n.....\n.....");
        game = new FourInARow(field, 1);

        assertEquals(HUMAN_WON, game.humanMove(3, 1));

    }

    /**
     * Test, ob der Computer gewinnt, falls 4 seiner Züge senkrecht,waagerecht oder diagonal liegen und dann das
     * richtige Resultat, also Computer hat gewonnen,  zurückgegeben wird
     */

    @Test
    public void computerWinsResult() {

        // senkrecht

        Player[][] field = asField("O....\nO....\nO....\n.....\n.....");
        FourInARow game = new FourInARow(field, 1);

        assertEquals(COMPUTER_WON, game.computerMove());


        // waagerecht

        field = asField("OOO..\n.....\n.....\n.....\n.....");
        game = new FourInARow(field, 1);

        assertEquals(COMPUTER_WON, game.computerMove());

        // downright

        field = asField("O....\n.O...\n..O..\n.....\n.....");
        game = new FourInARow(field, 1);

        assertEquals(COMPUTER_WON, game.computerMove());

        // upright

        field = asField("....O\n...O.\n..O..\n.....\n.....");
        game = new FourInARow(field, 1);

        assertEquals(COMPUTER_WON, game.computerMove());

    }

    /**
     * Test, ob DRAW zurückgegeben wird, falls das aktuelle Spiefeld beim nächsten Zug des menschlichen Spielers / Computers zu einem Unentschieden führt
     */


    @Test

    public void resultDraw() {

        Player[][] field = asField("XOXOX\nOXOOX\nOOOXX\nXXXOO\nXOO.X");
        FourInARow game = new FourInARow(field, 1);
        assertEquals(DRAW, game.humanMove(4, 3));


        field = asField("XOXOX\nOXOOX\nOOOXX\nXXXOO\nXOO.X");
        game = new FourInARow(field, 1);
        assertEquals(DRAW, game.computerMove());


    }

    /**
     * Test, ob nach einem Zug des menschlichen Spielers / des Computers als Resultat ein CONTINUE zurückgegeben wird, falls das Spiel noch nicht zu Ende ist
     */

    @Test

    public void gameContinues() {

        Player[][] field = asField(".....\n.....\n.....\n.....\n.....");
        FourInARow game = new FourInARow(field, 3);


        assertEquals(CONTINUE, game.humanMove(0, 0));
        assertEquals(CONTINUE, game.computerMove());


    }



    @Test
    void testHumanMove() {
        Player[][] field = asField(
                "....\n" +
                        "....\n" +
                        "....\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(CONTINUE, game.humanMove(0, 0));
        assertEquals(HUMAN, field[0][0]);
    }

    @Test
    void testComputerMove() {
        Player[][] field = asField(
                "....\n" +
                        "....\n" +
                        "....\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(CONTINUE, game.computerMove());
    }

    @Test
    void testHumanWin() {
        Player[][] field = asField(
                "....\n" +
                        "....\n" +
                        "XXXX\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(HUMAN_WON, game.humanMove(3, 3));
    }


    @Test
    void testComputerWin() {
        Player[][] field = asField(
                "....\n" +
                        "....\n" +
                        "OOOO\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(COMPUTER_WON, game.computerMove());
    }




}