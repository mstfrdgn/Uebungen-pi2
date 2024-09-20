package de.uni_bremen.pi2;

// INVALID, VALID und SOLVED können direkt verwendet werden.
import static de.uni_bremen.pi2.InsectHotel.fill;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.Test;
/**
@author Simon Berg
*/

public class InsectHotelTest
{
    /**
     * Ein komplexer Test aus dem ursprünglichen Puzzle.
     * Ist standardmäßig deaktiviert.
     */
    @Test
    public void testComplex()
    {
        final String[] puzzle = {
                "X       ",
                " OO     ",
                "   X   O",
                "O       ",
                "  XO  X ",
                "X X     ",
                "     X  ",
                "     X  "
        };
        final String[] solution = {
                "XXOOXOOX",
                "XOOXOOXX",
                "OOXXOXXO",
                "OXOOXXOX",
                "XOXOXOXO",
                "XOXXOOXO",
                "OXOXOXOX",
                "OXXOXXOO"
        };
        assertArrayEquals(solution, InsectHotel.solve(puzzle));
    }


    /**
     * Die Testmethode testet die Check-Methode mit einem korrekt gelösten Insektenhotel.
     *
     * @param result Ergebnis der Check-Methode für das korrekt gelöste Insektenhotel.
     */
    @Test
    public void testInsectHotelCheckMethod_Solved() {
        String[] solvedHotel = {"XOX", "OXO", "XOX"};
        InsectHotel.Result result = InsectHotel.check(solvedHotel);
        System.out.println("Result for solved hotel: " + result);
        assertEquals(InsectHotel.Result.INVALID, result);
    }


    /**
     * Testet die Check-Methode mit einem ungelösten Insektenhotel.
     *
     * @param result Ergebnis der Check-Methode für das ungelöste Insektenhotel.
     */
    @Test
    public void testInsectHotelCheckMethod_Unsolved() {
        String[] unsolvedHotel = {"XOX", " O ", "XOX"};
        InsectHotel.Result result = InsectHotel.check(unsolvedHotel);
        System.out.println("Result for unsolved hotel: " + result);
        assertEquals(InsectHotel.Result.INVALID, result);
    }


    /**
     * Die Testmethode testet die Check-Methode mit einem korrekt gelösten Insektenhotel.
     *
     * @param result Ergebnis der Check-Methode für das korrekt gelöste Insektenhotel.
     */
    @Test
    public void testCheck_Valid() {
        String[] hotel = {" O ", "X X", " O "};
        assertEquals(InsectHotel.Result.VALID, InsectHotel.check(hotel));
    }


    /**
     * Testet die Check-Methode mit einem ungelösten Insektenhotel.
     *
     * @param result Ergebnis der Check-Methode für das ungelöste Insektenhotel.
     */
    @Test
    public void testCheck_Invalid() {
        String[] hotel = {"OOO", "XXX", " O "};
        assertEquals(InsectHotel.Result.INVALID, InsectHotel.check(hotel));
    }


    /**
     * Testet due Solve-Methode mit einem unlösbaren Insektenhotel.
     * @param hotel Ein nicht lösbares Insektenhotel.
     */
    @Test
    public void testSolve_NoSolution() {
        String[] hotel = {"OOO", "XXX", " O "};
        assertNull(InsectHotel.solve(hotel));
    }


    /**
     * Testet die Fill-Methode mit leeren Einträgen.
     * @param entries Leere Einträge als String.
     */
    @Test
    public void testFill_EmptyEntries() {
        String[] hotel = {"  O", "X  ", "  X"};
        String entries = "";
        String[] expected = hotel.clone();
        String[] actual = fill(hotel, entries);
        assertArrayEquals(expected, actual);
    }


    /**
     * Testet die Solve-Methode mit einem unlösbaren Insektenhotel.
     * @param hotel Ein unlösbares Insektenhotel als 2D-Array von Strings.
     */
    @Test
    public void testSolve_Unsolvable() {
        String[] hotel = {"  O", "XXX", "  X"};
        assertNull(InsectHotel.solve(hotel));
    }


    /**
     * Testet die Fill-Methode mit einem leeren Insektenhotel und Einträgen.
     *
     * @param hotel Ein leeres Insektenhotel als 2D-Array von Strings.
     * @param entries Einträge als String.
     */
    @Test
    public void testFillEmptyHotel() {
        String[] hotel = {"     ", "     ", "     "};
        String entries = "OOXX";
        String[] expected = {"OOXX ", "     ", "     "};
        assertArrayEquals(expected, fill(hotel, entries));
    }


    /**
     * Testet die Fill-Methode mit einem belegten Insektenhotel und keinen Einträgen.
     *
     * @param hotel Ein belegtes Insektenhotel als 2D-Array von Strings.
     * @param entries keine Einträge als String.
     */
    @Test
    public void testFillHotelWithNoEntries() {
        String[] hotel = {"OX    ", " X    ", "     X"};
        String entries = "";
        String[] expected = {"OX    ", " X    ", "     X"};
        assertArrayEquals(expected, fill(hotel, entries));
    }





    /**
     * Java Doc fehlt
     *
     @Test
     public void testSolve_HasSolution() {
     String[] hotel = {"  O", "X X", " O "};
     String[] expected = {"OXO", "XOX", "OXO"};
     String[] actual = InsectHotel.solve(hotel);
     assertNotNull(actual);
     assertArrayEquals(expected, actual);

     }
     */

    /**
     *
     *
     @Test
     public void testCheck_Solved() {
     String[] hotel = {"OXO"};
     InsectHotel.Result result = InsectHotel.check(hotel);
     assertEquals(InsectHotel.Result.SOLVED, result);

     }
     */

    /**
     @Test
     public void testSolve() {
     String[] hotel = {"  O", "X X", " O "};
     String[] expected = {"OXO", "XOX", "OXO"};
     assertArrayEquals(expected, InsectHotel.solve(hotel));
     }
     */

    /**
     * Testet die Fill Methode.
     @Test
     public void testFill() {
     String[] hotel = {"  O", "X X", " O "};
     String entries = "OXOX";
     String[] expected = {"OXO", "XOX", "XO"};
     assertArrayEquals(expected, fill(hotel, entries));
     }
     */

    /**
     * Testet die Check-Methode.
     *
     @Test
     public void testCheck() {
     String[] validHotel = {"XOX", "OXO", "XOX"};
     String[] invalidHotel = {"XOX", " O ", "XOX"};

     InsectHotel.Result validationResultValid = InsectHotel.check(validHotel);
     InsectHotel.Result validationResultInvalid = InsectHotel.check(invalidHotel);

     assertEquals(InsectHotel.Result.VALID, validationResultValid);
     assertEquals(InsectHotel.Result.INVALID, validationResultInvalid);
     }
     */


}

