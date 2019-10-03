package everNote.service;

import everNote.model.EverNote;
import everNote.model.Tag;

public interface TagService {
    Iterable<Tag> findAll();

    Tag findById(Long id);

    void save(Tag tag);

    void remove(Long id);

    Iterable<Tag> findAllByEverNote(EverNote everNote);

}
