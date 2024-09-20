package de.uni_bremen.pi2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Instanzen dieser Klasse bieten ein Bild in verschiedenen Formaten an.
 *
 * @author Simon Berg
 * @author Mustafa Erdogan
 */
class ImageHandler {
    /**
     * Der Farbwert schwarzer Pixel (RGBA = (0, 0, 0, 255).
     */
    private static final int BLACK = 0xff000000;

    /**
     * Der Farbwert weißer Pixel (RGBA = (255, 255, 255, 255).
     */
    private static final int WHITE = 0xffffffff;

    /**
     * Das Orignialbild.
     */
    private final BufferedImage original;
    /**
     * Zufallszahlengenerator zum Erzeugen zufälliger Farben.
     */
    private final Random random = new Random(0);
    /**
     * Ein Grauwertbild. null, wenn es noch nicht berechnet wurde.
     */
    private BufferedImage grayscale;
    /**
     * Ein Schwarzweißbild. null, wenn es noch nicht berechnet wurde.
     */
    private BufferedImage blackAndWhite;
    /**
     * Ein segmentiertes Bild. null, wenn es noch nicht berechnet wurde.
     */
    private BufferedImage segmented;

    /**
     * Konstruktor.
     *
     * @param image Das Bild, das in verschiedenen Formaten bereit gestellt wird.
     */
    ImageHandler(final BufferedImage image) {
        original = image;
    }

    /**
     * Liefert das Originalbild.
     *
     * @return Das Orignalbild.
     */
    BufferedImage getOriginal() {
        return original;
    }

