package Linux;

import java.time.LocalDateTime;
import java.util.Stack;

// Component interface
interface FileSystemComponent {
    String getName();
    long getSize();
    String getType();
    String getDirectory();
    int getComponentCount();
    LocalDateTime getCreationTime();
    void display();
    void changeDirectory(String name, Stack<FileSystemComponent> currentPath);
    void displayDetails(String name, Stack<FileSystemComponent> currentPath);
    void listContents();
    boolean delete(String name);
    boolean recursiveDelete(String name);
    void jumpToRoot(Stack<FileSystemComponent> currentPath, Drive root);

    void addComponent(FileSystemComponent component);

    void setParent(String name);
}

