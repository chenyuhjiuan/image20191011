package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;

//import org.springframework.util.ObjectUtils;
@Controller

public class ImageController {
    @Autowired
    ActorRepository actorRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/pictures")
    public String listActors(Model model){
        model.addAttribute("actors", actorRepository.findAll());
        return "imagelist";
    }

    @GetMapping("/load")
    public String newActor(Model model){
        model.addAttribute("actor", new Actor());
        return "imageform";
    }

    @PostMapping("/load")
    public String processActor(@ModelAttribute Actor actor, @RequestParam("file") MultipartFile file, Model model){
        model.addAttribute("actor",actor);
        if (file.isEmpty()){
            return "redirect:/load";
        }
        try {
            Map uploadResult =cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            actor.setHeadshot(uploadResult.get("url").toString());
            actorRepository.save(actor);
        } catch (IOException e){
            e.printStackTrace();
            return "redirect:/load";
        }
        return "redirect:/pictures";
    }
}
