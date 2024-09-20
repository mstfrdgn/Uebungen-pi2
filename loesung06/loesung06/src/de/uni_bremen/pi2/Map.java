package de.uni_bremen.pi2;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

/**
 * Die Klasse liest die Karte aus Knoten und Kanten ein und
 * repräsentiert diese. Die Daten stammen ursprünglich aus Open
 * Street Map (OSM). Dabei werden für jede eingelesene Kante zwei
 * gerichtete Kanten in die Karte eingetragen. Die Klasse stellt
 * eine Methode zur Verfügung, um die Karte zu zeichnen, sowie
 * eine, die zu einem Punkt den dichtesten Knoten ermittelt.
 */
class Map {

    /**
     * Liste der Knoten des Graphen
     */
    List<Node> nodes = new ArrayList<>();


    /**
     * Konstruktor. Liest die Karte ein.
     *
     * @throws FileNotFoundException Entweder die Datei "nodes.txt" oder die
     *                               Datei "edges.txt" wurden nicht gefunden.
     * @throws IOException           Ein Lesefehler ist aufgetreten.
     */
    Map() throws FileNotFoundException, IOException {
        // Lest hier die beiden Dateien nodes.txt und edges.txt ein
        // und erzeugt daraus eine Karte aus Node- und Edge-Objekten
        // Verbindungen sollen immer in beide Richtungen gehen, d.h.
        // ihr braucht zwei Edges pro Zeile aus der edges.txt.
        final List<String> fileEdges = new ArrayList<String>();
        final List<String> fileNodes = new ArrayList<String>();

        try (final BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream("edges.txt")))) {
            String line;
            while ((line = stream.readLine()) != null) {
                fileEdges.add(line);
            } //gegebenfalls catch weglassen, wieso IllegalArgumentExeption, wenn es kein Argument gibt :o|
        } catch (final FileNotFoundException e) {
            throw new IllegalArgumentException("'edges.txt' wurde nicht gefunden.");
        } catch (final IOException e) {
            throw new IllegalArgumentException("Ein Lesefehler ist aufgetreten.");
        }

        try (final BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream("nodes.txt")))) {
            String line;
            while ((line = stream.readLine()) != null) {
                fileNodes.add(line);
            }
        } catch (final FileNotFoundException e) {
            throw new IllegalArgumentException("'nodes.txt' wurde nicht gefunden.");
        } catch (final IOException e) {
            throw new IllegalArgumentException("Ein Lesefehler ist aufgetreten.");
        }



        //Map<Integer, ArrayList<Integer>> nodeWithEdges = new HashMap<Integer, ArrayList<Integer>();


        for (int i = 0; i < fileNodes.size(); i++) {

            String[] nodeParams = fileNodes.get(i).split(" ");

            int id = Integer.parseInt(nodeParams[0]);
            double xNode = Double.parseDouble(nodeParams[1]);
            double yNode = Double.parseDouble(nodeParams[2]);


            nodes.add(new Node(id, xNode, yNode));

        }
        //  edges = nodes.get(i).getEdges();

        for(String edgeString : fileEdges){
            String[] edgesParams = edgeString.split(" ");
            int idStart = Integer.parseInt(edgesParams[0]);
            int idTarget = Integer.parseInt(edgesParams[1]);


            Node startNode = null;

            for(Node node : nodes){

                if(node.getId() == idStart){

                    startNode = node;
                    break;

                }

            }

            Node endNode = null;

            for(Node node : nodes){

                if(node.getId() == idTarget){

                    endNode = node;
                    break;

                }

            }

            Edge startEdge = new Edge(endNode, startNode.distance(endNode));
            startNode.getEdges().add(startEdge);

            Edge endEdge = new Edge(startNode, endNode.distance(startNode));
            endNode.getEdges().add(endEdge);
        }



       /* for (int i = 0; i < nodes.size(); i++) {

            for (int j = 0; j < fileEdges.size(); j++) {

                String[] edgesParams = fileEdges.get(j).split(" ");
                int idStart = Integer.parseInt(edgesParams[0]);
                int idTarget = Integer.parseInt(edgesParams[1]);


                if (nodes.get(i).getId() == idStart) {



                    for( int k = 0; k < nodes.size(); k++) {

                        if(nodes.get(k).getId() == idTarget) {

                            edges.add(new Edge(nodes.get(k), Integer.MAX_VALUE ));

                        }

                    }




                }else if (nodes.get(i).getId() == idTarget) {

                    for( int k = 0; k < nodes.size(); k++) {

                        if(nodes.get(k).getId() == idStart) {

                            edges.add(new Edge(nodes.get(k), Integer.MAX_VALUE ));

                        }

                    }


                }
            }


        }*/


        //   edges.add(new Edge(Node target, int costs ));


        // Node target/s in edges.txt ->
        // in edges.txt nach jeweiliger Node ID suchen, wenn ID gefunden, dann ZielNode nehmen und in edges speichern
        // von beiden Seiten, weil ungerichteter Graph
        // HashMap geeignet für key + value?
        // int costs anfangs auf unendlich, bis man mit shortestPath() die beste Route berechnet


        // List<Edge> edges = node.getEdges();
        // edges.add(...);


    }


    /**
     * Zeichnen der Karte.
     */
    void draw() {
        // Zeichnet hier alle Kanten der Karte. Hierzu sollte Node.draw benutzt werden.
        // Es können die Original-Koordinaten aus den Knoten benutzt werden. Diese werden
        // automatisch geeignet skaliert.
        for(int m = 0; m < nodes.size(); m++){
            for (int n = 0; n < nodes.get(m).getEdges().size(); n++){
                Node start = nodes.get(m);
                Node target = start.getEdges().get(n).getTarget();

                start.draw(target, Color.BLACK);
            }
        }
    }

    /**
     * Findet den dichtesten Knotens zu einer gegebenen Position.
     *
     * @param x Die x-Koordinate.
     * @param y Die y-Koordinate.
     * @return Der Knoten, der der Position am nächsten ist. null,
     * falls es einen solchen nicht gibt.
     */
    Node getClosest(final double x, final double y) {


        Node position = new Node(-1, x,y);

        double minDistance = Double.POSITIVE_INFINITY;
        Node closestNode= null;



        for(Node node : nodes) {                        // Iteriert durch Liste mit allen Knoten
            double closest = node.distance(position);

            if(closest < minDistance) {
                minDistance = closest;
                closestNode = node;
            }


        }

        return closestNode; // Ersetzen
    }

    /**
     * Löschen aller Vorgängereinträge und Setzen aller Kosten auf unendlich.
     */
    void reset() {
        // Nutzt Node.reachedFromAtCosts für jeden Knoten.
    }

    List<Node> getNodes() {
        return nodes;
    }
}
