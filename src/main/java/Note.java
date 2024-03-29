import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import lp.toolkit.JSON;

public class Note {
    private String id;
    private String title;
    private String content;
    private String creationDate;
    private String lastChangeDate;

    Scanner keyboard = new Scanner(System.in);

    public Note() {}
        
    public Note(String title) {
        this.title = title;
    }

    public Note(String id, String title, String content, String creationDate, String lastChangeDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.lastChangeDate = lastChangeDate;
    }

    JSON noteJson = new JSON("id,title,content,creationDate,lastChangeDate");

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getLastChangeDate() {
        return lastChangeDate;
    }

    public void setId() {
        byte[] randomBytes = new byte[6];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : randomBytes) {
            hexBuilder.append(String.format("%02x", b));
        }
        this.id = hexBuilder.toString();
    }

    public void setTitle() {
        System.out.println("Choose a title: ");
        this.title = keyboard.nextLine();
    }

    public void setContent() {
        System.out.println("Write your text (use '//' instead of Enter): ");
        this.content = keyboard.nextLine();
    }

    public void setCreationDate() {
        this.creationDate = newDate();
        this.lastChangeDate = null;
    }

    public String newDate() {
        LocalDateTime creationDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return creationDate.format(formatter);
    }

    public void setLastChangeDate() {
        this.lastChangeDate = newDate();
    }

    public String createFileContent() {
        // JSON noteJson = new JSON("id,title,content,creatonDate,lastChangeDate");
        
        noteJson.setValue("id", id);
        noteJson.setValue("title", title);
        noteJson.setValue("content", content);
        noteJson.setValue("creationDate", creationDate);
        noteJson.setValue("lastChangeDate", lastChangeDate);

        return noteJson.toString();
    }

    public void save(String directoryPath) {

        String filePath = directoryPath + creationDate + "_" + title + ".txt";
        File noteFile = new File(filePath);
        File directory = new File(directoryPath);

        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }

            if (!noteFile.exists()) {
                noteFile.createNewFile();
                System.out.println("New file created");
            }

            System.out.println(1);
            FileWriter fileWriter = new FileWriter(noteFile);
            System.out.println(2);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println(3);
            bufferedWriter.write(createFileContent());
            System.out.println(4);
            bufferedWriter.close();

            System.out.println("File saved successfully on " + noteFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int selection, String filePath, ArrayList<String> notesList) {
        File file = new File(filePath);
        file.delete();
    }

    public static void list(String directoryPath, boolean onlyTitleView) {

        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                String fileName;
                int n = 0;
                for (File file : files) {
                    fileName = file.getName();
                    if (onlyTitleView) {
                        fileName = file.getName().substring(16);
                    }
                    n++;
                    System.out.println(n + " - " + fileName);
                }
            }
            else {
                System.out.println("Directory '" + directoryPath + "' is empty.");
            }
        }
        else {
            System.out.println("'" + directoryPath + "' is not an valid directory.");
        }
    }

    public static ArrayList<String> filesArrayList(String directoryPath) {
        ArrayList<String> filesArrayList = new ArrayList<String>();

        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    filesArrayList.add(file.getName());
                }
            }
            else {
                System.out.println("Directory '" + directoryPath + "' is empty.");
            }
        }
        else {
            System.out.println("'" + directoryPath + "' is not an valid directory.");
        }

        return filesArrayList;
    }

    // preciso separar setNote de get file usando o file manager.
    public void setNote(String directoryPath) {
        String fileContent = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(directoryPath+title))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("/n");
            }

            fileContent = stringBuilder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        noteJson.setKeys(fileContent);
        noteJson.setValues(fileContent);

        id = noteJson.getValue("id").toString();
        title = noteJson.getValue("title").toString();
        content = noteJson.getValue("content").toString();
        creationDate = noteJson.getValue("creationDate").toString();
        lastChangeDate = noteJson.getValue("lastChangeDate").toString();
    }

    public void getNoteInfo(boolean detailed) {
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        
        if (detailed) {
            System.out.println("ID: " + id);
            System.out.println("Creation date: " + creationDate);
            System.out.println("Changed for the last time on: " + lastChangeDate);
        } 
    }
}