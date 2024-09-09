package com.Library_Management_System.author;

import com.Library_Management_System.author.dto.AuthorDto;
import com.Library_Management_System.baseConfiguration.BaseTest;
import com.Library_Management_System.user.dto.LoginDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AuthorControllerTest extends BaseTest {

    @Test
    void addAuthor() {
        super.login(new LoginDto("admin", "admin"));
        AuthorDto requestBody = new AuthorDto();
        requestBody.setName("lavdim");
        requestBody.setSurname("krasniqi");
        requestBody.setCountry("Kosova");
        requestBody.setBirthdate(LocalDate.of(1999, 4, 19));

        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto authorDto = requestOne("/author", HttpMethod.POST, httpHeaders, null, requestBody, AuthorDto.class);
        assertThat(authorDto).isNotNull();
        assertThat(authorDto.getName()).isEqualTo("lavdim");
    }

    @Test
    void addAuthorFailure() {
        super.login(new LoginDto("user", "user"));
        AuthorDto requestBody = new AuthorDto();
        requestBody.setName("lavdim1");
        requestBody.setSurname("krasniqi");
        requestBody.setCountry("Kosova");
        requestBody.setBirthdate(LocalDate.of(1999, 4, 19));

        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        assertThrows(WebClientResponseException.Forbidden.class,
                () -> requestOne("/author", HttpMethod.POST, httpHeaders, null, requestBody, AuthorDto.class));

    }

    @Test
    void updateAuthor() {
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto requestBody = new AuthorDto();
        requestBody.setName("erik");
        requestBody.setSurname("smith");
        requestBody.setCountry("America");
        requestBody.setBirthdate(LocalDate.of(1999, 4, 19));

        AuthorDto authorDto = requestOne("/author", HttpMethod.POST, httpHeaders, null, requestBody, AuthorDto.class);
        requestBody.setName("test");
        requestOne("/author/" + authorDto.getId(), HttpMethod.PUT, httpHeaders, null, requestBody, Object.class);
        AuthorDto updatedAuthor = requestOne("/author/getDetails/" + authorDto.getId(), HttpMethod.GET, httpHeaders, null, null, AuthorDto.class);

        assertThat(updatedAuthor.getName()).isEqualTo("test");

    }

    @Test
    void deleteAuthor() {
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto requestBody = new AuthorDto();
        requestBody.setName("author1");
        requestBody.setSurname("author1");
        requestBody.setCountry("America");
        requestBody.setBirthdate(LocalDate.of(1999, 4, 19));

        AuthorDto authorDto = requestOne("/author", HttpMethod.POST, httpHeaders, null, requestBody, AuthorDto.class);
        assertThat(authorDto).isNotNull();

        requestOne("/author/" + authorDto.getId(), HttpMethod.DELETE, httpHeaders, null, null, Object.class);

        AuthorDto deletedAuthor = requestOne("/author/getDetails/" + authorDto.getId(), HttpMethod.GET, httpHeaders, null, null, AuthorDto.class);

        assertThat(deletedAuthor).isNull();
    }

    @Test
    void retrieveAllAuthors() {
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto requestBody = new AuthorDto();
        requestBody.setName("author2");
        requestBody.setSurname("author1");
        requestBody.setCountry("America");
        requestBody.setBirthdate(LocalDate.of(1999, 4, 19));

        AuthorDto authorDto = requestOne("/author", HttpMethod.POST, httpHeaders, null, requestBody, AuthorDto.class);
        assertThat(authorDto).isNotNull();

        List<AuthorDto> arrayList = requestMany("/author", HttpMethod.GET, httpHeaders, null, null, AuthorDto.class, List.class);
        assertThat(arrayList.isEmpty()).isFalse();

    }

    @Test
    void getAuthorDetails() {
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        AuthorDto requestBody = new AuthorDto();
        requestBody.setName("author2");
        requestBody.setSurname("author2");
        requestBody.setCountry("America");
        requestBody.setBirthdate(LocalDate.of(1999, 4, 19));

        AuthorDto authorDto = requestOne("/author", HttpMethod.POST, httpHeaders, null, requestBody, AuthorDto.class);
        assertThat(authorDto).isNotNull();

        AuthorDto authorDetails = requestOne("/author/getDetails/" + authorDto.getId(), HttpMethod.GET, httpHeaders, null, null, AuthorDto.class);

        assertThat(authorDetails).isNotNull();
        assertThat(authorDetails.getName()).isEqualTo("author2");

    }
}