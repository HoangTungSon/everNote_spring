package everNote.service.impl;

import everNote.model.EverNote;
import everNote.model.Tag;
import everNote.repository.TagRepository;
import everNote.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Iterable<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public void remove(Long id) {
        tagRepository.delete(id);
    }

    @Override
    public Iterable<Tag> findAllByEverNote(EverNote everNote) {
        return tagRepository.findAllByEverNoteSet(everNote);
    }
}
