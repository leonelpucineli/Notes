public class Main {
    public static void main(String[] args) {
        int selection = 1;

        System.out.println("Welcome, user!");

        // Main loop where the things happen.
        while (selection != 0) {
            Menu.home();
            selection = Validator.forInt(0,2,"");
            System.out.println();

            switch (selection){
                case 1:
                    Note note = NoteMethods.createNewNote();
                    String noteFileContent = NoteMethods.createFileContent(note);
                    NoteMethods.saveFile(noteFileContent, note);
                    System.out.println();
                    break;
                case 2:
                    NoteMethods.list(true);
                    System.out.println();
                    break;
                default:
                    break;
            }
        }

        System.out.println("Closing program...");
    }
}