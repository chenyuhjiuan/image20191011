package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@Controller


public class HomeController {
    @Autowired
    PostRepository postRepository;

    @RequestMapping("/")
    public String listPosts(Model model){
        model.addAttribute("posts", postRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String courseForm(Model model){
        model.addAttribute("post", new Post());
        return "postform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Post post, BindingResult result ){
        //model.addAttribute("course", course);
        if (result.hasErrors()){
            return "postform";
        }
        postRepository.save(post);
        return "redirect:/";
    }



    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("post", postRepository.findById(id).get());
        return "show";
    }


    @RequestMapping("/update/{id}")
    public String updatePost(@PathVariable("id") long id, Model model){
        model.addAttribute("post", postRepository.findById(id).get());
        return "postform";
    }

    @RequestMapping("/delete/{id}")
    public String delPost(@PathVariable("id") long id){
        postRepository.deleteById(id);
        return "redirect:/";
    }

}



