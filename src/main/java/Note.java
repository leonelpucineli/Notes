import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

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

    /*private Note(String id, String title, String content, String creationDate, String lastChangeDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.lastChangeDate = lastChangeDate;
    }*/

    public String getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
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
        // Creates a String compatible with JSON
        return String.format("{\"id\": \"%s\", \"title\": \"%s\", \"content\": \"%s\", \"creationDate\": \"%s\", \"lastChangeDate\": \"%s\"}", this.id, this.title, this.content, this.creationDate, this.lastChangeDate);
    }

    public void save(String directoryPath) {

        String filePath = directoryPath + creationDate + "_" + title;
        File noteFile = new File(filePath);
        File directory = new File(directoryPath);

        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }

            if (!noteFile.exists()) {
                noteFile.createNewFile();
                System.out.println("New file created at " + filePath);
            }

            FileWriter fileWriter = new FileWriter(noteFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(createFileContent());

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
                String fileName;
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

    // getNote() needs to 'return new Note(...)'
    public String getNote(String directoryPath) {
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

        return fileContent;
    }
}