package ru.imaksimkin.utils;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {

    private List<String> getWordsWithMoreThanTwoCharsAndMore(String stringWithWords) {
        List<String> words = new ArrayList<>();
        Pattern p = Pattern.compile("[\\p{L}+]{2,}");
        Matcher m = p.matcher(stringWithWords);
        while (m.find()) {
            words.add(m.group().toLowerCase());
        }
        System.out.println(words);
        return words;
    }

    public Map<String, String> deleteNonUniqueValuesFromString(String htmlPage) {
        saveArtifact(htmlPage);
        String stringWithWords = Jsoup.parse(htmlPage).text();
        Map<String, String> mapWithUniqueWords = new HashMap<>();
        List<String> listWords = getWordsWithMoreThanTwoCharsAndMore(stringWithWords);
        Set<String> uniqueWords = new HashSet<>(listWords);
        for (String word : uniqueWords) {
            mapWithUniqueWords.put(word, String.valueOf(Collections.frequency(listWords, word)));
        }
        return mapWithUniqueWords;
    }

    public boolean validateURL(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException ex) {
            System.out.println("The URL is not valid. Be more careful next time. " + ex);
            return false;
        }

        return true;
    }

    private void saveArtifact(String page) {
        String path = String.format("target/page%s.html", System.nanoTime());
        try {
            FileUtils.writeStringToFile(new File(path), page, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}