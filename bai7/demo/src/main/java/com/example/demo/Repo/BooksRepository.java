package com.example.demo.Repo;


import com.example.demo.Model.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface BooksRepository extends MongoRepository<Book, String>, PagingAndSortingRepository<Book, String> {
    Book findBy_id(ObjectId _id);

    long deleteBy_id(ObjectId _id);

    @Query(
            "{'public_date':{$gt : ?0, $lt: ?1}}"
    )
    List<Book> findByDateRange(Date begin, Date end);

    @Query("{'title':{$regex:?0}, 'author': {$regex: ?1}}")
    List<Book> findByTitleAndAuthor(String title, String author);
}
