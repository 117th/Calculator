import calc.Calculator;
import cli.CliClient;
import http.Server;

import java.util.Scanner;

public class CLI {

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Port to run server on: ");
        String port = scanner.nextLine();

        Server server = new Server(port);
        server.startServer();

        CliClient cliClient = new CliClient("localhost", String.valueOf(port));
        cliClient.run();
    }

}
