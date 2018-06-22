package com.example.apple.cinematracker.Pojo;
import java.util.List;

public class Poster {
    String title;
    String imgUrl;
    List<String> time;

    public Poster(String title,String imgUrl,List<String> time){
        this.title = title;
        this.imgUrl = imgUrl;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<String> getTime() {
        return time;
    }

    @Override
    public String toString(){
        StringBuilder answer = new StringBuilder();

        answer.append("Title: " + title + "\n");
        answer.append("Img: " + imgUrl + "\n");
        answer.append("Time: " + "\n");

        for (String hour : time)
            answer.append("\t" + hour + "\n");


        return answer.toString();
    }
}

