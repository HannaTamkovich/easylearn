package com.easylearn.easylearn.mock;

import com.easylearn.easylearn.category.model.Category;
import com.easylearn.easylearn.category.repository.CategoryRepository;
import com.easylearn.easylearn.category.repository.converter.CategoryEntityConverter;
import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.UserRepository;
import com.easylearn.easylearn.security.user.repository.converter.UserEntityConverter;
import com.easylearn.easylearn.security.user.service.UserService;
import com.easylearn.easylearn.word.model.Word;
import com.easylearn.easylearn.word.repository.WordRepository;
import com.easylearn.easylearn.word.repository.WordToUserRepository;
import com.easylearn.easylearn.word.repository.converter.WordEntityConverter;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Transactional
@Component
@RequiredArgsConstructor
@Slf4j
public class MockDataCreator {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    private final CategoryEntityConverter categoryEntityConverter;
    private final UserEntityConverter userEntityConverter;

    private final WordRepository wordRepository;
    private final WordEntityConverter wordEntityConverter;
    private final WordToUserRepository wordToUserRepository;

    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void createMockData() {
        log.info("Create mock data for local env");

        //createUsers();
        //createWords();
        //createCategories();
    }

    private void createWords() {
        var words = List.of(
                Word.builder()
                        .word("Dog")
                        .translation("????????????")
                        .language(Language.ENGLISH)
                        .build(),
                Word.builder()
                        .word("Cat")
                        .translation("??????")
                        .language(Language.ENGLISH)
                        .build()
        );
        wordRepository.saveAll(wordEntityConverter.toEntities(words));

        /*var wordToUsers = List.of(
                WordToUserEntity.builder()
                        .wordId(1L)
                        .userId(1L)
                        .numberOfAnswers(0L)
                        .numberOfCorrectAnswers(0L)
                        .dateOfLastAnswer(Instant.now())
                        .build(),
                WordToUserEntity.builder()
                        .wordId(2L)
                        .userId(1L)
                        .numberOfAnswers(0L)
                        .numberOfCorrectAnswers(0L)
                        .dateOfLastAnswer(Instant.now())
                        .build()
        );
        wordToUserRepository.saveAll(wordToUsers);*/
    }

    private void createUsers() {
        var users = List.of(
                /*UserAccount.builder()
                        .username("admin")
                        .firstName("admin")
                        .middleName("admin")
                        .secondName("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .language(Language.ENGLISH)
                        .role(Role.ADMIN)
                        .dateOfLastVisit(Instant.now())
                        .build(),*/
                User.builder()
                        .username("user")
                        .firstName("user")
                        .middleName("user")
                        .secondName("user")
                        .email("user@gmail.com")
                        .password(passwordEncoder.encode("user"))
                        .language(Language.ENGLISH)
                        .dateOfLastVisit(Instant.now())
                        .build()
                /*,
                UserAccount.builder()
                        .username("german_a")
                        .firstName("german")
                        .middleName("german")
                        .secondName("german")
                        .email("german@gmail.com")
                        .password(passwordEncoder.encode("german_a"))
                        .language(Language.GERMAN)
                        .dateOfLastVisit(Instant.now())
                        .build(),
                UserAccount.builder()
                        .username("german_u")
                        .firstName("german")
                        .middleName("german")
                        .secondName("german")
                        .email("german@gmail.com")
                        .password(passwordEncoder.encode("german_u"))
                        .language(Language.GERMAN)
                        .dateOfLastVisit(Instant.now())
                        .build(),
                UserAccount.builder()
                        .username("spanish_a")
                        .firstName("spanish")
                        .middleName("spanish")
                        .secondName("spanish")
                        .email("spanish@gmail.com")
                        .password(passwordEncoder.encode("spanish_a"))
                        .language(Language.SPANISH)
                        .dateOfLastVisit(Instant.now())
                        .build(),
                UserAccount.builder()
                        .username("spanish_u")
                        .firstName("spanish")
                        .middleName("spanish")
                        .secondName("spanish")
                        .email("spanish@gmail.com")
                        .password(passwordEncoder.encode("spanish_u"))
                        .language(Language.SPANISH)
                        .role(.USER)
                        .dateOfLastVisit(Instant.now())
                        .build()*/
        );
        userRepository.saveAll(userEntityConverter.toEntities(users));
    }

    private void createCategories() {
        var categories = List.of(
                Category.builder()
                        .name("??????")
                        .language(Language.ENGLISH)
                        //.user(userService.loadByUsername("user"))
                        .build(),
                Category.builder()
                        .name("??????????????")
                        .language(Language.ENGLISH)
                        //.user(userService.loadByUsername("user"))
                        .build(),
                Category.builder()
                        .name("??????????????????")
                        .language(Language.ENGLISH)
                        //.user(userService.loadByUsername("user"))
                        .build());

        categoryRepository.saveAll(categoryEntityConverter.toEntities(categories));

    }
}
