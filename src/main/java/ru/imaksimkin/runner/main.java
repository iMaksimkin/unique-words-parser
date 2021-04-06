package ru.imaksimkin.runner;

import lombok.SneakyThrows;
import ru.imaksimkin.clients.HttpBaseClient;
import ru.imaksimkin.clients.MySQLClient;
import ru.imaksimkin.utils.DataUtil;
import ru.imaksimkin.utils.DbUtil;

import java.util.Map;
import java.util.Scanner;

public class main {

    @SneakyThrows
    public static void main(String[] args) {

        DataUtil dataUtil = new DataUtil();
        System.out.println("Please write URL we need to get");
        Scanner scanner = new Scanner(System.in);

        String uRL = scanner.next();

        dataUtil.validateURL(uRL);

        HttpBaseClient client = new HttpBaseClient();
        MySQLClient mySQLClient = new MySQLClient();
        mySQLClient.connect();
        String html = client.getResponse(uRL);
        Map<String, String> wordsAndFrequencesList = dataUtil.deleteNonUniqueValuesFromString(html);
        new DbUtil().saveStatistic(wordsAndFrequencesList, uRL, mySQLClient);
    }
}

