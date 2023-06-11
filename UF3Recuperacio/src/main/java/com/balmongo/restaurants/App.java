package com.balmongo.restaurants;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;


public class App {
    //    public static void main(String[] args) {
//
//        Restaurant restOne = new Restaurant("El raco de les brases");
//
//        System.out.println("Preus del: " + restOne.getNameRestaurant());
//        System.out.println("El menu del dia es: " + restOne.getMenu());
//        System.out.println("Si agafem un plat, ens costa: " +  restOne.costMenu(1));
//        System.out.println("Si agafem dos plats, ens costa: " +  restOne.costMenu(2));
//        System.out.println("Si agafem tres plats, ens costa: " +  restOne.costMenu(3));
//    }

    private static MongoCollection<Document> collection;  // Colección MongoDB para almacenar los restaurantes

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");  // Conexión al servidor MongoDB
//        MongoClient mongoClient = MongoClients.create("mongodb://mongo:4OHGQdIY8JAmz268sENp@containers-us-west-194.railway.app:5780");  // Conexión al servidor MongoDB
        MongoDatabase database = mongoClient.getDatabase("restaurants");  // Base de datos MongoDB

        boolean collectionExists = database.listCollectionNames()
                .into(new ArrayList<>())
                .contains("restaurants");  // Verificar si la colección "restaurants" ya existe

        collection = database.getCollection("restaurants");  // Obtener la colección "restaurants"

        if (collectionExists) {
            System.out.println("La colección 'restaurants' ya existe en la base de datos.");
        } else {
            // Importar datos desde un archivo JSON a la colección "restaurants" si no existe
            String jsonPath = "primer-dataset.json";
            InputStream inputStream = App.class.getClassLoader().getResourceAsStream(jsonPath);
            String jsonText = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            JSONArray jsonArray = new JSONArray(jsonText);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Document document = Document.parse(jsonObject.toString());
                collection.insertOne(document);  // Insertar cada documento JSON en la colección "restaurants"
            }

            System.out.println("La importación de datos se ha completado correctamente.");
        }

        boolean exit = false;

        while (!exit) {
            System.out.println();
            System.out.println("Menú:");
            System.out.println("1. Listar todos los restaurantes");
            System.out.println("2. Listar restaurantes por barrio");
            System.out.println("3. Insertar un nuevo restaurante");
            System.out.println("4. Eliminar un restaurante");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();  // Leer la opción seleccionada por el usuario

            switch (option) {
                case 1:
                    listRestaurants();  // Listar todos los restaurantes
                    break;
                case 2:
                    String barrio = getBarrioFromUser();  // Obtener el nombre del barrio ingresado por el usuario
                    listRestaurantsByBarrio(barrio);  // Listar los restaurantes por barrio
                    break;
                case 3:
                    insertNewRestaurant();  // Insertar un nuevo restaurante
                    break;
                case 4:
                    deleteRestaurant();  // Eliminar un restaurante
                    break;
                case 0:
                    exit = true;  // Salir del programa
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        }

        mongoClient.close();  // Cerrar la conexión con el servidor MongoDB
    }

    private static void listRestaurants() {
        Document query = new Document("name", new Document("$ne", null));  // Consulta para filtrar los restaurantes que tienen un nombre válido
        FindIterable<Document> iterable = collection.find(query);  // Obtener los documentos que cumplen con la consulta

        System.out.println("Listado de restaurantes:");
        for (Document document : iterable) {
            String name = document.getString("name");
            String cuisine = document.getString("cuisine");
            String borough = document.getString("borough");

            System.out.println("Nombre: " + name);
            System.out.println("Tipo de cocina: " + cuisine);
            System.out.println("Barrio: " + borough);
            System.out.println();
        }
    }

    private static String getBarrioFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del barrio: ");
        return scanner.nextLine();  // Leer y devolver el nombre del barrio ingresado por el usuario
    }

    private static void listRestaurantsByBarrio(String barrio) {
        Document query = new Document("borough", barrio).append("name", new Document("$ne", null));  // Consulta para filtrar los restaurantes por barrio y que tienen un nombre válido
        FindIterable<Document> iterable = collection.find(query);  // Obtener los documentos que cumplen con la consulta

        System.out.println("Listado de restaurantes en el barrio " + barrio + ":");
        for (Document document : iterable) {
            String name = document.getString("name");
            String cuisine = document.getString("cuisine");

            System.out.println("Nombre: " + name);
            System.out.println("Tipo de cocina: " + cuisine);
            System.out.println();
        }
    }

    private static void insertNewRestaurant() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del nuevo restaurante: ");
        String name = scanner.nextLine();  // Leer el nombre del nuevo restaurante ingresado por el usuario
        System.out.print("Ingrese el tipo de cocina del nuevo restaurante: ");
        String cuisine = scanner.nextLine();  // Leer el tipo de cocina del nuevo restaurante ingresado por el usuario
        System.out.print("Ingrese el barrio del nuevo restaurante: ");
        String borough = scanner.nextLine();  // Leer el barrio del nuevo restaurante ingresado por el usuario

        Document newRestaurant = new Document("name", name)
                .append("cuisine", cuisine)
                .append("borough", borough);  // Crear un nuevo documento para el nuevo restaurante

        collection.insertOne(newRestaurant);  // Insertar el nuevo restaurante en la colección

        System.out.println("El nuevo restaurante ha sido insertado correctamente.");
    }

    private static void deleteRestaurant() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del restaurante que desea eliminar: ");
        String name = scanner.nextLine();  // Leer el nombre del restaurante a eliminar ingresado por el usuario

        Document query = new Document("name", name);  // Consulta para encontrar el restaurante a eliminar
        DeleteResult result = collection.deleteOne(query);  // Eliminar el restaurante de la colección

        if (result.getDeletedCount() > 0) {
            System.out.println("El restaurante ha sido eliminado correctamente.");
        } else {
            System.out.println("No se encontró el restaurante especificado.");
        }
    }
}