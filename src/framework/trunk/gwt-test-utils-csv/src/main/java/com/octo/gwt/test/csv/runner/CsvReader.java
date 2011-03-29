package com.octo.gwt.test.csv.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {

  public static List<List<String>> readCsv(Reader reader) throws IOException {
    BufferedReader br = new BufferedReader(reader);
    List<List<String>> l = new ArrayList<List<String>>();
    while (true) {
      String line = br.readLine();
      if (line == null) {
        break;
      }
      line = new String(line.getBytes(), "UTF-8");
      if ("".equals(line)) {
        l.add(new ArrayList<String>());
      } else {
        String[] tab = line.split(";");
        l.add(Arrays.asList(tab));
      }
    }
    return l;
  }
}
