package com.example.lab1.controller;

import com.example.lab1.forms.BookForm;
import com.example.lab1.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping
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

    @GetMapping(value = {"/", "/index"})
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index_book");
        model.addAttribute("message", message);
        log.info("/index was called");
        return modelAndView;
    }

    @GetMapping(value = { "/book_list_page" })
    public ModelAndView personalList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("book_list");
        model.addAttribute("books", books);
        log.info("/book_list_page was called");
        return modelAndView;
    }


    @GetMapping(value = { "/add_book_page" })
    public ModelAndView showAddBookPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("add_book");
        BookForm bookForm = new BookForm();
        model.addAttribute("bookform", bookForm);
        log.info("/add_book_page was called");
        return modelAndView;
    }

    @PostMapping(value = { "/add_book_page" })
    public ModelAndView saveBook(Model model, @ModelAttribute("bookform") BookForm bookForm) {
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

    @GetMapping(value = { "/update_book_page" })
    public ModelAndView show_update_book(Model model, @RequestParam String book_title) {
        Book book = books.stream().filter(b -> b.getTitle().equals(book_title)).findFirst().get();
        ModelAndView modelAndView = new ModelAndView("update_book");
        model.addAttribute("book", book);
        model.addAttribute("bookForm", new BookForm());
        return modelAndView;
    }

    @PostMapping(value = { "/update_book_page" })
    public ModelAndView update_book(Model model, @ModelAttribute("bookForm")BookForm bookForm, @RequestParam String by_title) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("book_list");

        Book book = books.stream().filter(b -> b.getTitle().equals(by_title)).findFirst().get();
        book.setTitle(bookForm.getTitle());
        book.setAuthor(bookForm.getAuthor());
        model.addAttribute("books",books);

        return modelAndView;
    }

    @PostMapping(value = { "/delete_book" })
    public ModelAndView deleteBookAction(Model model, @RequestParam String book_title) {
        books.removeIf(book -> book_title.equals(book.getTitle()));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("book_list");
        model.addAttribute("books", books);
        return modelAndView;
    }
}
