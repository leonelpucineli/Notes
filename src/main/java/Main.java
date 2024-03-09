import lp.toolkit.Validator;

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
            selection = Validator.forInt(0,4,"");
            System.out.println();

            switch (selection){
                case 1 -> {

                    // Instantiate a new note
                    Note note = new Note();
                    note.setContent();
                    note.setTitle();
                    note.setCreationDate();
                    note.setId();

                    // Saves the content
                    note.save(directoryPath);
                    System.out.println();
                }
                case 2 -> {
                    System.out.println("From: " + directoryPath);
                    Note.list(directoryPath,true);
                    System.out.println();
                }
                case 3 -> {
                    Note.list(directoryPath,true);
                    System.out.println("Select one to delete: ");
                    ArrayList<String> notesList = new ArrayList<String>(Note.filesArrayList(directoryPath));
                    selection = Validator.forInt(1, notesList.size(), "");
                    System.out.print("Confirm to delete " + notesList.get(selection-1) + "?\n1 - Yes\n0 - No\n>>> ");
                    int confirm = Validator.forInt(0, 1, "");
                    if (confirm == 1) {
                        filePath = directoryPath + notesList.get(selection - 1);
                        Note.delete(selection, filePath, notesList);
                    }
                    else{
                        System.out.println("Your note hasn't been deleted");
                    }
                }
                case 4 -> {
                    System.out.println("Choose a note: ");
                    Note.list(directoryPath,true);
                    ArrayList<String> notesList = new ArrayList<String>(Note.filesArrayList(directoryPath));
                    selection = Validator.forInt(1, notesList.size(), "");
                    Note note = new Note(notesList.get(selection-1));
                    note.setNote(directoryPath);
                    note.getNoteInfo(false);

                    System.out.println();
                }
                default -> {
                }
            }
        }

        System.out.println("Closing program...");
    }
}