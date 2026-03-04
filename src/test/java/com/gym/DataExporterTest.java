package com.gym;

import com.gym.io.DataExporter;
import com.gym.model.Visitor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataExporterTest {
    private final DataExporter exporter = new DataExporter();

    @Test
    void exportVisitors_sorted_writesCorrectJson() throws IOException {
        Visitor v1 = new Visitor(1, "Дмитро", "Ставицький", "0995555555");
        Visitor v2 = new Visitor(2, "Артем", "Тішин", "0996666666");
        v1.addVisit(LocalDate.of(2026, 1, 10));

        StringWriter sw = new StringWriter();
        exporter.exportVisitors(List.of(v1, v2),
                Comparator.comparing(Visitor::getLastName), sw);

        String json = sw.toString();
        int posStav = json.indexOf("Ставицький");
        int posTish = json.indexOf("Тішин");
        assertTrue(posStav < posTish, "Visitors should be sorted by last name");
        assertTrue(json.contains("2026-01-10"));
    }


    static class DelegatingWriter extends Writer {
        private final StringWriter delegate = new StringWriter();
        boolean writeCalled = false;
        boolean flushCalled = false;

        @Override
        public void write(char[] cbuf, int off, int len) {
            writeCalled = true;
            delegate.write(cbuf, off, len);
        }

        @Override
        public void write(String str) throws IOException {
            writeCalled = true;
            delegate.write(str);
        }

        @Override
        public void flush() {
            flushCalled = true;
            delegate.flush();
        }

        @Override
        public void close() {
            // no-op
        }

        public String getOutput() {
            return delegate.toString();
        }
    }

    @Test
    void exportVisitors_callsWriterWriteAndFlush() throws IOException {
        DelegatingWriter dw = new DelegatingWriter();
        Visitor v = new Visitor(1, "Тест", "Тест", "0500000000");

        exporter.exportVisitors(List.of(v),
                Comparator.comparing(Visitor::getLastName), dw);

        assertTrue(dw.writeCalled, "write() should have been called");
        assertTrue(dw.flushCalled, "flush() should have been called");
        assertTrue(dw.getOutput().contains("Тест"));
    }
}
