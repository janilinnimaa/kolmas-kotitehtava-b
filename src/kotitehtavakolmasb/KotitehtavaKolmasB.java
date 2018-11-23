package kotitehtavakolmasb;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* 
En ole aivan varma vastaako tämä ratkaisu tehtävänantoa, sillä ohjelma ei varsinaisesti 
lue tiedostoja käynnistyessään. Kuten pelkässä HashMap-versiossa, niin tässäkin ohjelma ensin kysyy 
uusia sanoja, jotka tallennetaan tiedostoihin alkuteksti.txt ja käännös.txt. Tallennuksen jälkeen 
tiedostot luetaan rivi riviltä HashMappiin avain-arvo pareiksi ja ohjelma toimii muuten samoin
kuin KotitehtäväKolmasA. Kumpikaan ohjelma ei ota kantaa käyttäjän lisäämien käännösten oikeellisuuteen 
(koska käyttäjä voi syöttää ohjelmaan mitä hyvänsä), eikä siihen, onko samat tiedot mahdollisesti 
yritetty lisätä useampaan kertaan.
*/

public class KotitehtavaKolmasB {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Scanner lukijaEka = null;
        Scanner lukijaToka = null;
        Scanner lukija = new Scanner(System.in);
        
        String syote;
        
        HashMap<String, String> rakenne = new HashMap<String, String>();
        
        PrintWriter kirjoittajaEka = new PrintWriter(new FileWriter("alkuteksti.txt", true));
        PrintWriter kirjoittajaToka = new PrintWriter(new FileWriter("käännös.txt", true));
        
        while (true) {
            System.out.println("Sana alkukielellä? (tyhjä lopettaa)");

            syote = lukija.nextLine();

            if (syote.isEmpty()) {
                break;
            } else {
                String avain = syote;
                System.out.println("Sana käännettynä? (tyhjä lopettaa)");
                syote = lukija.nextLine();
                if (syote.isEmpty()) {
                    break;
                } else {
                    String arvo = syote;
                    kirjoittajaEka.println(avain);
                    kirjoittajaToka.println(arvo);
                }
            }
        }
        
        kirjoittajaEka.close();
        kirjoittajaToka.close();
        
        File alkuteksti = new File("alkuteksti.txt");
        File kaannos = new File("käännös.txt");
        
        try {
            lukijaEka = new Scanner(alkuteksti);
        } catch (FileNotFoundException fe) {
            System.out.println("Tiedostoa ei löydy!");
        }
        try {
            lukijaToka = new Scanner(kaannos);
        } catch (FileNotFoundException fe) {
            System.out.println("Tiedostoa ei löydy!");
        }
        
        while (lukijaEka.hasNextLine() && lukijaToka.hasNextLine()) {
            String riviEka = lukijaEka.nextLine();
            String riviToka = lukijaToka.nextLine();
            rakenne.put(riviEka, riviToka);
        }
        
        System.out.println("Tallennetaan uudet sanat...");
        System.out.println("Ladataan sanakirjan sisältö...");
        
        Iterator<Map.Entry<String, String>> it = rakenne.entrySet().iterator();
        
        System.out.print("Sanakirjan sisältö: {");
        
        while (it.hasNext()) {
            HashMap.Entry<String, String> alkio = (HashMap.Entry<String, String>) it.next();
            if (it.hasNext()) {
                System.out.print(alkio.getKey() + " = " + alkio.getValue() + ", ");
            } else {
                System.out.println(alkio.getKey() + " = " + alkio.getValue() + "}");
            }
        }
        
        while (true) {
            System.out.println("Minkä sanan käännöksen haluaet tietää? (tyhjä sana lopettaa)");

            syote = lukija.nextLine();
            if (syote.isEmpty()) {
                break;
            } else {
                if (rakenne.containsKey(syote)) {
                    System.out.println("Sanan \"" + syote + "\" käännös on \"" + rakenne.get(syote) + "\"");
                } else if (rakenne.containsValue(syote)) {
                    for (Map.Entry<String, String> entry : rakenne.entrySet()) {
                        if (entry.getValue().equals(syote)) {
                            System.out.println("Sanan \"" + syote + "\" käännös on \"" + entry.getKey() + "\"");
                        }
                    }
                } else {
                    System.out.println("Kirjoittamaasi sanaa ei löytynyt sanakirjasta!");
                }
            }
        }
    }

}
