package com.unit.testing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

// DIFFERENT PACKAGE

@DisplayName("My test")
class SomeFancyServiceTest {

    private final SomeFancyService service = new SomeFancyService();

    // <editor-fold desc="Before & After">
    // NOT public ANYMORE
    @BeforeAll
    static void beforeAll() {
        // "New name is more explicit"
        System.out.println("In beforeAll");
    }

    @AfterAll
    static void afterAll() {
        // "New name is more explicit"
        System.out.println("In afterAll");
    }

    @BeforeEach
    void beforeEach() {
        // "New name is more explicit"
        System.out.println("In beforeEach");
    }

    @AfterEach
    void afterEach() {
        // "New name is more explicit"
        System.out.println("In afterEach");
    }
    // </editor-fold>

    @Nested
    class Assertions {

        @Test
        void positiveScenario() {
            final int a = 4, b = 8;
//            final int res = 12;
        final int res = 6;
            assertEquals(service.sum(a, b), res, () -> {
                String m = "Hardly constructed message: ";
                m += a + " plus " + b + " is not " + res;
                return m;
            });
        }

        @Test
        void multiplePositiveScenarios() {
            assertAll("Positive cases",
                    () -> assertEquals(service.divide(4, 2), 2),
                    () -> assertEquals(service.divide(10, 2), 5),
                    () -> assertEquals(service.divide(1, 1), 1)
            );
        }

        @Test
        void multiplePositiveScenariosAndOneNegative() {
            assertAll("Positive cases and one negative",
                    () -> assertEquals(service.divide(4, 2), 2),
                    () -> assertEquals(service.divide(10, 2), 5),
                    () -> assertEquals(service.divide(10, 2), 3),
                    () -> assertEquals(service.divide(1, 1), 1)
            );
        }

        @Test
        void testLinesMatch() {
            List<String> expectedLines = Collections.singletonList("(.*)@(.*)");
            List<String> emails = Collections.singletonList("ruslan@smaato.com");
            assertLinesMatch(expectedLines, emails);
        }

        @Test
        void testIterableEquals() {
            List<String> developers = Arrays.asList("Ruslan", "Gerd");
            Set<String> allDevelopers = service.getAllDevelopers();

            assertIterableEquals(developers, allDevelopers);
        }
    }

    @Nested
    class Exceptions {
        @Test
        void exceptionTesting() {
            assertDoesNotThrow(() -> service.divide(-1, -2));
            assertThrows(ArithmeticException.class,
                    () -> service.divide(10, 0));
        }

        @Test
        void exceptionTestingExtended() {
            Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(exception.getMessage(), "Not supported");
        }

//    @Test(timeout = 1)
//    public void shouldFailBecauseTimeout() throws InterruptedException {
//        Thread.sleep(10);
//    }

        @Test
        void testFailsBecauseTimeout() {
            assertTimeout(Duration.ofMillis(100), () -> Thread.sleep(150));
        }

        @Test
        void testDoesNotFailBecauseTimeout() {
            assertTimeout(Duration.ofMillis(100), () -> Thread.sleep(50));
        }
    }

    @Nested
    @DisplayName("My first nested class name")
    class Sugaring {
        @Test
        @DisplayName("Easy readable space separated name")
        void testWithIncrediblyLongAndUnreadableName() {
            System.out.println("I'm running with such a beautiful name!");
        }

        @Test
        @Disabled("Just to show that I'm ignored") // <-- see how many there are 
        void skippedTest() {
            System.out.println("I'm not running!");
        }

        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void sometimesSkippedTest18() {
            System.out.println("I'm running for Java 8 solely!");
        }

        @Test
        @EnabledOnJre(JRE.OTHER)
        void sometimesSkippedTest17() {
            System.out.println("I'm running for Java 7 and below!");
        }
    }

    // <editor-fold desc="Tagging">
    @Tag("kafka")
    @Test
    void kafkaTest() {
        System.out.println("Kafka test");
    }

    @Tag("kinesis")
    @Test
    void kinesisTest() {
        System.out.println("Kinesis test");
    }
    // </editor-fold>

    @Nested
    static class RepeatedAndMore {
        private static int counter = 0;

        @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
        void multipleRunningTest() {
            assertTrue(Boolean.TRUE);
            System.out.println("multipleRunningTest is running for the " + ++counter + " time");
        }

        // JUnit 5 now allows defining parameters for test constructors and methods and enables dependency injection for them.
        // Currently, there are only 3 built-in resolvers for parameters of type TestInfo, RepetitionInfo and TestReporter.
        @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
        void multipleRunningTestVer2(RepetitionInfo info) {
            assertTrue(Boolean.TRUE);
            System.out.println("multipleRunningTestVer2 is running for the " + info.getCurrentRepetition() + " time");
        }

        @Test
        @DisplayName("some name")
        void exploringTestInfo(TestInfo info) {
            System.out.println("DisplayName `" + info.getDisplayName() + "` TestMethod `" + info.getTestMethod().get() + "`");
        }
    }

    @Nested
    class Parametrized {
        @ParameterizedTest
        @ValueSource(strings = {"panda", "koala"})
            // other values ints(), longs(), doubles() etc.
        void testParametrized(String animal) {
            assertNotNull(animal);
        }

        @ParameterizedTest
        @EnumSource(value = SomeFancyService.Game.class)
        void testParametrizedWithEnum(SomeFancyService.Game game) {
            final EnumSet<SomeFancyService.Game> allGames = service.getAllGames();
            assertTrue(allGames.contains(game));
        }

    }

//    In order to fully migrate from JUnit 4 to the JUnit 5 API – here are some of the changes we will most likely encounter:
//
//    change everything from org.junit to the org.junit.jupiter.api package
//    replace @BeforeClass and @AfterClass with @BeforeAll and @AfterAll
//    replace @Before and @After with @BeforeEach and @AfterEach
//    replace @Ignore with @Disabled
//    remove @Rule, @ClassRule and @RunWith
}