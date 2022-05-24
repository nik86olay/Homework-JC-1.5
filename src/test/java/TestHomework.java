import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestHomework {

    @Test
    @DisplayName("Тест проверки входных данных метода parseCSV()")
    public void test_validParam_method_parseCSV() {
        // given:
        Employee employee = new Employee();

        final var origin = new StringBuilder();
        final var expected = new StringBuilder();
        final var origin1 = Main.fileName;

        // when:
        for (var string : Main.columnMapping) {
            origin.append(string);
        }

        for (var field : employee.getClass().getDeclaredFields()) {
            expected.append(field.getName());
        }

        // then:
        assertThat(Main.columnMapping, not(emptyArray()));
        assertThat(employee.getClass().getDeclaredFields(), not(emptyArray()));
        assertThat(origin.toString(), equalTo(expected.toString()));
        assertThat(origin1, notNullValue());

        // выражение переписано в стиле Hamcrest
//        Assertions.assertNotNull(origin);
//        Assertions.assertNotNull(expected);
//        Assertions.assertEquals(origin.toString(), expected.toString());
//        Assertions.assertNotNull(origin1);
    }

    @Test
    @DisplayName("Тест на успешное считывание информации из CSV в объекты класса Employee")
    public void success_Create_ListEmployee() {
        // given:
        final String[] origin = Main.columnMapping;
        final String fileName = Main.fileName;

        // when:
        Main.createDataCsv(fileName);
        List<Employee> employeeList = Main.parseCSV(origin, fileName);

        // then:
        assertThat(employeeList, is(not(empty())));

        // выражение переписано в стиле Hamcrest
//        Assertions.assertNotNull(employeeList);
    }

    @Test
    @DisplayName("Тест на успешное преобразование из объекта в строку формата Gson ")
    public void success_Create_String(){
        // given:
        Employee employee = new Employee(25, "Denis", "Kozlov", "RUS", 16);
        String employeeString = "\"id\":25,\"firstName\":\"Denis\",\"lastName\":\"Kozlov\",\"country\":\"RUS\",\"age\":16";
        List<Employee> list = new ArrayList<>();
        list.add(employee);

        // when:
        var valueStr = Main.listToJson(list);

        // then:
        assertThat(valueStr, is(not(isEmptyOrNullString())));
        assertThat(valueStr, containsString(employeeString));

        // выражение переписано в стиле Hamcrest
//        Assertions.assertNotNull(valueStr);
//        Assertions.assertTrue(valueStr.contains(employeeString));


    }

    @Test
    @DisplayName("Тест на успешное создание файла CSV")
    public void success_Create_FileCSV(){
        // given:
        var fileName = "C:\\Users\\Nikolay\\IdeaProjects\\Homework-JC-1.5\\src\\test\\java\\TestFile.txt";
        File testFile = new File(fileName);
        try {
            if(testFile.createNewFile()) System.out.println("File created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String extend = "1,John,Smith,USA,252,Ivan,Petrov,RU,23";
        String origin;
        StringBuilder stringBuilder = new StringBuilder();

        // when:
        Main.createDataCsv(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String s;
            while ((s = bufferedReader.readLine()) != null){
                    stringBuilder.append(s);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        origin = stringBuilder.toString().replaceAll("\"", "");

        // then:
        Assertions.assertTrue(testFile.exists());
        Assertions.assertTrue(testFile.canRead());
        assertThat(extend, equalToIgnoringWhiteSpace(origin));

    }

    @Test
    @DisplayName("Тест на успешное создание файла Gson")
    public void success_Create_FileGson(){
        // given:
        var fileName = "C:\\Users\\Nikolay\\IdeaProjects\\Homework-JC-1.5\\src\\test\\java\\TestFileGson.txt";
        File testFile = new File(fileName);
        try {
            if(testFile.createNewFile()) System.out.println("File created");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // when:
        Main.createDataCsv(fileName);

                // then:
        Assertions.assertTrue(testFile.exists());
        Assertions.assertTrue(testFile.canRead());

    }

}
