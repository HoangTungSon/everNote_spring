package everNote.controller;

import everNote.model.Category;
import everNote.model.EverNote;
import everNote.model.Tag;
import everNote.model.User;
import everNote.service.CategoryService;
import everNote.service.EverNoteService;
import everNote.service.TagService;
import everNote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private UserService userService;

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
        if (note == null) {
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

    @GetMapping("/registration")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("/loginPage/registerPage");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registerSuccessfully(@ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/loginPage/registerPage");

        if (!bindingResult.hasErrors()) {
            user.setRoles("USER");

            userService.save(user);
            modelAndView.addObject("message", "successfully creating new user");
        } else {
            modelAndView.addObject("message", "failed to create new user");
        }
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @GetMapping("/change-password")
    public ModelAndView passwordChange(Principal principal) {
        String name = principal.getName();
        User user = userService.findByUsername(name);
        ModelAndView modelAndView = new ModelAndView("/loginPage/changePassword");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/change-password")
    public ModelAndView passwordUpdate(@ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/loginPage/changePassword");
        if (!bindingResult.hasErrors()) {
            userService.save(user);
            modelAndView.addObject("message", "successfully creating new user");
        } else {
            modelAndView.addObject("message", "failed to create nedw user");
        }
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
