package cli;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CliClient {
    static String host = "localhost";
    static String port = "8080";
    static String uri;

    public static void main(String[] args) throws Exception {
        run();
    }

    public CliClient() {
        this.host = "localhost";
        this.port = "8080";
        uri = "http://" + host + ":" + port + "/calculator";
    }

    public CliClient(String host, String port) {
        this.host = host;
        this.port = port;
        uri = "http://" + host + ":" + port + "/calculator";
    }

    private static void buildUri(){
        uri = "http://" + host + ":" + port + "/calculator";
    }

    public static void run(){
        Scanner scanner = new Scanner(System.in);
        String exp;

        try {
            while (true) {
                exp = scanner.nextLine().toUpperCase();
                if(exp.contains("SERVER=")){
                    String[] args = exp.replaceAll("SERVER=", "").split(":");
                    host = args[0];
                    port = args[1];
                    buildUri();
                } else {
                    System.out.println(exp + " = " + sendRequest(exp));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String sendRequest(String exp) throws Exception {
        //Create connection
        URL url = new URL(uri + "?exp=" + exp);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        connection.setRequestProperty("Content-Length",
                Integer.toString(exp.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        //Send request
        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(exp);
        wr.close();

        //Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }
}
