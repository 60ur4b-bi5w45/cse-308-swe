package Linux;

import java.util.Stack;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Folder implements FileSystemComponent {
    private final String name;
    private List<FileSystemComponent> children;
    private final LocalDateTime creationTime;

    private String parent;

    public Folder(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.creationTime = LocalDateTime.now();
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return children.stream().mapToLong(FileSystemComponent::getSize).sum();
    }

    public String getType() {
        return "Folder";
    }

    public String getDirectory() {
        String parentDirectory = parent + ":\\" + getName();
        return parentDirectory.startsWith(":\\" ) ? parentDirectory.substring(2) : parentDirectory;
    }

    public int getComponentCount() {
        return children.size();
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void addComponent(FileSystemComponent component) {
        component.setParent(parent+":\\"+getName());
        children.add(component);
    }

    public void removeComponent(FileSystemComponent component) {
        children.remove(component);
    }

    public void display() {
        String output = String.format("Folder: %s\nSize: %d\nType: %s\nDirectory: %s\nComponent Count: %d\nCreation Time: %s\n",
                getName(), getSize(), getType(), getDirectory(), getComponentCount(),
                getCreationTime().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy h:mm a")));
        System.out.println(output);
    }

    public void changeDirectory(String name, Stack<FileSystemComponent> currentPath) {
        for (FileSystemComponent component : children) {
            if (component.getName().equals(name) && component instanceof Folder) {
                currentPath.push(component);
                return;
            }
        }
        System.out.println("Error: Folder '" + name + "' not found in the current directory.");
    }

    public void displayDetails(String name, Stack<FileSystemComponent> currentPath) {
        if (getName().equals(name)) {
            display();
        } else {
            for (FileSystemComponent component : children) {
                component.displayDetails(name, currentPath);
            }
        }
    }

    public void listContents() {
        for (FileSystemComponent component : children) {
            String output = String.format("%s     %d     %s\n",
                    component.getName(), component.getSize(), component.getCreationTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            System.out.println(output);
        }
    }

    public boolean delete(String name) {
        for (FileSystemComponent component : new ArrayList<>(children)) {
            if (component.delete(name)) {
                children.remove(component);
                return true;
            }
        }
        return false;
    }

    public boolean recursiveDelete(String name) {
        for (FileSystemComponent component : new ArrayList<>(children)) {
            if (component.recursiveDelete(name)) {
                children.remove(component);
            }
        }
        return true;
    }

    public void jumpToRoot(Stack<FileSystemComponent> currentPath, Drive root) {
        currentPath.push(root);
    }
}
