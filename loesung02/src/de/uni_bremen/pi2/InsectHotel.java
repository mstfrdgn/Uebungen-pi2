package de.uni_bremen.pi2;


// INVALID, VALID und SOLVED können direkt verwendet werden.
import static de.uni_bremen.pi2.InsectHotel.Result.*;
/**
@author mustafaerdogan
*/

public class InsectHotel
{
    /**
     * Die drei möglichen Ergebnisse der Methode {@link #check}.
     * INVALID: Die aktuelle Teilbelegung des Hotels ist bereits ungültig.
     * VALID: Die aktuelle Teilbelegung des Hotels ist gültig.
     * SOLVED: Das Hotel ist voll belegt und gültig.
     */
    enum Result {INVALID, VALID, SOLVED}

    /**
     * Die Methode füllt ein zum Teil belegtes Hotel mit weiteren
     * Einträgen auf. Dabei werden die weiteren Einträge der Reihe nach
     * von oben links nach unten rechts eingetragen (horizontal zuerst).
     * Dabei bleiben die existierenden Einträge erhalten und die neuen
     * Einträge ersetzen nur die Lücken (' ').
     * @param hotel Das zum Teil belegte Hotel. Wird nicht verändert.
     *              {@see #solve}.
     * @param entries Eine Folge aus 'O' und 'X', die in die Lücken in
     *                {@code hotel} eintragen wird.
     * @return Ein Hotel, bei dem {@code entries} in die Lücken von
     * {@code hotel} eingesetzt wurde, zumindest so weit, wie
     * {@code entries} gereicht hat. Da {@code hotel} nicht geändert
     * werden darf, ist dies ein neues Objekt.
     */
    static String[] fill(final String[] hotel, final String entries)
    {
        String[] newHotel = new String[hotel.length];  // Cloned array
        int entryIndex = 0;  // To track the current position in the entries string

        for (int i = 0; i < hotel.length; i++) {
            StringBuilder newRow = new StringBuilder(hotel[i]);  // Work with a mutable StringBuilder
            for (int j = 0; j < newRow.length(); j++) {
                if (newRow.charAt(j) == ' ' && entryIndex < entries.length()) {
                    newRow.setCharAt(j, entries.charAt(entryIndex++));  // Replace space with the next entry
                }
            }
            newHotel[i] = newRow.toString();  // Convert StringBuilder back to String and assign to the new array
        }

        return newHotel;  // Return the newly filled hotel
    }


    /**
     * Die Methode überprüft, ob ein Hotel gültig (teil-) belegt ist.
     * Es dürfen horizontal und vertikal maximal jeweils zwei gleiche
     * Einträge ('O' bzw. 'X') aufeinander folgen. In jeder voll
     * ausgefüllten Zeile und Spalte müssen gleich viele Einträge beider
     * Sorten stehen.
     * @param hotel Das zum Teil belegte Hotel. {@see #solve}.
     * @return
     * INVALID: Wenn eine der Anforderungen nicht erfüllt ist – oder
     * besser – auch mit späteren Eintragungen nicht mehr erfüllt
     * werden kann.
     * VALID: Bisher ist keine Anforderung verletzt und mit weiteren
     * Eintragungen könnte noch eine gültige Lösung entstehen.
     * SOLVED: Das Hotel ist vollständig belegt und keine der
     * Anforderungen ist verletzt.
     */


    static Result check(final String[] hotel) {
        boolean isCompletelyFilled = true;

        for (String row : hotel) {
            if (!isRowValid(row)) {
                return INVALID;
            }
            if (row.contains(" ")) {
                isCompletelyFilled = false;
            }
        }

        for (int col = 0; col < hotel[0].length(); col++) {
            if (!isColumnValid(hotel, col)) {
                return INVALID;
            }
        }

        return isCompletelyFilled ? SOLVED : VALID;
    }

    private static boolean isRowValid(String row) {
        return isLineValid(row) && isBalanced(row);
    }

    private static boolean isColumnValid(String[] hotel, int colIndex) {
        StringBuilder column = new StringBuilder();
        for (String row : hotel) {
            column.append(row.charAt(colIndex));
        }
        return isLineValid(column.toString()) && isBalanced(column.toString());
    }

    private static boolean isLineValid(String line) {
        int count = 1;
        char lastChar = line.charAt(0);
        for (int i = 1; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (currentChar == lastChar && currentChar != ' ') {
                count++;
                if (count > 2) {
                    return false;
                }
            } else {
                lastChar = currentChar;
                count = 1;
            }
        }
        return true;
    }

    private static boolean isBalanced(String line) {
        if (line.contains(" ")) {
            return true;  // Skip balance checking if the line is not fully filled
        }
        long countX = line.chars().filter(ch -> ch == 'X').count();
        long countO = line.chars().filter(ch -> ch == 'O').count();
        return countX == countO;
    }





    /**
     * Die Methode bekommt ein zum Teil belegtes Hotel übergeben und gibt
     * ein gültig voll belegtes Hotel zurück, wenn dies möglich ist. Die
     * Details, was "gültig" bedeutet, stehen auf dem Übungsblatt.
     * @param hotel Das zum Teil belegte Hotel. Es besteht aus den Zeichen
     *              ' ', 'O' und 'X'. Alle Zeilen müssen dieselbe Länge
     *              haben. Weder der Parameter noch eine seiner Zeilen
     *              dürfen null sein. Wenn von diesen Vorgaben, abgewichen
     *              wird, ist das Verhalten undefiniert.
     * @return Ein gültig voll belegtes Hotel oder null, wenn es keine
     * gültige Belegung gibt.
     */
    public static String[] solve(final String[] hotel) {
        String[] solution = new String[hotel.length];
        System.arraycopy(hotel, 0, solution, 0, hotel.length);  // Copy hotel to solution to preserve original

        if (solveRecursive(solution, 0)) {
            return solution;
        } else {
            return null;  // No solution found
        }
    }

    private static boolean solveRecursive(String[] hotel, int cellIndex) {
        if (cellIndex == hotel.length * hotel[0].length()) {  // Check if we're past the last cell
            return check(hotel) == Result.SOLVED;  // Check if the solution is complete and valid
        }

        int row = cellIndex / hotel[0].length();
        int col = cellIndex % hotel[0].length();
        String currentRow = hotel[row];

        if (currentRow.charAt(col) != ' ') {  // Skip already filled cells
            return solveRecursive(hotel, cellIndex + 1);
        }

        char[] candidates = {'X', 'O'};
        for (char candidate : candidates) {
            hotel[row] = currentRow.substring(0, col) + candidate + currentRow.substring(col + 1);
            if (check(hotel) != Result.INVALID) {
                if (solveRecursive(hotel, cellIndex + 1)) {
                    return true;
                }
            }
            hotel[row] = currentRow;  // Reset the row after backtracking
        }

        return false;
    }

}