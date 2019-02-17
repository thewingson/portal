package com.almat;

import com.almat.domain.Post;
import com.almat.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Post> posts = postRepository.findAll();

        model.put("posts", posts);

        return "main";
    }

    @PostMapping
    public String add(@RequestParam String title, @RequestParam String text, Map<String, Object> model) {
        Post post = new Post(title, text);

        postRepository.save(post);

        Iterable<Post> posts = postRepository.findAll();

        model.put("posts", posts);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Post> posts;

        if (filter != null && !filter.isEmpty()) {
            posts = postRepository.findByText(filter);
        } else {
            posts = postRepository.findAll();
        }

        model.put("posts", posts);

        return "main";
    }
}