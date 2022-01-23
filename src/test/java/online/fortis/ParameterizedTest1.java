package online.fortis;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ParameterizedTest1 {

    @BeforeEach
    @DisplayName("Открываем Гисметео")
    void openPage() {
        open("https://www.gismeteo.ru/");
        $(".search-label").shouldBe(visible);
    }

    //задисейбленный тест
    @Test
    @Disabled
    void disabledTest(){
        System.out.println("Задисейбленный тест :-)");
    }

    //обычный тест с параметрами (testData)
    @ValueSource(strings = {"Ставрополь", "Нарьян-Мар", "Сочи"})
    @ParameterizedTest(name = "Обычный тест с параметром: {0}")
    void testWithParams(String testData) {
        $("[class=\"input js-input\"]").setValue(testData);
        $(".found a").click();
        $$(".breadcrumbs-links a").last();
    }

    //тест с параметрами CSV (testData и expectedResult)
    @CsvSource(value = {
            "Ставрополь, Ставропольский край",
            "Нарьян-Мар, Ненецкий автономный округ",
            "Сочи, Краснодарский край"
    })
    @ParameterizedTest(name = "тест с двумя параметрами {0} > {1}")
    void testWithCSVParams(String testData, String expectedResult) {
        $("[class=\"input js-input\"]").setValue(testData);
        $(".found a").click();
        $(".breadcrumbs-links").$("a").sibling(0).shouldHave(text(expectedResult));
    }


    //тест с параметрами MethodSource

    static Stream<Arguments> commonSearchTestDataProvider(){
        return Stream.of(
                Arguments.of("Москва", "Москва (город федерального значения)"),
                Arguments.of("Санкт-Петербург", "Санкт-Петербург (город федерального значения)")
        );
    }
    @MethodSource("commonSearchTestDataProvider")
    @ParameterizedTest(name = "тест с параметрами {0} > {1}")
        void testWithMethodSource(String testData, String expectedResult) {
            $("[class=\"input js-input\"]").setValue(testData);
            $(".found a").click();
            $(".breadcrumbs-links").$("a").sibling(0).shouldHave(text(expectedResult));
        }

}
