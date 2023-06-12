package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ArticlesCompra implements Serializable {
    private String descripcio;                 // Variable per emmagatzemar la descripció de l'article
    private double quantitat;                  // Variable per emmagatzemar la quantitat de l'article
    private String unitat;                     // Variable per emmagatzemar la unitat de l'article
    private String seccio;                     // Variable per emmagatzemar la secció de l'article
    private double preu;                       // Variable per emmagatzemar la secció de l'article

    public ArticlesCompra() {
    }

    public ArticlesCompra(String descripcio, double quantitat, String unitat, String seccio, double preu) {
        this.descripcio = descripcio;
        this.quantitat = quantitat;
        this.unitat = unitat;
        this.seccio = seccio;
        this.preu = preu;
    }

    // Getters and setters

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(double quantitat) {
        this.quantitat = quantitat;
    }

    public String getUnitat() {
        return unitat;
    }

    public void setUnitat(String unitat) {
        this.unitat = unitat;
    }

    public String getSeccio() {
        return seccio;
    }

    public void setSeccio(String seccio) {
        this.seccio = seccio;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public static void main(String[] args) throws IOException {
        Main.run();
    }

    private static class Main {
        public static void run() throws IOException {
            mostraMenu();
        }

        private static void mostraMenu() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ArrayList<ArticlesCompra> llistaCompra = new ArrayList<>();
            int opcio = 0;

            while (opcio != 3) {
                System.out.println("Benvingut a la llista de la compra!");
                System.out.println("1. Fer la llista de la compra");
                System.out.println("2. Llegir la llista de la compra");
                System.out.println("3. Sortir");
                System.out.println("Selecciona una opció: ");
                opcio = Integer.parseInt(reader.readLine());

                switch (opcio) {
                    case 1:
                        llistaCompra = captureArticlesCompra();
                        generateXML(llistaCompra);
                        serializeObject(llistaCompra);
                        break;
                    case 2:
                        readXML();
                        break;
                    case 3:
                        System.out.println("Fins aviat!");
                        break;
                    default:
                        System.out.println("Opció no vàlida. Si us plau, selecciona una opció vàlida.");
                }
            }
        }

        private static ArrayList<ArticlesCompra> captureArticlesCompra() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ArrayList<ArticlesCompra> llistaCompra = new ArrayList<>();

            while (true) {
                System.out.println("Introdueix la descripció de l'article (o 'exit' per sortir):");
                String descripcio = reader.readLine();

                if (descripcio.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.println("Introdueix la quantitat:");
                double quantitat = Double.parseDouble(reader.readLine());

                System.out.println("Introdueix la unitat:");
                String unitat = reader.readLine();

                System.out.println("Introdueix la secció:");
                String seccio = reader.readLine();

                System.out.println("Introdueix el preu:");

                double preu = Double.parseDouble(reader.readLine());

                ArticlesCompra article = new ArticlesCompra(descripcio, quantitat, unitat, seccio, preu);
                llistaCompra.add(article);
            }

            return llistaCompra;
        }

        private static void generateXML(ArrayList<ArticlesCompra> llistaCompra) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("llista_compra.xml"));
                writer.write("<llistacompra>\n");

                for (ArticlesCompra article : llistaCompra) {
                    writer.write("  <article>\n");
                    writer.write("  <descripcio>" + article.getDescripcio() + "</descripcio>\n");
                    writer.write("  <quantitat>" + article.getQuantitat() + "</quantitat>\n");
                    writer.write("  <unitat>" + article.getUnitat() + "</unitat>\n");
                    writer.write("  <seccio>" + article.getSeccio() + "</seccio>\n");
                    writer.write("  <preu>" + article.getSeccio() + "</preu>\n");
                    writer.write("  </article>\n");
                }

                writer.write("</llistacompra>");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void readXML() {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("llista_compra.xml"));
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void serializeObject(ArrayList<ArticlesCompra> llistaCompra) {
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("llista_compra.ser"));
                outputStream.writeObject(llistaCompra);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
