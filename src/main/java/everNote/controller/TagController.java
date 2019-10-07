package everNote.controller;

import everNote.model.EverNote;
import everNote.model.Tag;
import everNote.service.EverNoteService;
import everNote.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TagController {

    @Autowired
    private EverNoteService noteService;

    @Autowired
    private TagService tagService;

    @ModelAttribute("notes")
    public Iterable<EverNote> notes(Pageable pageable){return noteService.findAll(pageable);}

    @GetMapping("/tags")
    public ModelAndView listTags(){
        Iterable<Tag> tags = tagService.findAll();
        ModelAndView modelAndView = new ModelAndView("/tag/list");
        modelAndView.addObject("tags", tags);
        return modelAndView;
    }

    @GetMapping("/create-tag")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/tag/create");
        modelAndView.addObject("tag", new Tag());
        return modelAndView;
    }

    @PostMapping("/create-tag")
    public ModelAndView saveTag(@ModelAttribute("tag") Tag tag){
        tagService.save(tag);

        ModelAndView modelAndView = new ModelAndView("/tag/create");
        modelAndView.addObject("tag", new Tag());
        modelAndView.addObject("message", "New tag was created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-tag/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Tag tag = tagService.findById(id);
        if(tag != null) {
            ModelAndView modelAndView = new ModelAndView("/tag/edit");
            modelAndView.addObject("tag", tag);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-tag")
    public ModelAndView updateTag(@ModelAttribute("tag") Tag tag){
        tagService.save(tag);
        ModelAndView modelAndView = new ModelAndView("/tag/edit");
        modelAndView.addObject("tag", tag);
        modelAndView.addObject("message", "Tag was updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-tag/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Tag tag = tagService.findById(id);
        if(tag != null) {
            ModelAndView modelAndView = new ModelAndView("/tag/delete");
            modelAndView.addObject("tag", tag);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-tag")
    public String deleteTag(@ModelAttribute("tag") Tag tag){
        tagService.remove(tag.getId());
        return "redirect:provinces";
    }

}
