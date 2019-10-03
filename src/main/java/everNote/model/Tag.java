package everNote.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tagSet")
    private Set<EverNote> everNoteSet;

    public Tag(){

    }

    public Tag(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EverNote> getEverNoteSet() {
        return everNoteSet;
    }

    public void setEverNoteSet(Set<EverNote> everNoteSet) {
        this.everNoteSet = everNoteSet;
    }
}
