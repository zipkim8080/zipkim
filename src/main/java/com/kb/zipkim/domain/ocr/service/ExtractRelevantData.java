package com.kb.zipkim.domain.ocr.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ExtractRelevantData {

    public String extractRelevantData(String jsonResponse) {

        // 원본 JSON 파싱
        JSONObject originalJson = new JSONObject(jsonResponse);
        JSONArray images = originalJson.getJSONArray("images");

        // 새로운 JSON 구조
        JSONArray simplifiedImages = new JSONArray();

        for (int i = 0; i < images.length(); i++) {
            JSONObject image = images.getJSONObject(i);
            JSONArray tables = image.getJSONArray("tables");

            JSONArray simplifiedTables = new JSONArray();

            for (int j = 0; j < tables.length(); j++) {
                JSONObject table = tables.getJSONObject(j);
                JSONArray cells = table.getJSONArray("cells");

                JSONArray simplifiedCells = new JSONArray();

                for (int k = 0; k < cells.length(); k++) {
                    JSONObject cell = cells.getJSONObject(k);

                    // 필요한 정보만 추출하여 새로운 JSON 오브젝트 생성
                    JSONObject simplifiedCell = new JSONObject();
                    simplifiedCell.put("rowSpan", cell.getInt("rowSpan"));
                    simplifiedCell.put("columnSpan", cell.getInt("columnSpan"));
                    simplifiedCell.put("rowIndex", cell.getInt("rowIndex"));
                    simplifiedCell.put("columnIndex", cell.getInt("columnIndex"));

                    // inferText 추출 (cellTextLines에서 추출)
                    JSONArray cellTextLines = cell.getJSONArray("cellTextLines");
                    if (cellTextLines.length() > 0) {
                        JSONObject firstLine = cellTextLines.getJSONObject(0);
                        JSONArray cellWords = firstLine.getJSONArray("cellWords");
                        if (cellWords.length() > 0) {
                            JSONObject firstWord = cellWords.getJSONObject(0);
                            simplifiedCell.put("inferText", firstWord.getString("inferText"));
                        }
                    }

                    simplifiedCells.put(simplifiedCell); // 간소화된 셀 추가
                }

                // 테이블에 간소화된 셀 추가
                JSONObject simplifiedTable = new JSONObject();
                simplifiedTable.put("cells", simplifiedCells);
                simplifiedTables.put(simplifiedTable);
            }

            // 이미지에 간소화된 테이블 추가
            JSONObject simplifiedImage = new JSONObject();
            simplifiedImage.put("tables", simplifiedTables);
            simplifiedImages.put(simplifiedImage);
        }

        // 최종 간소화된 JSON 객체 반환
        JSONObject simplifiedResult = new JSONObject();
        simplifiedResult.put("images", simplifiedImages);

        return simplifiedResult.toString(2); // 들여쓰기로 보기 쉽게 출력
    }
}
