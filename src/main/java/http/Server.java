package http;

import calc.Calculator;
import com.sun.net.httpserver.*;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.URI;

public class Server {

    private static Calculator calculator;
    private int port = 8080;

    public Server(){
        calculator = new Calculator();
    }

    public Server(String port){
        calculator = new Calculator();
        this.port = Integer.valueOf(port);
    }

    public void startServer() throws Exception{

        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(port), 0);

        httpServer.createContext("/calculator", new CalculatorHandler());
        httpServer.setExecutor(null);
        httpServer.start();

    }

    static class CalculatorHandler implements HttpHandler {

        public void handle(HttpExchange exchange) throws IOException {
            URI requestUri = exchange.getRequestURI();

            String resp = requestUri.getRawQuery().split("=")[1];

            try {
                resp = calculator.calc(resp).toString();
            } catch (ScriptException scre) {
                resp = scre.getLocalizedMessage();
                scre.printStackTrace();
            }

            exchange.sendResponseHeaders(200, resp.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(resp.getBytes());
            outputStream.close();
        }
    }

}
