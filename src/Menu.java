public class Menu {

    public static void home() {
        System.out.print("""
                Chose an action:
                1 - Write a new note
                2 - List my notes by title
                0 - Close program
                >>> \s""");
    }
}
