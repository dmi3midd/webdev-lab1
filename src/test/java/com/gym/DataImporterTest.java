package com.gym;

import com.gym.io.DataExporter;
import com.gym.io.DataImporter;
import com.gym.model.Visitor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataImporterTest {
    private final DataExporter exporter = new DataExporter();
    private final DataImporter importer = new DataImporter();

    @Test
    void importVisitors_validJson_returnsList() throws IOException {
        String json = """
                [
                  {
                    "id": 1,
                    "firstName": "Олександр",
                    "lastName": "Голота",
                    "phone": "0501234567",
                    "visitHistory": ["2026-01-10"]
                  }
                ]
                """;
        List<Visitor> visitors = importer.importVisitors(new StringReader(json));

        assertEquals(1, visitors.size());
        assertEquals("Голота", visitors.get(0).getLastName());
        assertEquals(1, visitors.get(0).getVisitHistory().size());
    }

    @Test
    void exportImport_roundTrip_preservesData() throws IOException {
        Visitor v1 = new Visitor(1, "Олександр", "Голота", "0501111111");
        Visitor v2 = new Visitor(2, "Михайло", "Ільїн", "0502222222");
        v1.addVisit(LocalDate.of(2026, 1, 5));
        v2.addVisit(LocalDate.of(2026, 2, 10));

        // Export
        StringWriter sw = new StringWriter();
        exporter.exportVisitors(List.of(v1, v2),
                Comparator.comparing(Visitor::getLastName), sw);

        // Import
        List<Visitor> imported = importer.importVisitors(new StringReader(sw.toString()));

        assertEquals(2, imported.size());
        assertEquals("Голота", imported.get(0).getLastName());
        assertEquals("Ільїн", imported.get(1).getLastName());
        assertEquals(LocalDate.of(2026, 1, 5), imported.get(0).getVisitHistory().get(0));
    }

    @Test
    void importVisitors_emptyJson_returnsEmptyList() throws IOException {
        List<Visitor> visitors = importer.importVisitors(new StringReader("[]"));
        assertTrue(visitors.isEmpty());
    }
}
