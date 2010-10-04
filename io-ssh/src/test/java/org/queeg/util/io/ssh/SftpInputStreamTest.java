package org.queeg.util.io.ssh;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;
import org.queeg.util.io.ssh.SftpInputStream;

public class SftpInputStreamTest {
  @Test
  public void integrationTest() throws Exception {
      SftpInputStream is = new SftpInputStream("noddy", "dumdum17", "localhost", 22, "/etc/passwd");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
      
      br.close();
  }
}
