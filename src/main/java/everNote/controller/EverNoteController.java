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
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

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

    @ModelAttribute("note")
    public EverNote note(@ModelAttribute("note") EverNote note) {
        if(note == null){
            note = new EverNote();
        }
        return note;
    }

    @GetMapping("/notes")
    public ModelAndView noteList(Pageable pageable) {
        Page<EverNote> noteList = noteService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/everNote/list");
        modelAndView.addObject("notes", noteList);
        return modelAndView;
    }

    @GetMapping("/create-note")
    public ModelAndView createForm(@ModelAttribute("note") EverNote note) {

        ModelAndView modelAndView = new ModelAndView("/everNote/create");

        modelAndView.addObject("note", note);

        return modelAndView;
    }

    @PostMapping("/create-note")
    public ModelAndView saveNote(@ModelAttribute("note") EverNote note, BindingResult bindingResult, Principal principal
    ) {
        ModelAndView modelAndView = new ModelAndView("/everNote/create");
        if (!bindingResult.hasErrors()) {
            modelAndView.addObject("message", "Create success");
        } else {
            modelAndView.addObject("message", "fail to create");
        }
        note.setUsername(principal.getName());
        noteService.save(note);
        modelAndView.addObject("note", note);
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
