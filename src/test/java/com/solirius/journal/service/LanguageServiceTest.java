//package com.solirius.journal.service;
//
//import com.solirius.journal.Service.LanguageService;
//import com.solirius.journal.model.Language;
//import com.solirius.journal.repository.LanguageRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static junit.framework.TestCase.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//@RunWith(SpringRunner.class)
//public class LanguageServiceTest {
//
//    @Mock
//    private LanguageRepository languageRepository;
//
//    @InjectMocks
//    private LanguageService languageService;
//
//    private Language testLanguage;
//    private Language testLanguage2;
//    private List<Language> testLanguages;
//
//    @Before
//    public void setup(){
//        testLanguage = new Language();
//        testLanguage.setName("testLanguage");
//        testLanguage.setLanguageId(1);
//
//        testLanguage2 = new Language();
//        testLanguage2.setName("testLanguage2");
//        testLanguage2.setLanguageId(2);
//
//        testLanguages = Arrays.asList(testLanguage,testLanguage2);
//
//        given(languageRepository.findAllByOrderByLanguageIdAsc()).willReturn(testLanguages);
//        given(languageRepository.findByName(anyString())).willReturn(Optional.ofNullable(testLanguage));
//        given(languageRepository.findById(anyInt())).willReturn(Optional.ofNullable(testLanguage));
//        given(languageRepository.save(any(Language.class))).willReturn(testLanguage);
//        given(languageRepository.findAllByNameOrderByLanguageIdAsc(anyString())).willReturn(testLanguages);
//    }
//
//    @Test
//    public void testGetLanguageByName(){
//        Optional<Language> get = languageService.getLanguage("foo");
//        assertEquals(testLanguage,get.get());
//    }
//
//    @Test
//    public void testGetLanguageById(){
//        Optional<Language> get = languageService.getLanguage(10);
//        assertEquals(testLanguage,get.get());
//    }
//
//    @Test
//    public void testGetAllLanguages(){
//        List<Language> get = languageService.getAllLanguages();
//        assertEquals(testLanguages,get);
//    }
//
//    @Test
//    public void testGetAllLanguagesByName(){
//        List<Language> getLanguagesByName = languageService.getAllLanguages("foo");
//        assertEquals(testLanguages,getLanguagesByName);
//    }
//
//    @Test
//    public void testCreateLanguage(){
//        Language createLanguage = languageService.createLanguage(testLanguage);
//        assertEquals(testLanguage,createLanguage);
//    }
//
//    @Test
//    public void testDestroyLanguage(){
//        Language del = languageService.destroyLanguage(testLanguage);
//        assertEquals(testLanguage,del);
//    }
//}
