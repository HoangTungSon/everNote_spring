package everNote.controller;

import everNote.model.Category;
import everNote.model.EverNote;
import everNote.model.Tag;
import everNote.service.CategoryService;
import everNote.service.EverNoteService;
import everNote.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;

@Controller
@SessionAttributes("note")
public class EverNoteController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EverNoteService noteService;

    @Autowired
    private TagService tagService;


    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("tags")
    public Iterable<Tag> tags() {
        return tagService.findAll();
    }

    @GetMapping("/notes")
    public ModelAndView noteList(Pageable pageable) {
        Page<EverNote> noteList = noteService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/everNote/list");
        modelAndView.addObject("notes", noteList);
        return modelAndView;
    }

    @GetMapping("/create-note")
    public ModelAndView createForm() {
        return new ModelAndView("/everNote/create", "note", new EverNote());
    }

    @PostMapping("/create-note")
    public ModelAndView saveNote(@ModelAttribute("note") EverNote note, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/everNote/create");
        if (!bindingResult.hasErrors()) {
            modelAndView.addObject("message", "Create success");
        } else {
            modelAndView.addObject("message", "fail to create");
        }
        noteService.save(note);
        modelAndView.addObject("note", new EverNote());
        return modelAndView;
    }

    @GetMapping("/tag-create")
    public ModelAndView createTag(@CookieValue(value = "date", defaultValue = "") String date,
                                  @CookieValue(value = "goal", defaultValue = "") String goal,
                                  @CookieValue(value = "content", defaultValue = "") String content,
                                  @CookieValue(value = "attendees", defaultValue = "") String attendees,
                                  @ModelAttribute("note") EverNote note
                                  ) {
        Cookie cookieDate = new Cookie("date", date);
        Cookie cookieGoal = new Cookie("goal", goal);
        Cookie cookieContent = new Cookie("content", content);
        Cookie cookieAttendees = new Cookie("attendees", attendees);

        date = note.getDate();
        goal = note.getGoal();
        content = note.getContent();
        attendees = note.getAttendees();

        ModelAndView modelAndView = new ModelAndView("/everNote/create");
        modelAndView.addObject("date", date);
        modelAndView.addObject("goal", goal);
        modelAndView.addObject("content", content);
        modelAndView.addObject("attendees", attendees);

        return modelAndView;
    }

    @GetMapping("/edit-note/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id) {
        EverNote note = noteService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/everNote/edit");
        modelAndView.addObject("note", note);
        modelAndView.addObject("frameId", id);
        return modelAndView;
    }

    @PostMapping("/edit-note")
    public ModelAndView updateNote(@ModelAttribute("note") EverNote note) {
        noteService.save(note);
        return new ModelAndView("/everNote/edit", "note", note);
    }

    @GetMapping("/delete-note/{id}")
    public ModelAndView deleteForm(@PathVariable("id") Long id) {
        EverNote note = noteService.findById(id);
        return new ModelAndView("/everNote/delete", "note", note);
    }

    @PostMapping("/delete-note")
    public String deleteNote(@ModelAttribute("note") EverNote note) {
        noteService.remove(note.getId());
        return "redirect:notes";
    }

    @GetMapping("/view-note/{id}")
    public ModelAndView viewNote(@PathVariable("id") Long id) {
        EverNote note = noteService.findById(id);
        Iterable<Tag> tags = tagService.findAllByEverNote(note);
        ModelAndView modelAndView = new ModelAndView("/everNote/view");
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("note", note);
        return modelAndView;
    }

}
