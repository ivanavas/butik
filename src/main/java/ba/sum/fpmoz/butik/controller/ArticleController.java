package ba.sum.fpmoz.butik.controller;

import ba.sum.fpmoz.butik.repositories.ArticleRepository;
import ba.sum.fpmoz.butik.repositories.CategoryRepository;
import ba.sum.fpmoz.butik.model.Article;
import ba.sum.fpmoz.butik.model.UserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.IOException;


@Controller
public class ArticleController {

    @Autowired
    ArticleRepository articleRepo;

    @Autowired
    CategoryRepository categoryRepo;

    private static String UPLOADED_FOLDER = "./uploads/";


    @GetMapping("/articles")
    public String showArticles (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("article", new Article());
        model.addAttribute("articles", articleRepo.findAll());
        model.addAttribute("added", false);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("activeLink", "Proizvodi");
        return "articles";
    }

    @GetMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id, Model model) throws IOException {
        Article article = articleRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        articleRepo.delete(article);

        return "redirect:/articles";
    }

    @GetMapping("/article/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Article article = articleRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("activeLink", "Proizvodi");
        return "article_edit";
    }

    @PostMapping("/article/edit/{id}")
    public String editArticle (@PathVariable("id") Long id, @Valid Article article, BindingResult result, Model model) {

        if (result.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) auth.getPrincipal();
            model.addAttribute("user", user);
            model.addAttribute("article", article);
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("activeLink", "Proizvodi");
            return "article_edit";
        }
        articleRepo.save(article);
        return "redirect:/articles";
    }


    @PostMapping("/article/add")
    public String addArticle (@Valid Article article, BindingResult result, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("activeLink", "Proizvodi");



        if (result.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("added", true);
            model.addAttribute("articles", articleRepo.findAll());
            model.addAttribute("categories", categoryRepo.findAll());
            return "articles";
        }
        articleRepo.save(article);
        return "redirect:/articles";
    }
}
