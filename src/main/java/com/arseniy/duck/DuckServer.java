package com.arseniy.duck;


import com.arseniy.duck.responses.HttpResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

final public class DuckServer {

    private final ServerSocket serverSocket;
    private Map<String, Ducklet> handlers = new HashMap<>();

    DuckServer(int port) throws IOException {
        this.serverSocket= new ServerSocket(port);
    }


    public void start() throws IOException {
        System.out.println("Started duck server\n");
        while (true){
            Socket clientSocket = serverSocket.accept();
            handleRequests(clientSocket);
        }

    }

    private void handleRequests(Socket clientSocket) throws IOException {

        System.out.println("proccesing request");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        try {

            HttpRequest request = parseHttp(in);

            Ducklet handler = handlers.get(request.getMethod()+":" +request.getPath());

            if(handler == null){
                System.out.println("no handler");
                out.write("Bad response");
                clientSocket.close();
                return;
            }

            System.out.println("hanling request");
            HttpResponse response = handler.handle(request);

            System.out.println("sending request out " + response.toString() + "\n");
            sendResponse(response, out);

        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }

        clientSocket.close();

    }

    public void registerRoute(String path, String method, Ducklet handler){
        handlers.put(method+":"+path, handler);
    }



    private HttpRequest parseHttp(BufferedReader request) throws Exception {

        HttpRequest parsedRequest = new HttpRequest();

        // Parse request line

        String line = request.readLine();
        if(line.isEmpty()){
            throw new Exception("NO FIRST LINE");
        }

        String[] requestLine = line.split(" ");

        parsedRequest.setMethod(requestLine[0]);
        parsedRequest.setPath(requestLine[1]);
        parsedRequest.setVersion(requestLine[2]);


        // Parse headers

        while (!(line = request.readLine()).isEmpty()){
            String[] requestHeader= line.split(": ");
            parsedRequest.addHeader(requestHeader[0], requestHeader[1]);
        }



        // Parse body

        if(parsedRequest.getMethod().equals("POST") || parsedRequest.getMethod().equals("PUT") ){
            int length = Integer.parseInt(parsedRequest.getHeaders().getOrDefault("Content-Length", "0"));
            char[] bodyChars = new char[length];
            request.read(bodyChars, 0, length);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(bodyChars);
            parsedRequest.setBody(stringBuilder.toString());
        }


        return parsedRequest;

    }


    private void sendResponse(HttpResponse httpResponse, BufferedWriter out) throws IOException {

        String responseLine = httpResponse.getVersion() + " " + httpResponse.getCode() + " " + httpResponse.getMessage();

        // Headers
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String, String> entry : httpResponse.getHeaders().entrySet()  ) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        if(httpResponse.getBody() != null) {
            stringBuilder.append("Content-Length").append(": ").append(httpResponse.getBody().length());
        }
        stringBuilder.append("\r\n");



        System.out.println(responseLine+ "\r\n");
        out.write(responseLine+ "\r\n");

        System.out.println(stringBuilder.toString() +"\r\n");
        out.write(stringBuilder.toString() +"\r\n");

        System.out.println(httpResponse.getBody());

        if(httpResponse.getBody() != null) {
            out.write(httpResponse.getBody());
        }

        out.flush();
    }





}
