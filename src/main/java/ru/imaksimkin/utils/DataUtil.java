package ru.imaksimkin.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class DataUtil {

    private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);
    public static String pathToFile;

    private List<String> getWordsWithMoreThanTwoCharsAndMore(String stringWithWords) {
        logger.trace("deleting words with length less than 3 characters");
        List<String> words = new ArrayList<>();
        Pattern p = Pattern.compile("[\\p{L}+]{2,}");
        Matcher m = p.matcher(stringWithWords);
        while (m.find()) {
            words.add(m.group().toLowerCase());
        }
        return words;
    }

    public Map<String, Integer> deleteNonUniqueValuesFromString(String htmlPage) {
        logger.trace("Deleting not unique words");
        saveArtifact(htmlPage);
        String stringWithWords = Jsoup.parse(htmlPage).text();
        Map<String, Integer> mapWithUniqueWords = new HashMap<>();
        List<String> listWords = getWordsWithMoreThanTwoCharsAndMore(stringWithWords);
        Set<String> uniqueWords = new HashSet<>(listWords);
        for (String word : uniqueWords) {
            mapWithUniqueWords.put(word, Collections.frequency(listWords, word));
        }
        return mapWithUniqueWords;
    }

    public boolean validateURL(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException ex) {
            logger.error("URL is not correct. \n" + ex.getMessage(), ex);
            System.out.println("The URL is not valid. Be more careful next time. " + ex.getMessage());
            return false;
        }
        return true;
    }

    private void saveArtifact(String page) {
        String pageName = "page" + System.nanoTime();
        pathToFile = String.format("target/%s", pageName);
        logger.trace("Saving " + pageName + " as html page into target folder");
        try {
            FileUtils.writeStringToFile(new File(pathToFile), page, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            logger.error("Error while saving html page. \n" + ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }
}