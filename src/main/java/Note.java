public class Note {
    String id;
    String title;
    String content;
    String creationDate;
    String lastChangeDate;

    public Note(String id, String title, String content, String creationDate, String lastChangeDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.lastChangeDate = lastChangeDate;
    }
}
