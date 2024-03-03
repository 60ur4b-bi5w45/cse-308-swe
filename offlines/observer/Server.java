import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static boolean loggedIn;
    private static Map<PrintWriter,String> buffer = new HashMap<>();
    private static Map<String, Double> stockPrices = new HashMap<>();
    private static Map<String, Integer> stockInventory = new HashMap<>();
    private static Map<String, Set<PrintWriter>> clientsByStock = new HashMap<>(); // To store clients for each stock
    private static PrintWriter writer;

    public static void main(String[] args) throws IOException {
        initialize(); // Initialize stock prices, stock inventory

        new Thread(() -> {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Listening on port " + PORT);
            while (true) {
                ClientHandler clientHandler = new ClientHandler(serverSocket.accept());

                writer = new PrintWriter(ClientHandler.clientSocket.getOutputStream(), true);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }).start();

        Scanner scanner = new Scanner(System.in);


        while(true) {
            String adminInput = scanner.nextLine();
            if (adminInput.startsWith("I")) {
                processIncrementPrice(adminInput);
            } else if (adminInput.startsWith("D")) {
                processDecrementPrice(adminInput);
            } else if (adminInput.startsWith("C")) {
                processIncrementStockCount(adminInput);
            }
        }

    }

    private static class ClientHandler extends Thread {
        private static Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ClientHandler(Socket socket) {
            clientSocket = socket;
        }

        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                String input;

                while ((input = reader.readLine()) != null) {
                    if(input.equalsIgnoreCase("Login"))
                    {
                        writer.print(buffer.get(Server.writer));
                        writer.println("Welcome to the stock market!");
                        loggedIn = true;
                    }

                    else if(input.equalsIgnoreCase("Logout"))
                    {
                        writer.println("Goodbye!");
                        loggedIn = false;
                    }

                    if(loggedIn)
                    {
                        if (input.equalsIgnoreCase("GET_STOCKS")) {
                            sendStockInfo();
                        } else if (input.startsWith("S")) {
                            processSubscription(input, writer);
                        } else if (input.startsWith("U")) {
                            processUnsubscription(input, writer);
                        }
                        else if(input.startsWith("V"))
                        {
                            printSubscribedStocks();
                        }
                    }
                    else {
                        writer.println("Please login first!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    writer.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void printSubscribedStocks() {
            writer.println("Here's your stocks:");
            Set<String> Stocks = clientsByStock.keySet();
            for(String stock : Stocks)
            {
                Set<PrintWriter> clients = clientsByStock.get(stock);
                if(clients.contains(writer))
                {
                    writer.println(stock + " " + stockInventory.get(stock) + " " + stockPrices.get(stock));
                }
            }
        }
        private void sendStockInfo() {
            for (Map.Entry<String, Double> entry : stockPrices.entrySet()) {
                writer.println(entry.getKey() + ": $" + entry.getValue() + " Available: " + stockInventory.get(entry.getKey()) );
            }
        }
        private void processSubscription(String input, PrintWriter clientWriter) {
            String[] tokens = input.split("\\s+");
            String stock = tokens[1];
            clientsByStock.computeIfAbsent(stock, k -> new HashSet<>()).add(clientWriter);
            writer.println("Subscribed to " + stock);
        }

        private void processUnsubscription(String input, PrintWriter clientWriter) {
            String[] tokens = input.split("\\s+");
            String stock = tokens[1];
            Set<PrintWriter> clients = clientsByStock.get(stock);
            if (clients != null) {
                clients.remove(clientWriter);
                clientsByStock.put(stock, clients);
                writer.println("Unsubscribed from " + stock);
            }
        }
    }
    private static void initialize(){
        loadStocksFromFile("init_stocks.txt");
    }

    public static void loadStocksFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String name = scanner.next();
                int count = scanner.nextInt();
                double price = scanner.nextDouble();
                stockInventory.put(name, count);
                stockPrices.put(name, price);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void notifyClients(String stock, String message) {
        Set<PrintWriter> clients = clientsByStock.get(stock);
        if(!(loggedIn) && clients != null)
        {
            for (PrintWriter clientWriter : clients) {
                buffer.merge(clientWriter, message, (a, b) -> a + "\n" + b);
                System.out.println(buffer.get(clientWriter));
            }
        }

        if (clients != null && loggedIn) {
            for (PrintWriter clientWriter : clients) {
                clientWriter.println(message);
            }
        }
    }

    private static void processIncrementPrice(String input){
        String[] tokens = input.split("\\s+");
        if (tokens.length != 3) {
            writer.println("Invalid command");
            return;
        }
        String stock = tokens[1];
        double amount = Double.parseDouble(tokens[2]);
        if (!stockPrices.containsKey(stock)) {
            writer.println("Invalid stock");
            return;
        }
        stockPrices.put(stock, stockPrices.get(stock) + amount);
        notifyClients(stock, "Incremented price of " + stock + " by $" + amount);
    }

    private static void processDecrementPrice(String input) throws IOException {
        PrintWriter writer = new PrintWriter(ClientHandler.clientSocket.getOutputStream(), true);
        String[] tokens = input.split("\\s+");
        String stock = tokens[1];
        double amount = Double.parseDouble(tokens[2]);
        if (!stockPrices.containsKey(stock)) {
            writer.println("Invalid stock");
            return;
        }
        stockPrices.put(stock, stockPrices.get(stock) - amount);
        notifyClients(stock, "Decremented price of " + stock + " by $" + amount);
    }
    private static void processIncrementStockCount(String input) throws IOException {
        PrintWriter writer = new PrintWriter(ClientHandler.clientSocket.getOutputStream(), true);
        String[] tokens = input.split("\\s+");
        String stock = tokens[1];
        int amount = Integer.parseInt(tokens[2]);
        if (!stockPrices.containsKey(stock)) {
            writer.println("Invalid stock");
            return;
        }
        stockInventory.put(stock, stockInventory.get(stock) + amount);
        notifyClients(stock, "Incremented stock count of " + stock + " by " + amount);
    }
}
