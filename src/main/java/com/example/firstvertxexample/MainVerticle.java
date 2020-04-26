package com.example.firstvertxexample;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.util.concurrent.atomic.AtomicReference;

public class MainVerticle extends AbstractVerticle {
  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    Route postHandler = router
      .post("/product/save/:productname")
      .handler(routingContext -> {
        System.out.println("Printed Product name(from post)");
        String productname = routingContext.request().getParam("productname");
        HttpServerResponse response = routingContext.response();
        response.setChunked(true);
        String result = "Product can't be saved Because yet Database didn't add ( Product name: " + productname + ")";
        response.write(result);
        response.end();
      });

    Route getHandler = router
      .get("/product/get")
      .handler(routingContext -> {
        System.out.println("Printed Product name(from get)");
        HttpServerResponse response = routingContext.response();
        response.setChunked(true);
        response.write("Product not found");
        response.end();
      });

    httpServer
      .requestHandler(router::accept)
      .listen(8080);

  }
}
