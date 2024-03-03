package Linux;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static Stack<FileSystemComponent> currentPath = new Stack<>();

    public static void main(String[] args) {

        Drive root = new Drive("root");
        currentPath.push(root);
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            // System.out.print("Enter command (type 'exit' to quit): ");
            command = scanner.nextLine().trim();

            switch (command.split(" ")[0]) {
                case "cd":
                    if(command.split(" ")[1].equalsIgnoreCase("~"))
                        currentPath.peek().jumpToRoot(currentPath,root);
                    else
                        currentPath.peek().changeDirectory(command.split(" ")[1], currentPath);
                    break;
                case "mkdir":
                    if((currentPath.peek() instanceof Drive && !(currentPath.peek().getName().equalsIgnoreCase("root"))) || currentPath.peek() instanceof Folder)
                    {
                        currentPath.peek().addComponent(new Folder(command.split(" ")[1]));
                    }
                    else if(currentPath.peek() instanceof Drive && currentPath.peek().getName().equalsIgnoreCase("root")){
                        System.out.println("Error: Cannot create folder under root drive.");
                    }
                    else {
                        System.out.println("Error: Cannot create folder in a file.");
                    }
                    break;
                case "list":
                    currentPath.peek().listContents();
                    break;
                case "ls":
                    currentPath.peek().display();
                    break;
                case "delete":
                    if(command.split(" ")[1].equalsIgnoreCase("-r"))
                    {
                        currentPath.peek().recursiveDelete(command.split(" ")[2]);
                    }
                    else
                    {
                        currentPath.peek().delete(command.split(" ")[1]);
                    }
                    break;
                case "touch":
                    if((currentPath.peek() instanceof Drive && !(currentPath.peek().getName().equalsIgnoreCase("root"))) || currentPath.peek() instanceof Folder)
                    {
                        if(command.split(" ").length == 3)
                            currentPath.peek().addComponent(new File(command.split(" ")[1], Long.parseLong(command.split(" ")[2])));
                        else if(command.split(" ").length == 2)
                        {
                            currentPath.peek().addComponent(new File(command.split(" ")[1], 124));
                        }
                        else
                        {
                            System.out.println("Error: Invalid command.");
                        }
                    }

                    else if(currentPath.peek() instanceof Drive && currentPath.peek().getName().equalsIgnoreCase("root")){
                        System.out.println("Error: Cannot create file under root drive.");
                    }

                    else {
                        System.out.println("Error: Cannot create file in a file.");
                    }
                    break;
                case "mkdrive":
                    if(currentPath.peek() instanceof Drive && currentPath.peek().getName().equalsIgnoreCase("root"))
                    {
                        Drive newDrive = new Drive(command.split(" ")[1]);
                        currentPath.peek().addComponent(newDrive);
                    }
                    else {
                        System.out.println("Error: Cannot create drive under any file or folder or drive unless it's root.");
                    }
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
            }

        }while (!command.equals("exit")) ;
    }
}