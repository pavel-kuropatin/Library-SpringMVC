package com.kuropatin.library.services;

import com.kuropatin.library.models.Author;
import com.kuropatin.library.models.Book;
import com.kuropatin.library.repository.impl.AuthorRepositoryImpl;
import com.kuropatin.library.repository.impl.BookAuthorRepositoryImpl;
import com.kuropatin.library.repository.impl.BookRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepositoryImpl bookRepositoryImpl;
    private final AuthorRepositoryImpl authorRepositoryImpl;
    private final BookAuthorRepositoryImpl bookAuthorRepositoryImpl;

    @Autowired
    public BookService(BookRepositoryImpl bookRepositoryImpl, AuthorRepositoryImpl authorRepositoryImpl, BookAuthorRepositoryImpl bookAuthorRepositoryImpl) {
        this.bookRepositoryImpl = bookRepositoryImpl;
        this.authorRepositoryImpl = authorRepositoryImpl;
        this.bookAuthorRepositoryImpl = bookAuthorRepositoryImpl;
    }

    public void createBook(Book book) {
        bookRepositoryImpl.createBook(book);
        bookAuthorRepositoryImpl.createBookAuthor(getBookId(book), book.getBookAuthorId());
    }

    public void updateBook(long id, Book book) {
        bookRepositoryImpl.updateBook(id, book);
    }

    public void deleteBook(long id) {
        bookRepositoryImpl.deleteBook(id);
    }

    public void addBookAuthor(Book book) {
        bookAuthorRepositoryImpl.createBookAuthor(book.getId(), book.getBookAuthorId());
    }

    public void removeBookAuthor(Book book) {
        bookAuthorRepositoryImpl.deleteBookAuthor(book.getId(), book.getBookAuthorId());
    }

    public long getBookId(Book book) {
        Book bookForId = bookRepositoryImpl.getBookByName(book.getName());
        return bookForId.getId();
    }

    public boolean isBookExists(String bookName) {
        Book bookForValidation = bookRepositoryImpl.getBookByName(bookName);
        return bookForValidation != null;
    }

    public boolean isLastAuthor(long id) {
        List<Author> listOfBookAuthors = authorRepositoryImpl.getAuthorsByBookId(id);
        return listOfBookAuthors.size() < 2;
    }
}
