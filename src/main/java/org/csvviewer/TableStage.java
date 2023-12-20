package org.csvviewer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TableStage {
    private final TableView<List<String>> tableView = new TableView<>();
    private final List<List<String>> records = new ArrayList<>();
    private int maxColumnsNumber = 0;
    private final boolean hasHeader;
    private final String fileName;

    public TableStage(File CSVFile, char delimiter, boolean hasHeader) {
        fileName = CSVFile.getName();
        this.hasHeader = hasHeader;
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(CSVFile.toURI()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addRecordsAndSetMaxColumnsNumber(lines, delimiter);
    }

    public Stage init() {
        Stage tableStage = new Stage();
        tableStage.setTitle(fileName);
        Scene tableScene = new Scene(tableView);
        tableStage.setScene(tableScene);
        createTableColumns((hasHeader ? records.remove(0) : records.get(0)));
        tableView.setItems(FXCollections.observableList(records));
        return tableStage;
    }

    private void addRecordsAndSetMaxColumnsNumber(List<String> lines, char delimiter) {
        for (String line : lines) {
            List<String> record = new CSVParser(line, delimiter).parse();
            maxColumnsNumber = Integer.max(maxColumnsNumber, record.size());
            records.add(record);
        }
    }

    private void createTableColumns(List<String> firstRow) {
        for (int i = 0; i < maxColumnsNumber; i++) {
            TableColumn<List<String>, String> tableColumn;
            if (i < firstRow.size()) {
                tableColumn = new TableColumn<>(firstRow.get(i));
            } else {
                tableColumn = new TableColumn<>("");
            }
            tableView.getColumns().add(tableColumn);
            int columnIndex = i;
            tableColumn.setCellValueFactory(cdf -> {
                List<String> record = cdf.getValue();
                return columnIndex >= record.size() ? new SimpleStringProperty("") : new SimpleStringProperty(record.get(columnIndex));
            });
        }
        if (!hasHeader) {
            tableView.getStylesheets().addAll(getClass().getResource("table-with-no-header.css").toExternalForm());
        }
    }
}