    /**
     * Liefert das Bild in Grauwerten.
     * Das Grauwertbild wird bei Bedarf erst berechnet.
     *
     * @return Das Grauwertbild.
     */
    BufferedImage getGrayscale() {
        if (grayscale == null) {
            final BufferedImage image = getOriginal();
            grayscale = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    grayscale.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return grayscale;
    }

    /**
     * Liefert das Bild in Schwarzweiß.
     * Das Schwarzweißbild wird bei Bedarf erst berechnet, indem für das Grauwertbild der sog. Otsu-Schwellwert
     * bestimmt wird. Alle Grautöne unterhalb des Schwellwerts werden schwarz, alle darüber weiß.
     *
     * @return Das Schwarzweißbild.
     */
    BufferedImage getBlackAndWhite() {
        if (blackAndWhite == null) {
            final BufferedImage image = getGrayscale();
            blackAndWhite = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            final int threshold = otsuThreshold(image);
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    blackAndWhite.setRGB(x, y, (image.getRGB(x, y) & 255) <= threshold ? 0 : -1);
                }
            }
        }
        return blackAndWhite;
    }

    /**
     * Bestimmung des Otsu-Schwellwerts.
     * Vgl. z.B. http://www.labbookpages.co.uk/software/imgProc/otsuThreshold.html .
     *
     * @param image Das Grauwertbild, zu dem der beste Schwellwert zwischen Schwarz
     *              und Weiß bestimmt wird.
     */
    private int otsuThreshold(final BufferedImage image) {
        final int[] histogram = new int[256];
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                ++histogram[image.getRGB(x, y) & 255];
            }
        }

        int countLow = 0; // Anzahl Pixel bis zum Schwellwert
        int countHigh = image.getWidth() * image.getHeight(); // Anzahl über Schwellwert
        int sumLow = 0; // Gewichtete Summe der Pixel bis zum Schwellwert
        int sumHigh = 0; // Gewichtete Summe der Pixel über dem Schwellwert

        for (int i = 0; i < histogram.length; ++i) {
            sumHigh += histogram[i] * i;
        }

        int bestThreshold = 0;
        double bestVar = 0;
        for (int threshold = 0; threshold < histogram.length; ++threshold) {
            countLow += histogram[threshold];
            countHigh -= histogram[threshold];
            sumLow += histogram[threshold] * threshold;
            sumHigh -= histogram[threshold] * threshold;
            if (countLow > 0 && countHigh > 0) {
                double avgLow = (double) sumLow / countLow;
                double avgHigh = (double) sumHigh / countHigh;
                double diff = avgHigh - avgLow;
                double var = (double) countLow * countHigh * diff * diff;
                if (var > bestVar) {
                    bestVar = var;
                    bestThreshold = threshold;
                }
            }
        }

        return bestThreshold;
    }

    /**
     * Liefert eine zufällige Farbe, die mindestens 50% Sättigung und Helligkeit hat.
     *
     * @return Die Farbe, deren Rot-, Grün- und Blauanteile in einem int kodiert sind.
     */
    private int getRandomColor() {
        return Color.HSBtoRGB(random.nextFloat(),
                random.nextFloat() * 0.5f + 0.5f,
                random.nextFloat() * 0.5f + 0.5f);
    }

    /**
     * Liefert ein segmentiertes Bild, bei dem alle zusammenhängenden, weißen Regionen
     * des Schwarzweißbildes mit zufälligen Farben markiert sind. Das Bild wird erst bei
     * Bedarf berechnet.
     *
     * @return Das segmentierte Bild.
     */
    BufferedImage getSegmented() {
        if (segmented == null) {
            final BufferedImage image = getBlackAndWhite();
            segmented = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

            ArrayList<Run> runs = getRuns();

            int i = 0;
            int j = 0;

            while (i < runs.size()) {
                if ((runs.get(j).getY() + 1 == runs.get(i).getY()) &&
                        ((runs.get(j).getxStart() < runs.get(i).getxEnd()) && (runs.get(i).getxStart() < runs.get(j).getxEnd()))){

                    union(runs.get(j), runs.get(i));

                }

                if((runs.get(j).getY() + 1 < runs.get(i).getY())){
                    ++j;
                }

                else if ((runs.get(j).getY() + 1 == runs.get(i).getY()) && (runs.get(j).getxEnd() < runs.get(i).getxEnd())) {
                    ++j;
                }

                else {
                    ++i;
                }

            }


            for (Run run : runs) {


                if (run == run.getRoot()) {

                    segmented.setRGB(run.getxStart(), run.getY(), getRandomColor());


                    for (int x = run.getxStart() + 1; x < run.getxEnd(); x++)
                        segmented.setRGB(x, run.getY(), segmented.getRGB(run.getxStart(), run.getY()));


                } else {

                    for (int x = run.getxStart(); x < run.getxEnd(); x++) {
                        segmented.setRGB(x, run.getY(), segmented.getRGB(run.getRoot().getxStart(), run.getRoot().getY()));

                    }


                }
            }


            /*for(int k = 0; k < runs.size(); k++){

                if(runs.get(k) == runs.get(k).getRoot()) {

                    segmented.setRGB(runs.get(k).getxStart(), runs.get(k).getY(), getRandomColor());

                    for( int x = runs.get(k).getxStart() + 1; x < runs.get(k).getxEnd(); x++) {

                        segmented.setRGB(x, runs.get(k).getY(), segmented.getRGB(runs.get(k).getxStart(), runs.get(k).getY()));
                    }

                }else {

                    for( int x = runs.get(k).getxStart() ; x < runs.get(k).getxEnd(); x++) {
                        segmented.setRGB(x, runs.get(k).getY(), segmented.getRGB(runs.get(k).getRoot().getxStart(), runs.get(k).getRoot().getY()));
                    }
                }



            }*/
        }
        return segmented;
    }

    /**
     * Liefert eine Folge aus Runs des Bildes als ArrayList. Sind horizontal weiße Pixel
     * benachbart, so werden sie in einen Run zusammengefasst und in eine ArrayList gepackt.
     *
     * @return Die Folge aus Runs des Bildes als ArrayList
     */
    private ArrayList<Run> getRuns() {

        int xStart = 0;
        int xEnde = 0;
        int pY = 0;

        ArrayList<Run> runs = new ArrayList<>();

        final BufferedImage image = getBlackAndWhite();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                if (image.getRGB(x, y) == WHITE ) {

                    xStart = x;
                    xEnde = x + 1;
                    pY = y;

                    for (int xE = xStart + 1; xE < image.getWidth(); xE++) {

                        if (image.getRGB(xE, y) == BLACK) {

                            break;

                        } else if (image.getRGB(xE, y) == WHITE) {
                            xEnde++;
                        }

                    }

                    runs.add(new Run(xStart, xEnde, pY));
                    x = xEnde;

                }
            }
        }

        return runs;

    }

    /**
     * Vereinigt 2 Regionen, wenn sie benachbarte Runs aufweisen, indem das entsprechende Wurzelelement einer Region die
     * Referenz der anderen erhält.
     *
     * @param a eine Region, die der Nachbar der zweiten Region ist
     * @param b eine Region, die der Nachbar der ersten Region ist
     */
    private void union(Run a, Run b) {

        Run aRoot = a.getRoot();
        Run bRoot = b.getRoot();

        if ((aRoot.getParent().getY() < bRoot.getParent().getY())
                || (aRoot.getParent().getY() == bRoot.getParent().getY()) && ( aRoot.getxEnd() < bRoot.getxEnd() )) {

            bRoot.setParent(aRoot);

        }else{

            aRoot.setParent(bRoot);



        }


    }

}
