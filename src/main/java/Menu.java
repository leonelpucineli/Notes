public class Menu {

    public static void home() {
        System.out.print("""
                Chose an action:
                1 - Write a new note
                2 - List my notes by title
                3 - Delete a note
                4 - Read a note
                0 - Close program
                >>> \s""");
    }
}
