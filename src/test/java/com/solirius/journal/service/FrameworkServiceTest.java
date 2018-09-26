//package com.solirius.journal.service;
//
//import com.solirius.journal.Service.FrameworkService;
//import com.solirius.journal.model.Framework;
//import com.solirius.journal.model.Language;
//import com.solirius.journal.repository.FrameworkRepository;
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
//public class FrameworkServiceTest {
//
//    @Mock
//    private FrameworkRepository frameworkRepository;
//
//    @Mock
//    private LanguageRepository languageRepository;
//
//    @InjectMocks
//    private FrameworkService frameworkService;
//
//    private Language testLanguage;
//    private Framework testFramework;
//    private Framework testFramework2;
//    private List<Framework> testFrameworks;
//
//    @Before
//    public void setup(){
//        testLanguage = new Language();
//        testLanguage.setName("testLanguage");
//        testLanguage.setLanguageId(1);
//
//        testFramework = new Framework();
//        testFramework.setName("testFramework");
//        testFramework.setFrameworkId(1);
//
//        testFramework2 = new Framework();
//        testFramework2.setName("testFramework2");
//        testFramework2.setFrameworkId(2);
//
//        testFrameworks = Arrays.asList(testFramework,testFramework2);
//
//        given(frameworkRepository.findAllByOrderByFrameworkIdAsc()).willReturn(testFrameworks);
//        given(frameworkRepository.findByName(anyString())).willReturn(Optional.ofNullable(testFramework));
//        given(frameworkRepository.findById(anyInt())).willReturn(Optional.ofNullable(testFramework));
//        given(frameworkRepository.save(any(Framework.class))).willReturn(testFramework);
//        given(languageRepository.findByName(anyString())).willReturn(Optional.ofNullable(testLanguage));
//        given(frameworkRepository.findAllByLanguageOrderByFrameworkIdAsc(any(Language.class))).willReturn(testFrameworks);
//    }
//
//    @Test
//    public void testGetFrameworkByName(){
//        Optional<Framework> get = frameworkService.getFramework("foo");
//        assertEquals(testFramework,get.get());
//    }
//
//    @Test
//    public void testGetFrameworkById(){
//        Optional<Framework> get = frameworkService.getFramework(10);
//        assertEquals(testFramework,get.get());
//    }
//
//    @Test
//    public void testGetAllFrameworks(){
//        List<Framework> get = frameworkService.getAllFrameworks();
//        assertEquals(testFrameworks,get);
//    }
//
//    @Test
//    public void testGetAllFrameworksByLanguage(){
//        List<Framework> getFrameworksByLanguage = frameworkService.getAllFrameworks(testLanguage);
//        assertEquals(testFrameworks,getFrameworksByLanguage);
//    }
//
//    @Test
//    public void testCreateFramework(){
//        Framework createFramework = frameworkService.createFramework(testFramework);
//        assertEquals(testFramework,createFramework);
//    }
//
//    @Test
//    public void testDestroyFramework(){
//        Framework del = frameworkService.destroyFramework(testFramework);
//        assertEquals(testFramework,del);
//    }
//}
