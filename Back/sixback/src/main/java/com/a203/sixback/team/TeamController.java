package com.a203.sixback.team;

import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class TeamController {
    @Value("${API-KEY}")
    public String apiKey;
    // 전체 팀 정보 저장하기
    @GetMapping("/team")
    public void test() throws IOException, ParseException {
        JSONArray jsonArray = new JSONArray();
        String str = "https://apiv3.apifootball.com/?action=get_teams&league_id=152&APIkey=" + apiKey;
        URL url = new URL(str);
        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(),"UTF-8");
        jsonArray = (JSONArray) JSONValue.parseWithException(isr);
        for(int i=0;i< jsonArray.size();i++){
            JSONObject jsonObject = ((JSONObject) jsonArray.get(i));
            int teamId = Integer.parseInt(jsonObject.get("team_key").toString());
            String teamName = jsonObject.get("team_name").toString();
            String teamImage = jsonObject.get("team_badge").toString();
            JSONArray coaches = (JSONArray) jsonObject.get("coaches");
            String coachName= ((JSONObject)coaches.get(0)).get("coach_name").toString();
            System.out.println(teamId);
            System.out.println(teamName);
            System.out.println(teamImage);
            System.out.println(coachName);
        }
//        for(int i=0;i<jsonArray.size();i++){
//
//        }

    }

}
