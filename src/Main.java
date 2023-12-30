public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome, user!");

        int selection = 1;
        String systemUserName = System.getProperty("user.name");
        String directoryPath = "/home/" + systemUserName + "/NoteStorage/";

        String systemOSName = System.getProperty("os.name");
        if (systemOSName.substring(0,7) == "Windows"){
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