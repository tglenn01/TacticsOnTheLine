package main.persistance;

import main.model.characterSystem.CharacterUnit;
import main.model.characterSystem.StatSheet;
import main.ui.TacticBaseBattle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class SetSaveData {

    public SetSaveData(String account) throws IOException {
        JSONObject jsonCharacterList = new JSONObject();

        for (CharacterUnit unit : TacticBaseBattle.getInstance().getPartyMemberList()) {
            JSONObject unitObject = new JSONObject();
            String unitName = unit.getCharacterName();
            String unitJob = unit.getCharacterJob().getJobTitle();
            int totalExperience = (unit.getLevel() * 100) + unit.getExperiencePoints().getCurrentExperience();
            JSONArray maxStatList = new JSONArray();
            StatSheet unitStatSheet = unit.getCharacterStatSheet();
            maxStatList.add(unitStatSheet.getMaxHealth());
            maxStatList.add(unitStatSheet.getMaxMana());
            maxStatList.add(unitStatSheet.getBaseStrength());
            maxStatList.add(unitStatSheet.getBaseMagic());
            maxStatList.add(unitStatSheet.getBaseArmour());
            maxStatList.add(unitStatSheet.getBaseResistance());
            maxStatList.add(unitStatSheet.getBaseSpeed());
            maxStatList.add(unitStatSheet.getBaseDexterity());
            maxStatList.add(unitStatSheet.getMovement());

            unitObject.put("unitJob", unitJob);
            unitObject.put("totalExperience", totalExperience);
            unitObject.put("maxStatList", maxStatList);
            jsonCharacterList.put(unitName, unitObject);
        }


        try (FileWriter file = new FileWriter(account)) {
            file.write(jsonCharacterList.toJSONString());
        }
    }
}
