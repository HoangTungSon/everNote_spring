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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("note")
public class EverNoteController {

    private void cookieUpload(@RequestParam("goalNote") String goalParam, @RequestParam("dateNote") String dateParam, @RequestParam("attendeeNote") String attendeeParam, @RequestParam("contentNote") String contentParam, HttpServletResponse response, ModelAndView modelAndView) {
        Cookie cookie1 = new Cookie("goal", goalParam);
        cookie1.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("date", dateParam);
        cookie2.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie2);

        Cookie cookie3 = new Cookie("attendees", attendeeParam);
        cookie3.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie3);

        Cookie cookie4 = new Cookie("content", contentParam);
        cookie4.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie4);

        modelAndView.addObject("goalNote", cookie1);
        modelAndView.addObject("dateNote", cookie2);
        modelAndView.addObject("attendeeNote", cookie3);
        modelAndView.addObject("contentNote", cookie4);
    }

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
    public EverNote note() {
        return new EverNote();
    }

    @GetMapping("/notes")
    public ModelAndView noteList(Pageable pageable) {
        Page<EverNote> noteList = noteService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/everNote/list");
        modelAndView.addObject("notes", noteList);
        return modelAndView;
    }

    @GetMapping("/create-note")
    public ModelAndView createForm(@CookieValue(value = "goal", defaultValue = "") String goal,
                                   @CookieValue(value = "date", defaultValue = "") String date,
                                   @CookieValue(value = "attendees", defaultValue = "") String attendees,
                                   @CookieValue(value = "content", defaultValue = "") String content,
                                   HttpServletResponse response, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/everNote/create");

        cookieUpload(goal, date, attendees, content, response, modelAndView);

        modelAndView.addObject("note", new EverNote());

        return modelAndView;
    }

    @PostMapping("/create-note-post")
    public ModelAndView saveNote(@ModelAttribute("note") EverNote note, BindingResult bindingResult,
                                 @CookieValue(value = "goal", defaultValue = "") String goal,
                                 @CookieValue(value = "date", defaultValue = "") String date,
                                 @CookieValue(value = "attendees", defaultValue = "") String attendees,
                                 @CookieValue(value = "content", defaultValue = "") String content,

                                 @RequestParam("goalNote") String goalParam,
                                 @RequestParam("dateNote") String dateParam,
                                 @RequestParam("attendeeNote") String attendeeParam,
                                 @RequestParam("contentNote") String contentParam,

                                 HttpServletResponse response, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("/everNote/create");
        if (!bindingResult.hasErrors()) {
            modelAndView.addObject("message", "Create success");
        } else {
            modelAndView.addObject("message", "fail to create");
        }

        cookieUpload(goalParam, dateParam, attendeeParam, contentParam, response, modelAndView);

        note.setGoal(goalParam);
        note.setDate(dateParam);
        note.setContent(contentParam);
        note.setAttendees(attendeeParam);

        noteService.save(note);
        modelAndView.addObject("note", new EverNote());
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
