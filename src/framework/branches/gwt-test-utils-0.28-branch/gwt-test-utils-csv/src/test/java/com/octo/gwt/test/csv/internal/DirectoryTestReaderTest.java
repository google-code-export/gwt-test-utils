package com.octo.gwt.test.csv.internal;

import java.io.InputStreamReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.octo.gwt.test.csv.Rep1;
import com.octo.gwt.test.csv.Rep2;
import com.octo.gwt.test.csv.internal.DirectoryTestReader.CsvReader;

public class DirectoryTestReaderTest {

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

  @Test
  public void checkRep1() throws Exception {
    DirectoryTestReader reader = new DirectoryTestReader(Rep1.class);
    Assert.assertEquals(4, reader.getTestList().size());
    Assert.assertEquals(4, reader.getTestMethods().size());
    Assert.assertEquals(2, reader.getMacroFileList().size());
  }

  @Test
  public void checkRep2() throws Exception {
    DirectoryTestReader reader = new DirectoryTestReader(Rep2.class);
    Assert.assertEquals(1, reader.getTestList().size());
    Assert.assertEquals(1, reader.getTestMethods().size());
    // because we set the "pattern" attribute on @CsvMacros
    Assert.assertEquals(1, reader.getMacroFileList().size());
  }
}
