package dao;

import model.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import service.CheckParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvCheckDao implements CheckDao {

    private final Resource resource;
    private final CheckParser<String> parser;

    @Override
    public List<Check> getAll() {
        List<String> checksAsStrings = getChecksFromResourceAsStrings();
        List<Check> checks = parser.parseChecksFrom(checksAsStrings);
        return checks;
    }

    private List<String> getChecksFromResourceAsStrings() {

        var checksAsString = new ArrayList<String>();
        try (var in = resource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                checksAsString.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return checksAsString;
    }
}
