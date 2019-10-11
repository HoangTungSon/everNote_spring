package everNote.service.impl;

import everNote.model.EverNote;
import everNote.model.Tag;
import everNote.repository.EverNoteRepository;
import everNote.service.EverNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class EverNoteServiceImpl implements EverNoteService {

    @Autowired
    private EverNoteRepository noteRepository;

    @Override
    public Page<EverNote> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @Override
    public EverNote findById(Long id) {
        return noteRepository.findOne(id);
    }

    @Override
    public void save(EverNote everNote) {
        noteRepository.save(everNote);
    }

    @Override
    public void remove(Long id) {
        noteRepository.delete(id);
    }

    @Override
    public Iterable<EverNote> findAllByTag(Tag tag) {
        return noteRepository.findAllByTagSet(tag);
    }

    @Override
    public Iterable<EverNote> findAllByUsername(String username) {
        return noteRepository.findAllByUsername(username);
    }


}
