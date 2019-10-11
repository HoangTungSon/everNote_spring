package everNote.repository;

import everNote.model.Category;
import everNote.model.EverNote;
import everNote.model.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EverNoteRepository extends PagingAndSortingRepository<EverNote, Long> {
    Iterable<EverNote> findAllByCategory(Category category);

    Iterable<EverNote> findAllByTagSet(Tag tag);

    Iterable<EverNote> findAllByUsername(String username);
}
