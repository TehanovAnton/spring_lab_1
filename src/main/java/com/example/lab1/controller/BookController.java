package com.example.lab1.controller;

import com.example.lab1.forms.BookForm;
import com.example.lab1.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    private static List<Book> books = new ArrayList<Book>();
    static {
        books.add(new Book("Full Stack Development with JHipster", "Deepu K Sasidharan, Sendil Kumar N"));
        books.add(new Book("Pro Spring Security", "Carlo Scarioni, Massimo Nardone"));
    }

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index_book");
        model.addAttribute("message", message);
        return modelAndView;
    }

    @RequestMapping(value = { "/personal_list" }, method = RequestMethod.GET)
    public ModelAndView personalList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("book_list");
        model.addAttribute("books", books);
        return modelAndView;
    }

    @RequestMapping(value = { "/add_book_page" }, method = RequestMethod.GET)
    public ModelAndView showAddBookPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("add_book");
        BookForm bookForm = new BookForm();
        model.addAttribute("bookform", bookForm);
        return modelAndView;
    }

    @RequestMapping(value = { "/add_book_page" }, method = RequestMethod.POST)
    public ModelAndView savePerson(Model model, @ModelAttribute("bookform") BookForm bookForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("book_list");

        String title = bookForm.getTitle();
        String author = bookForm.getAuthor();

        if (title != null && title.length() > 0 && author != null && author.length() > 0) {
            Book newBook = new Book(title, author);
            books.add(newBook);
            model.addAttribute("books",books);
            return modelAndView;
        }

        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("add_book");
        return modelAndView;
    }
}
