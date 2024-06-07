package net.kunmc.lab.app.util.csv;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class CSVWriter {

    private PrintWriter pw;
    private List<String> headers;
    private List<List<String>> rows;

    public CSVWriter(JavaPlugin plugin, String fileName)
            throws FileNotFoundException {
        this.cratePrintWriter(plugin, fileName);
    }

    public CSVWriter(JavaPlugin plugin, String fileName, List<String> headers)
            throws FileNotFoundException {
        this.headers = headers;
        this.cratePrintWriter(plugin, fileName);
    }

    private void cratePrintWriter(JavaPlugin plugin, String fileName) throws FileNotFoundException {
        // 出力ファイルの作成
        this.pw = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream("plugins/" + plugin.getDataFolder() + "/" + fileName + ".csv"),
                                StandardCharsets.UTF_8)));
    }

    public CSVWriter addRow(List<String> row) {
        this.rows.add(row);
        return this;
    }

    public void write() {
        // ヘッダー書き込み
        if (this.headers != null) {
            for (String header : this.headers) {
                this.pw.print(header);
                this.pw.print(",");
            }
            this.pw.println();
        }

        // データ書き込み
        for (List<String> row : this.rows) {
            for (String data : row) {
                this.pw.print(data);
                this.pw.print(",");
                this.pw.println();
            }
        }
        // ファイルを閉じる
        pw.close();
    }
}
