public class Main {
    public static void main(String[] args) {

        String systemUserName = System.getProperty("user.name");
        System.out.println("Welcome, " + systemUserName + "!");

        int selection = 1;

        String directoryPath = "/home/" + systemUserName + "/NoteStorage/";

        String systemOSName = System.getProperty("os.name");
        if (systemOSName.startsWith("Windows")){
            directoryPath = "C:\\Users\\" + systemUserName + "\\NoteStorage\\";
        }

        // Main loop where the things happen.
        while (selection != 0) {
            Menu.home();
            selection = Validator.forInt(0,2,"");
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
                default:
                    break;
            }
        }

        System.out.println("Closing program...");
    }
}