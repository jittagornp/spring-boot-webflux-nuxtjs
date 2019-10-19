package com.pamarin.learning.webflux.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Mono;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public Mono<String> index(final Model model) {
        model.addAttribute("access_token", UUID.randomUUID().toString());
        return Mono.just("index");
    }

    @GetMapping({
        "/{path:[^\\.]*}", 
        "/{path1:^(?!oauth).*}/{path2:[^\\.]*}",
        "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}",
        "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}/{path4:[^\\.]*}",
        "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}/{path4:[^\\.]*}/{path5:[^\\.]*}",
        "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}/{path4:[^\\.]*}/{path5:[^\\.]*}/{path6:[^\\.]*}"
    })
    public Mono<String> forward(final Model model) {
        return index(model);
    }
}
