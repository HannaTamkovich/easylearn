package com.easylearn.easylearn.mock;

import com.easylearn.easylearn.category.model.Category;
import com.easylearn.easylearn.category.repository.CategoryRepository;
import com.easylearn.easylearn.category.repository.converter.CategoryEntityConverter;
import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.security.user.model.UserAccount;
import com.easylearn.easylearn.security.user.repository.UserAccountRepository;
import com.easylearn.easylearn.security.user.repository.converter.UserAccountEntityConverter;
import com.easylearn.easylearn.security.user.service.UserAccountService;
import com.easylearn.easylearn.word.model.Word;
import com.easylearn.easylearn.word.repository.WordRepository;
import com.easylearn.easylearn.word.repository.WordToUserRepository;
import com.easylearn.easylearn.word.repository.converter.WordEntityConverter;
import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Transactional
@Component
@RequiredArgsConstructor
@Profile("local")
@Slf4j
public class MockDataCreator {

    private final CategoryRepository categoryRepository;
    private final UserAccountRepository userAccountRepository;

    private final UserAccountService userAccountService;

    private final CategoryEntityConverter categoryEntityConverter;
    private final UserAccountEntityConverter userAccountEntityConverter;

    private final WordRepository wordRepository;
    private final WordEntityConverter wordEntityConverter;
    private final WordToUserRepository wordToUserRepository;

    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void createMockData() {
        log.info("Create mock data for local env");

        createUsers();
        //createCategories();
        createWords();
    }

    private void createWords() {
        var words = List.of(
                Word.builder()
                        .word("Dog")
                        .translation("Собака")
                        .language(Language.ENGLISH)
                        .build(),
                Word.builder()
                        .word("Cat")
                        .translation("Кот")
                        .language(Language.ENGLISH)
                        .build()
        );
        wordRepository.saveAll(wordEntityConverter.toEntities(words));

        var wordToUsers = List.of(
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
        wordToUserRepository.saveAll(wordToUsers);
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
                UserAccount.builder()
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
        userAccountRepository.saveAll(userAccountEntityConverter.toEntities(users));
    }

    private void createCategories() {
        var categories = List.of(
                Category.builder()
                        .name("Дом")
                        .language(Language.ENGLISH)
                        .userAccount(userAccountService.loadByUsername("admin"))
                        .build(),
                Category.builder()
                        .name("Магазин")
                        .language(Language.SPANISH)
                        .userAccount(userAccountService.loadByUsername("spanish_a"))
                        .build(),
                Category.builder()
                        .name("Транспорт")
                        .language(Language.GERMAN)
                        .userAccount(userAccountService.loadByUsername("german_a"))
                        .build());

        categoryRepository.saveAll(categoryEntityConverter.toEntities(categories));

    }
}
