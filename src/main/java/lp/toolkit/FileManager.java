package lp.toolkit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    String directoryPath;
    String fileName;
    String extension;
    String content;

    public FileManager(String directoryPath, String fileName, String extension) {
        this.directoryPath = directoryPath;
        this.fileName = fileName;
        this.extension = extension;
    }

    public void saveText(String content) {

        String filePath = directoryPath + fileName + extension;

        File file = new File(filePath);
        File directory = new File(directoryPath);

        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
                System.out.println("New file created at " + filePath);
            }

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);

            bufferedWriter.close();

            System.out.println("File saved successfully on " + file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}