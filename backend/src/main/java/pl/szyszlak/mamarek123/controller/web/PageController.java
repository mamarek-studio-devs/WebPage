package pl.szyszlak.mamarek123.controller.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.szyszlak.mamarek123.service.interfaces.BlogService;

@Controller
public class PageController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", blogService.getAllPosts());
        return "index";
    }
}