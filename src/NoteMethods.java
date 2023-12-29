import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class NoteMethods {

    static Scanner keyboard = new Scanner(System.in);

    public static String newNoteId() {
        byte[] randomBytes = new byte[6];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : randomBytes) {
            hexBuilder.append(String.format("%02x", b));
        }
        return hexBuilder.toString();
    }

    public static String newTitle() {
        System.out.println("Choose a title: ");
        return keyboard.nextLine();
    }

    public static String newContent() {
        System.out.println("Write your text (use '//' instead of Enter): ");
        return keyboard.nextLine();
    }

    public static String newDate() {
        LocalDateTime creationDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return creationDate.format(formatter);
    }

    public static Note createNewNote() {

        String content = newContent();
        String title = newTitle();
        String id = newNoteId();
        String creationDate = newDate();

        return new Note(id, title, content, creationDate, "null");
    }

    public static String createFileContent(Note note) {
        return String.format("{\"id\": \"%s\", \"title\": \"%s\", \"content\": \"%s\", \"creationDate\": \"%s\", \"lastChangeDate\": \"%s\"}", note.id, note.title, note.content, note.creationDate, note.lastChangeDate);
    }

    public static void saveFile(String noteFileContent, Note note) {

        String directoryPath = System.getProperty("user.home") + "/NotesStorage/";
        String filePath = directoryPath + note.creationDate + "_" + note.title;
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

            bufferedWriter.write(noteFileContent);

            bufferedWriter.close();

            System.out.println("File saved successfully on " + noteFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void list(boolean onlyTitleView) {
        String directoryPath = System.getProperty("user.home") + "/NotesStorage/";

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
            System.out.println(directoryPath + " is not an valid directory.");
        }

    }
    
}