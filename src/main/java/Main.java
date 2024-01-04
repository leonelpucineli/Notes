import lp.toolkit.Validator;

import javax.management.remote.NotificationResult;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String systemUserName = System.getProperty("user.name");
        System.out.println("Welcome, " + systemUserName + "!");

        int selection = 1;

        String directoryPath;

        String filePath;

        String systemOSName = System.getProperty("os.name");
        if (systemOSName.startsWith("Windows")){
            directoryPath = "C:\\Users\\" + systemUserName + "\\NoteStorage\\";
        }
        else {
            directoryPath = "/home/" + systemUserName + "/NoteStorage/";
        }

        // Main loop where the things happen.
        while (selection != 0) {
            Menu.home();
            selection = Validator.forInt(0,3,"");
            System.out.println();

            switch (selection){
                case 1:
                    Note note = NoteMethods.createNewNote();
                    String noteFileContent = NoteMethods.createFileContent(note);
                    NoteMethods.saveFile(directoryPath, noteFileContent, note);
                    System.out.println();
                    break;
                case 2:
                    System.out.println("From: " + directoryPath);
                    NoteMethods.list(directoryPath,true);
                    System.out.println();
                    break;
                case 3:
                    NoteMethods.list(directoryPath,true);
                    System.out.println("Select one to delete: ");
                    ArrayList<String> notesList = new ArrayList<String>(NoteMethods.filesArrayList(directoryPath));
                    selection = Validator.forInt(1, notesList.size(), "");
                    System.out.print("Confirm to delete " + notesList.get(selection-1) + "?\n1 - Yes\n0 - No\n>>> ");
                    int confirm = Validator.forInt(0, 1, "");
                    if (confirm == 1) {
                        filePath = directoryPath + notesList.get(selection - 1);
                        NoteMethods.delete(selection, filePath, notesList);
                    }
                    else{
                        System.out.println("Your note hasn't been deleted");
                    }
                    break;
                default:
                    break;
            }
        }

        System.out.println("Closing program...");
    }
}