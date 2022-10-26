package main.persistance;

import main.model.battleSystem.TacticBaseBattle;
import main.model.characterSystem.CharacterUnit;
import main.model.scenarioSystem.Scenario;
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
        TacticBaseBattle tacticBaseBattle = TacticBaseBattle.getInstance();

        try (Reader reader = new FileReader(account)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            for (CharacterUnit partyMember : tacticBaseBattle.getPartyMemberList()) {
                JSONObject savedUnit = (JSONObject) jsonObject.get(partyMember.getCharacterName());
                JSONArray jsonArray = (JSONArray) savedUnit.get("maxStatList");
                String unitJobName = (String) savedUnit.get("unitJob");
                Long savedExperience = (Long) savedUnit.get("totalExperience");
                int totalExperience = savedExperience.intValue();
                partyMember.setDataFromSaveData(unitJobName, totalExperience, jsonArray);
            }

            for (Scenario scenario : tacticBaseBattle.getAvailableScenarios()) {
                JSONObject savedScenario = (JSONObject) jsonObject.get(scenario.getScenarioName());
                scenario.setCompleted((Boolean) savedScenario.get("isComplete"));
            }
            tacticBaseBattle.updateCompletedScenario();
        }
    }
}
