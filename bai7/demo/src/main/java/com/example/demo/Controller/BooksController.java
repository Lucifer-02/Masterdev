package com.example.demo.Controller;


import java.util.Date;
import java.util.List;


import com.example.demo.Exceptions.DateExceptions;
import com.example.demo.Exceptions.NotFoundExceptions;
import com.example.demo.Exceptions.TooLongSize;
import com.example.demo.Model.Book;
import com.example.demo.Repo.BooksRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final int RECORDS_LIMIT= 10_000;
    @Autowired
    private BooksRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Book> getBook(@RequestParam(value = "page") int page,
                                @RequestParam(value = "size") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("public_date").descending());
        Page<Book> result = repository.findAll(pageable);
        return result.getContent();
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Book addBook(@RequestBody Book book) {
        book.set_id(ObjectId.get());
        repository.save(book);
        return book;
    }

    @RequestMapping(value = "/searchByTitleAndAuthor", method = RequestMethod.GET)
    public List<Book> searchByTitleAndAuthor(@RequestParam(value = "title") String title,
                                              @RequestParam(value = "author") String author) {
        List<Book> result = repository.findByTitleAndAuthor(title, author);
        if (result.size() == 0) {
            throw new NotFoundExceptions("Not found any books");
        } else if (result.size() >= RECORDS_LIMIT) {
            throw new TooLongSize("The payload size is too large!!!");
        }
        return repository.findByTitleAndAuthor(title, author);
    }

    @RequestMapping(value = "/listByDateRange", method = RequestMethod.GET)
    public List<Book> listByDateRange(@RequestParam(value = "begin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin,
                                       @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        if (begin.after(end)) {
            throw new DateExceptions("Invalid date!!!");
        }
        List<Book> result = repository.findByDateRange(begin, end);

        if (result.size() == 0) {
            throw new NotFoundExceptions("Not found any records!!!");
        } else if (result.size() >= RECORDS_LIMIT) {
            throw new TooLongSize("The payload size is too large!!!");
        }
        return result;
    }
}
