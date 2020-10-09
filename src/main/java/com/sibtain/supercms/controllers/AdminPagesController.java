package com.sibtain.supercms.controllers;

import com.sibtain.supercms.models.PageRepository;
import com.sibtain.supercms.models.data.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    private PageRepository pageRepository;

    public AdminPagesController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping
    public String index(Model model){

        List<Page> pages = pageRepository.findAll();
        model.addAttribute("pages",pages);
        return "admin/pages/index";

    }
}
