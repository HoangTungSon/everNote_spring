package everNote.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "EverNotes")
public class EverNote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String date;
    private String goal;
    private String attendees;
    private String content;
    private String username;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "tag_like",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagSet;


    public Set<Tag> getTagSet() {
        return tagSet;
    }

    public void setTagSet(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    public EverNote() {

    }

    public EverNote(String date, String goal, String attendees, String content) {
        this.date = date;
        this.goal = goal;
        this.attendees = attendees;
        this.content = content;
    }

    public EverNote(String date, String goal, String attendees, String content, String username, Category category, Set<Tag> tagSet) {
        this.date = date;
        this.goal = goal;
        this.attendees = attendees;
        this.content = content;
        this.username = username;
        this.category = category;
        this.tagSet = tagSet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getAttendees() {
        return this.attendees;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
