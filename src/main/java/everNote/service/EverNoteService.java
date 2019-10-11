package everNote.service;

import everNote.model.EverNote;
import everNote.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EverNoteService {
    Page<EverNote> findAll(Pageable pageable);

    EverNote findById(Long id);

    void save(EverNote everNote);

    void remove(Long id);

    Iterable<EverNote> findAllByTag(Tag tag);

    Iterable<EverNote> findAllByUsername(String username);

}
