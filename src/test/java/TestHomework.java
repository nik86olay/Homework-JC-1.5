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

        final var actual = new StringBuilder();
        final var expected = new StringBuilder();
        final var actual1 = Main.fileName;
        final var expected1 = ".csv";

        // when:
        for (var string : Main.columnMapping) {
            actual.append(string);
        }

        for (var field : employee.getClass().getDeclaredFields()) {
            expected.append(field.getName());
        }

        // then:
        // Проверка соответствия значений массива наименованию полей класса
        assertThat(actual.toString(), equalTo(expected.toString()));
        // Проверка наличия наименования файла
        assertThat(actual1, is(not(isEmptyOrNullString())));
        assertThat(actual1, containsString(expected1));

        // выражение переписано в стиле Hamcrest
//        Assertions.assertEquals(origin.toString(), expected.toString());
//        Assertions.assertNotNull(origin1);
    }

    @Test
    @DisplayName("Тест на успешное считывание информации из CSV в объекты класса Employee")
    public void success_Create_ListEmployee() throws NoSuchFieldException, IllegalAccessException {
        // given:
        final var arrayFields = Main.columnMapping;
        final var fileName = Main.fileName;
        var stringBuilder = new StringBuilder();
        final String actual;
        final String expected;

        // when:
        Main.createDataCsv(fileName);
        List<Employee> employeeList = Main.parseCSV(arrayFields, fileName);
        // Считываем инф. с файла и записываем в строку
        readFile(fileName, stringBuilder);
        expected = stringBuilder.toString().replaceAll("\"", "").replaceAll(",",  "");
        // Считываем инф. о значениях полей класса и записываем в строку
        stringBuilder = new StringBuilder();
        for (Employee employee : employeeList) {
            for (String field : arrayFields) {
                stringBuilder.append(employee.getClass().getField(field).get(employee));
            }
        }
        actual = stringBuilder.toString();

        // then:
        assertThat(employeeList, is(not(empty())));
        // Проверка равенства содержимого файла fileName с полученными значениями полей объектов класса Employee
        assertThat(expected, equalTo(actual));

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
        readFile(fileName, stringBuilder);
        origin = stringBuilder.toString().replaceAll("\"", "");

        // then:
        Assertions.assertTrue(testFile.exists());
        Assertions.assertTrue(testFile.canRead());
        assertThat(extend, equalToIgnoringWhiteSpace(origin));

    }

    private void readFile(String fileName, StringBuilder stringBuilder) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String s;
            while ((s = bufferedReader.readLine()) != null){
                    stringBuilder.append(s);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
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
