package com.unimater;

import com.sun.net.httpserver.HttpServer;
import com.unimater.controller.HelloWorldHandler;
import com.unimater.controller.ProductHandler;
import com.unimater.controller.ProductTypeHandler;
import com.unimater.controller.SaleHandler;
import com.unimater.controller.SaleItemHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            HttpServer servidor = HttpServer.create(
                    new InetSocketAddress(8080), 0);

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jdbc2608", "root", "sdb162sw");

            servidor.createContext("/helloWorld", new HelloWorldHandler());
            servidor.createContext("/productType", new ProductTypeHandler(connection));
            servidor.createContext("/product", new ProductHandler(connection));
            servidor.createContext("/sale", new SaleHandler(connection));
            servidor.createContext("/saleItem", new SaleItemHandler(connection));

            servidor.setExecutor(null);
            servidor.start();
            System.out.println("Servidor rodando na porta 8080");

        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}