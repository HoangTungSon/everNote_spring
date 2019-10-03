package everNote.repository;

import everNote.model.EverNote;
import everNote.model.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    Iterable<Tag> findAllByEverNoteSet(EverNote note);
}
