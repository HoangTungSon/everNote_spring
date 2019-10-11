package everNote.controller;

import everNote.model.Category;
import everNote.model.EverNote;
import everNote.model.Tag;
import everNote.service.CategoryService;
import everNote.service.EverNoteService;
import everNote.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {

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

    @GetMapping("/user")
    public ModelAndView noteList(Principal principal) {
        Iterable<EverNote> noteUser = noteService.findAllByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("/loginPage/user");
        modelAndView.addObject("notes", noteUser);
        return modelAndView;
    }

}
