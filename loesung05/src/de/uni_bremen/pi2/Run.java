package de.uni_bremen.pi2;

/**
 *
 * Instanzen dieser Klasse repräsentieren einen horizontalen weißen Streifen des Bildes
 *
 * @author Simon Berg
 * @author Mustafa Erdogan
 */
public class Run {


    /**
     * Die x-Koordinate des linken Endes des Streifens
     */
    final private int xStart;

    /**
     * Die x-Koordinate der Position unmittelbar rechts hinter dem Streifen
     */
    final private int xEnd;

    /**
     * Die y-Koordinate, in der sich ein Streifen im Bild befindet
     */
    final private int y;

    /**
     * Die Elternwurzel des Runs bzw. ein übergeordneter (Eltern-) Run
     * Ist zu Beginn der Run selbst
     *
     */
    private Run parent;

    //private Run root = this;

    /**
     * Konstruktor.
     *
     * @param xStart  die x-Koordinate des linken Endes des Streifens
     * @param xEnd  die x-Koordinate der Position unmittelbar rechts hinter dem Streifen
     * @param y  die y-Koordinate, in der sich ein Streifen im Bild befindet
     */
    public Run(final int xStart, final int xEnd, final int y) {

        this.xStart = xStart;
        this.xEnd = xEnd;
        this.y = y;

        parent = this;

    }

    /**
     * Liefert die X-Koordinate des Anfangs des Runs zurück
     * @return die entsprechende x-Koordinate
     */
    public int getxStart() {
        return xStart;
    }

    /**
     * Liefert die x-Koordinate des Ende des Runs zurück
     * @return die entsprechende x-Koordinate
     */
    public int getxEnd() {
        return xEnd;
    }

    /**
     * Liefert die y-Koordinate des Runs zurück
     * @return die entsprechende Y-Koordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Liefert den übergeordneten (Eltern-) Run zurück
     * @return Elternrun von Run
     */
    public Run getParent() {
        return this.parent;
    }

    /**
     * Setzt den übergeordneten (Eltern-) Run
     *
     * @param parent übergeordneter (Eltern-) Run
     */
    public void setParent(final Run parent) {
        this.parent = parent;

    }

    /**
     * Liefert das Wurzelelement der Region zurück, zu der ein Run gehört, indem sie sich an den ELternreferenzen entlang hangelt
     * und komprimiert den durchlaufenen Pfad mittels Aufteilungsmethode
     * @return das entsprechende Wurzelelement
     */
    public Run getRoot(){
        if(parent != this ) {
            parent = parent.parent;

            return parent.getRoot();
        }
        return parent;
    }

    






}