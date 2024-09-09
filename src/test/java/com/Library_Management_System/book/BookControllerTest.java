package com.Library_Management_System.book;

import com.Library_Management_System.author.dto.AuthorDto;
import com.Library_Management_System.baseConfiguration.BaseTest;
import com.Library_Management_System.book.dto.BookDto;
import com.Library_Management_System.user.dto.LoginDto;
import com.Library_Management_System.user.entity.User;
import com.Library_Management_System.user.service.UserService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerTest extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    void addBook() {
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto authorDto = addAuthor();

        BookDto bookRequest = new BookDto();
        bookRequest.setTitle("first book");
        bookRequest.setIsbn("12134");
        bookRequest.setDescription("description");
        bookRequest.setAuthorId(authorDto.getId());

        BookDto response = requestOne("/book", HttpMethod.POST, httpHeaders, null, bookRequest, BookDto.class);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("first book");

        requestOne("/author/" + authorDto.getId(), HttpMethod.DELETE, httpHeaders, null, null, Object.class);


    }

    @Test
    void update() {
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto authorDto = addAuthor();

        BookDto bookRequest = new BookDto();
        bookRequest.setTitle("first book");
        bookRequest.setIsbn("12134");
        bookRequest.setDescription("description");
        bookRequest.setAuthorId(authorDto.getId());

        BookDto response = requestOne("/book", HttpMethod.POST, httpHeaders, null, bookRequest, BookDto.class);

        response.setTitle("test");

        BookDto updatedBook = requestOne("/book", HttpMethod.PUT, httpHeaders, null, response, BookDto.class);
        assertThat(updatedBook.getTitle()).isEqualTo("test");

        requestOne("/author/" + authorDto.getId(), HttpMethod.DELETE, httpHeaders, null, null, Object.class);
    }

    @Test
    void delete() {
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto authorDto = addAuthor();

        BookDto bookRequest = new BookDto();
        bookRequest.setTitle("first book");
        bookRequest.setIsbn("12134");
        bookRequest.setDescription("description");
        bookRequest.setAuthorId(authorDto.getId());

        BookDto response = requestOne("/book", HttpMethod.POST, httpHeaders, null, bookRequest, BookDto.class);
        assertThat(response).isNotNull();

        requestOne("/book/" + response.getId(), HttpMethod.DELETE, httpHeaders, null, null, Object.class);

        BookDto deletedBook = requestOne("/book/" + response.getId(), HttpMethod.GET, httpHeaders, null, null, BookDto.class);

        assertThat(deletedBook).isNull();
        requestOne("/author/" + authorDto.getId(), HttpMethod.DELETE, httpHeaders, null, null, Object.class);

    }

    @Test
    void getAllBooks() {
        super.login(new LoginDto("admin", "admin"));
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto authorDto = addAuthor();

        BookDto bookRequest = new BookDto();
        bookRequest.setTitle("first book");
        bookRequest.setIsbn("12134");
        bookRequest.setDescription("description");
        bookRequest.setAuthorId(authorDto.getId());

        BookDto response = requestOne("/book", HttpMethod.POST, httpHeaders, null, bookRequest, BookDto.class);
        assertThat(response).isNotNull();

        List<BookDto> arrayList = requestMany("/book", HttpMethod.GET, httpHeaders, null, null, BookDto.class, List.class);
        assertThat(arrayList.isEmpty()).isFalse();

        requestOne("/author/" + authorDto.getId(), HttpMethod.DELETE, httpHeaders, null, null, Object.class);
    }

    AuthorDto addAuthor() {
        AuthorDto requestBody = new AuthorDto();
        requestBody.setName("author3");
        requestBody.setSurname("author3");
        requestBody.setCountry("Kosova");
        requestBody.setBirthdate(LocalDate.of(1999, 4, 19));

        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        return requestOne("/author", HttpMethod.POST, httpHeaders, null, requestBody, AuthorDto.class);
    }
}