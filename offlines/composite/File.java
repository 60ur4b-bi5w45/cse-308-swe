package Linux;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

class File implements FileSystemComponent {
    private final String name;
    private final long size;
    private final LocalDateTime creationTime;

    private String parent;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
        this.creationTime = LocalDateTime.now();
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getType() {
        return "File";
    }

    public String getDirectory() {
        String parentDirectory = parent + ":\\" + getName();
        return parentDirectory.startsWith(":\\" ) ? parentDirectory.substring(2) : parentDirectory;
    }

    public int getComponentCount() {
        return 0;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void display() {
        String output = String.format("File: %s\nSize: %d\nType: %s\nDirectory: %s\nComponent Count: %d\nCreation Time: %s\n",
                getName(), getSize(), getType(), getDirectory(), getComponentCount(),
                getCreationTime().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy h:mm a")));
        System.out.println(output);
    }

    public void changeDirectory(String name, Stack<FileSystemComponent> currentPath) {
        System.out.println("Error: Cannot change directory. " + getName() + " is a file.");
    }

    public void displayDetails(String name, Stack<FileSystemComponent> currentPath) {
        if (getName().equals(name)) {
            display();
        }
    }

    public void addComponent(FileSystemComponent component) {
        System.out.println("Error: Cannot add component. " + getName() + " is a file.");
    }

    public void listContents() {
        System.out.println("Error: Cannot list contents. " + getName() + " is a file.");
    }

    public boolean delete(String name) {
        return getName().equals(name);
    }

    public boolean recursiveDelete(String name) {
        System.out.println("Warning: Cannot recursively delete file '" + name + "'.");
        return false;
    }

    public void jumpToRoot(Stack<FileSystemComponent> currentPath, Drive root) {
        currentPath.push(root);
    }

}