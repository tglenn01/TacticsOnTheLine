package main.persistance;

import main.model.characterSystem.CharacterUnit;
import main.ui.TacticBaseBattle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ReadSaveData {

    public ReadSaveData(String account) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(account)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            for (CharacterUnit partyMember : TacticBaseBattle.getInstance().getPartyMemberList()) {
                JSONObject savedUnit = (JSONObject) jsonObject.get(partyMember.getCharacterName());
                JSONArray jsonArray = (JSONArray) savedUnit.get("maxStatList");
                String unitJobName = (String) savedUnit.get("unitJob");
                Long savedExperience = (Long) savedUnit.get("totalExperience");
                Integer totalExperience = savedExperience.intValue();
                partyMember.setDataFromSaveData(unitJobName, totalExperience, jsonArray);
            }

        }
    }
}
