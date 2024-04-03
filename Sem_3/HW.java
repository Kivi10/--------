package Sem_3;
// Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Фамилия Имя Отчество дата _ рождения номер _ телефона пол

// Форматы данных:

// фамилия, имя, отчество - строки
// дата _ рождения - строка формата dd.mm.yyyy
// номер _ телефона - целое беззнаковое число без форматирования
// пол - символ латиницей f или m.

// Приложение должно проверить введенные данные по количеству. Если количество не совпадает, вернуть код ошибки, 
// обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

// Приложение должно распарсить полученную строку и выделить из них требуемые значения. Если форматы данных не совпадают, 
// нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. 
// Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

// Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны 
// записаться полученные данные, вида
// <Фамилия> <Имя> <Отчество> <дата _ рождения> <номер _ телефона> <пол>

// Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
// Не забудьте закрыть соединение с файлом.
// При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен 
// увидеть стектрейс ошибки.

public class HW {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        System.out.println(
                "Введите следующие данные, разделенные пробелом: ФИО дата _ рождения(dd.mm.yyyy) номер _ телефона пол (m/f). На Английском!: ");
        String info = scr.nextLine();
        scr.close();
        String[] inputData = info.split(" ");
        String[] data = new String[6];

        for (String item : inputData) {
            if (item.contains(".")) {
                data[3] = item; // дата_рождения
            } else if (item.matches("\\d+")) {
                data[4] = item; // телефон
            } else if (item.equals("f") || item.equals("m")) {
                data[5] = item; // пол
            } else if (data[0] == null) {
                data[0] = item; // фамилия
            } else if (data[1] == null) {
                data[1] = item; // имя
            } else if (data[2] == null) {
                data[2] = item; // отчество
            } else {
                throw new RuntimeException("Данные введены некорректно! Повторите.");
            }
        }
        if (data[0] == null || data[1] == null || data[3] == null || data[4] == null || data[5] == null) {
            throw new RuntimeException("Недостаточно данных! Повторите. ");
        }
        String surname = data[0];
        String name = data[1];
        String patronymic = data[2];
        String dateOfBirthStr = data[3];
        String phoneNumberStr = data[4];
        String genderStr = data[5];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            dateFormat.parse(dateOfBirthStr);
        } catch (ParseException e) {
            throw new RuntimeException("Ошибка: Неверный формат даты рождения. Используйте формат dd.mm.yyyy");
        }

        try {
            Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Ошибка: Номер телефона должен быть целым числом.");
        }
        String output = surname + " " + name + " " + patronymic + " " + dateOfBirthStr + " " + phoneNumberStr + " " + genderStr;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(surname + ".txt", true))) {
            writer.write(output);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
