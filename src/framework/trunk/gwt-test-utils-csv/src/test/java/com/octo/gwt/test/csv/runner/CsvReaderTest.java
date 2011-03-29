package com.octo.gwt.test.csv.runner;

import java.io.InputStreamReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CsvReaderTest {

  @Test
  public void checkCsvReader() throws Exception {
    InputStreamReader reader = new InputStreamReader(
        getClass().getResourceAsStream("/test.csv"));
    List<List<String>> l = CsvReader.readCsv(reader);
    Assert.assertNotNull(l);
    Assert.assertEquals(7, l.size());
    Assert.assertArrayEquals(new String[]{"toto"}, l.get(0).toArray());
    Assert.assertArrayEquals(new String[]{}, l.get(1).toArray());
    Assert.assertArrayEquals(new String[]{"toto1", "toto2"}, l.get(2).toArray());
    Assert.assertArrayEquals(new String[]{"toto3"}, l.get(3).toArray());
    Assert.assertArrayEquals(new String[]{"toto4", "toto 5", "toto6"},
        l.get(4).toArray());
    Assert.assertArrayEquals(new String[]{}, l.get(5).toArray());
    Assert.assertArrayEquals(new String[]{"", "toto7"}, l.get(6).toArray());
  }

}
