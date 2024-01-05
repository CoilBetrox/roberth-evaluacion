package com.rdtroyar;

import com.google.gson.Gson;
import com.rdtroyar.db.Book;
import com.rdtroyar.services.ServiceBook;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.List;

public class Principal {

    static SeContainer container;

    static List<Book> listBooks(Request req, Response res){
        var servicioBook = container.select(ServiceBook.class).get();
        res.type("application/json");
        return servicioBook.findAll();
    }

    static Book findBook(Request req, Response res){
        var servicioBook = container.select(ServiceBook.class).get();
        res.type("application/json");
        String _id = req.params(":id");

        var book = servicioBook.findById(Integer.valueOf(_id));
        if (book == null){
            res.status(404);
            spark.Spark.halt(404, "Book not find");
        }
        return book;
    }

    static  Book createBook(Request req, Response res){
        var servicioBook = container.select(ServiceBook.class).get();
        res.type("application/json");
        Gson gson = new Gson();
        Book newBook = gson.fromJson(req.body(), Book.class);
        servicioBook.insert(newBook);
        return newBook;
    }

    static Book updateBook(Request req, Response res){
        var servicioBook = container.select(ServiceBook.class).get();
        res.type("application/json");
        Gson gson = new Gson();
        Book bookUpdate = gson.fromJson(req.body(), Book.class);
        servicioBook.update(bookUpdate);
        return bookUpdate;
    }

    static String deleteBook(Request req, Response res) {
        var servicio = container.select(ServiceBook.class).get();
        res.type("application/json");
        String _id = req.params(":id");
        boolean delete = servicio.delete(Integer.valueOf(_id));

        if (delete) {
            return "Book delete suscesfull";
        } else {
            res.status(404);
            return "Book not find";
        }
    }


    public static void main(String[] args) {
        container = SeContainerInitializer.newInstance().initialize();
        ServiceBook servicio = container.select(ServiceBook.class).get();

        Spark.port(8080);

        Gson gson = new Gson();
        spark.Spark.get("/books", Principal::listBooks, gson::toJson);
        spark.Spark.get("/books/:id", Principal::findBook, gson::toJson);
        spark.Spark.post("/books", Principal::createBook, gson::toJson);
        spark.Spark.put("/books/:id", Principal::updateBook, gson::toJson);
        spark.Spark.delete("/books/:id", Principal::deleteBook, gson::toJson);


    }
}
