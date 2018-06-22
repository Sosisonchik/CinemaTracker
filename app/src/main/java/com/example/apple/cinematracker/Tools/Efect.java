package com.example.apple.cinematracker.Tools;

/**
 * Клас парсит постери всех фильмов в прокате в сайта кинотеатра Ефкет(Черновцы)
 * И предоставляет интерфейс для получения их в виде колекции постеров.
 * */
import com.example.apple.cinematracker.Pojo.Poster;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Efect {
    private static final String EFECT_URL = "http://efekt.cv.ua";
    private static final String GET = "GET";

    private static final String CLASS = "class";
    private static final String TITLE_TAG = "poster-title";
    private static final String IMG_TAG = "youtube-link";
    private static final String TIME_TAG = "main-time";

    private static volatile Efect instance;

    private Efect(){}

    public static Efect getInstance(){
        if (instance==null){
            synchronized (Efect.class){
                if (instance==null)
                    instance = new Efect();
            }
        }

        return instance;
    }

    public List<Poster> parse() {
        List<Poster> posters = new ArrayList<Poster>();

        Document html = Jsoup.parse(getJson());

        Elements title_containers = html.getElementsByAttributeValue(CLASS,TITLE_TAG);
        Elements img_containers = html.getElementsByAttributeValue(CLASS,IMG_TAG);
        Elements time_containers = html.getElementsByAttributeValue(CLASS,TIME_TAG);

        List<String> titles = getTitles(title_containers);
        List<String> imgs = getImgs(img_containers);
        List<List<String>> timeList = getTime(time_containers);

        for (int i = 0; i < titles.size(); i++)
            posters.add(new Poster(titles.get(i),imgs.get(i),timeList.get(i)));


        return posters;
    }

    private String getJson() {
        StringBuilder json = new StringBuilder();

        URL url;
        HttpURLConnection connection = null;
        BufferedReader in = null;
        //Поддерживаем Api 17 поетому не юзаем try-with-resources
        try  {
            url = new URL(EFECT_URL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(GET);
            connection.setConnectTimeout(250);

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line=in.readLine()) != null) {
                json.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return json.toString();
    }


    private List<String> getTitles(Elements containers){
        List<String> titles = new ArrayList<String>();
        for (Element container : containers){
            titles.add(container.text());
        }

        return titles;
    }

    private List<String> getImgs(Elements containers){
        List<String> imgs = new ArrayList<String>();
        for (Element container : containers){
            Element imgTag = container.child(0);
            imgs.add(imgTag.attr("src"));
        }

        return imgs;
    }

    private List<List<String>> getTime(Elements containers){
        List<List<String>> timeList = new ArrayList<List<String>>();
        for (Element container : containers){

            Elements timeTags = container.children();
            List<String> time = new ArrayList<String>();

            for (Element timeTag : timeTags)
                time.add(timeTag.text());

            timeList.add(time);
        }

        return timeList;
    }

}

