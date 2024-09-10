package pl.umcs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private ServerSocket ss;
    private List<Client> clients = new ArrayList<>();
    private Map<String, Client> clientMap = new HashMap<>();


    public Server() throws IOException {
        ss = new ServerSocket(2000);
    }

    public void listen() throws IOException {
        while (true) {
            Socket socket = ss.accept();
            Client client = new Client(this, socket);
            new Thread(client).start();
            clients.add(client);
        }
    }

    public void broadcast(String message) {
        for (Client client : clients)
            client.send(message);
    }

    public void clientLogged(Client loggedClient) {
        clientMap.put(loggedClient.getLogin(), loggedClient);
        for (Client currenClient : clients)
            if (currenClient != loggedClient)
                currenClient.send(String.format("%s logged in", loggedClient.getLogin()));
    }

    public void clientLeft(Client leavingClient) {
        clients.remove(leavingClient);
        try {
            clientMap.remove(leavingClient.getLogin());
        } catch (Exception _) {}
        for (Client currentClient : clients)
            currentClient.send(String.format("%s logged off", leavingClient.getLogin()));
    }
}
