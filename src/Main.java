import entities.Customer;
import entities.Order;
import entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main(String[] args) {
        // Prodotti
        Product libroSignoreAnelli = new Product(123L, "Il signore degli anelli", "Books", 90.0);
        Product libroHarryPotter = new Product(124L, "Harry Potter", "Books", 140.0);
        Product passeggino = new Product(125L, "Passeggino", "Baby", 70.0);
        Product biberon = new Product(125L, "Biberon", "Baby", 10.0);
        Product expedition33 = new Product(126L, "Expedition33: CLair Obscure", "Boys", 39.9);
        Product finalFantasyX = new Product(127L, "Final Fantasy X", "Boys", 69.9);

        // Lista Prodotti
        List<Product> prodottiDisponibili = new ArrayList<>();
        prodottiDisponibili.add(libroHarryPotter);
        prodottiDisponibili.add(libroSignoreAnelli);
        prodottiDisponibili.add(passeggino);
        prodottiDisponibili.add(biberon);
        prodottiDisponibili.add(expedition33);
        prodottiDisponibili.add(finalFantasyX);

        // Clienti
        Customer customer1 = new Customer(01L, "Antonio Schettini", 1);
        Customer customer2 = new Customer(012L, "Riccardo Gulin", 2);
        Customer customer3 = new Customer(013L, "Stefano Casasola", 2);

        // Ordini
        //Primo ordine
        List<Product> prodottiPrimoOrdine = new ArrayList<>();
        prodottiPrimoOrdine.add(expedition33);
        prodottiPrimoOrdine.add(finalFantasyX);
        prodottiPrimoOrdine.add(passeggino);
        Order primoOrdine = new Order(12341298L, "Spedito", LocalDate.of(2026, 6, 10), LocalDate.of(2026, 6, 12), prodottiPrimoOrdine, customer2);

        // Secondo ordine
        List<Product> prodottiSecondoOrdine = new ArrayList<>();
        prodottiSecondoOrdine.add(libroHarryPotter);
        prodottiSecondoOrdine.add(libroSignoreAnelli);
        prodottiSecondoOrdine.add(biberon);
        Order secondoOrdine = new Order(1234156L, "In elaborazione", LocalDate.of(2021, 2, 1), LocalDate.of(2021, 2, 9), prodottiSecondoOrdine, customer3);

        // Terzo ordine
        List<Product> prodottiTerzoOrdine = new ArrayList<>();
        prodottiTerzoOrdine.add(expedition33);
        prodottiTerzoOrdine.add(finalFantasyX);
        Order terzoOrdine = new Order(12456323L, "In Consegnato", LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 6), prodottiTerzoOrdine, customer1);

        // Lista di tutti gli Ordini
        List<Order> tuttiGliOrdini = new ArrayList<>();
        tuttiGliOrdini.add(primoOrdine);
        tuttiGliOrdini.add(secondoOrdine);
        tuttiGliOrdini.add(terzoOrdine);

        //Esercizio 1
        System.out.println("Eserciozio 1, Libri con prezzo maggiore di 100 euro");
        List<Product> libriCostoMaggioreDi100 = prodottiDisponibili.stream() // Avvio lo stream
                .filter(product -> product.getCategory().equals("Books")) // filter per trovare solo i libri
                .filter(product -> product.getPrice() > 100) // ulteriore filter per solo i >100
                .toList(); // faccio il collect.
        // Stampo in console
        libriCostoMaggioreDi100.forEach(System.out::println);

        //Esercizio 2
        System.out.println("Esercizio 2, Prodotti che contengono categoria Baby ");
        List<Order> ordiniConBaby = tuttiGliOrdini.stream() // Avvio lo stream
                .filter(ordine -> ordine.getProducts().stream() // filter per leggere i prodotti
                        .anyMatch(product -> product.getCategory().equals("Baby"))).toList(); // anymatch per il predicate
        // Stampo in console
        ordiniConBaby.forEach(System.out::println);

        //Esercizio 3
        System.out.println("Esercizio 3, prodotti Boys saranno scontati del 10%");
        List<Product> prodottiBoysScontati = prodottiDisponibili.stream().
                filter(product -> product.getCategory().equals("Boys")).toList();

        for (Product product : prodottiBoysScontati) { // ciclo per applicare lo sconto del 10%
            product.setPrice(product.getPrice() * 0.90);
        }
        prodottiBoysScontati.forEach(System.out::println);

        //Esercizio 4
        System.out.println("Esercizio 4, Prodotti ordinati da Tier 2 nel periodo custom");
        // Mi setto le mie variabili con le date precise da inserire nel conditionals
        LocalDate dataInizio = LocalDate.of(2021, 2, 1);
        LocalDate dataFIne = LocalDate.of(2021, 4, 1);

        List<Product> prodottiFiltratiPerData = tuttiGliOrdini.stream()
                // ! come condizionale per ordine dal 1 feb in poi e non oltre il 1 aprile
                .filter(order -> !order.getOrderDate().isBefore(dataInizio) && !order.getOrderDate().isAfter(dataFIne))
                .filter(order -> order.getCustomer().getTier() == 2)
                .flatMap(order -> order.getProducts().stream()).toList();

        System.out.println("Lista prodotti filtrati per data: " + prodottiFiltratiPerData);
        prodottiFiltratiPerData.forEach(System.out::println);

    }
}
