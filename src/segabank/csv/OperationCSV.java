package segabank.csv;

import segabank.bo.Operation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OperationCSV implements ICSV<List<Operation>> {
    private static String FOLDER_OUT = "resources/";


    private static String dateToString() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
        StringBuilder sb = new StringBuilder(dateFormat.format(date));
        sb.append(".csv");
        return sb.toString();
    }

    @Override
    public String toCSV(List<Operation> operations) {
        StringBuilder sb = new StringBuilder();
        sb.append("Id");
        sb.append(",").append("Montant");
        sb.append(",").append("Date");
        sb.append(",").append("Type");
        sb.append(",").append("Compte");
        sb.append(System.lineSeparator());

        String top = sb.toString();

        String fileName = FOLDER_OUT + dateToString();

        Path outPath = Paths.get(fileName);

        try (BufferedWriter bw = new BufferedWriter(Files.newBufferedWriter(outPath))) {
            bw.write(top);
            bw.newLine();
            for (Operation operation : operations) {
                addLines(operation, bw);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        String absolutePath = FileSystems.getDefault().getPath(fileName).normalize().toAbsolutePath().toString();
        return absolutePath;
    }

    private void addLines(Operation operation, BufferedWriter bw) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(operation.getId()).append(",");
        sb.append(operation.getMontant()).append(",");
        sb.append(operation.getDate()).append(",");
        sb.append(operation.getTypeOperation().getLabel()).append(",");
        sb.append(operation.getCompte().getId());
        bw.write(sb.toString());
    }
}
