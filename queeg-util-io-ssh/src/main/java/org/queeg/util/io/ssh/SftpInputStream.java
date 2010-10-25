package org.queeg.util.io.ssh;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

public class SftpInputStream extends InputStream {
  private InputStream stream;

  public SftpInputStream(String user, String password, String host, int port, String path) throws JSchException,
      SftpException {
    JSch jsch = new JSch();
    Session session = jsch.getSession(user, host);

    UserInfo ui = new MyUserInfo(null, password);
    session.setUserInfo(ui);

    session.connect();

    Channel channel = session.openChannel("sftp");
    channel.connect();
    ChannelSftp c = (ChannelSftp) channel;

    stream = c.get(path);
  }

  @Override
  public int available() throws IOException {
    return stream.available();
  }

  @Override
  public void close() throws IOException {
    stream.close();
  }

  @Override
  public synchronized void mark(int readlimit) {
    stream.mark(readlimit);
  }

  @Override
  public boolean markSupported() {
    return stream.markSupported();
  }

  @Override
  public int read() throws IOException {
    return stream.read();
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return stream.read(b, off, len);
  }

  @Override
  public int read(byte[] b) throws IOException {
    return stream.read(b);
  }

  @Override
  public synchronized void reset() throws IOException {
    stream.reset();
  }

  @Override
  public long skip(long n) throws IOException {
    return stream.skip(n);
  }

  public static class MyUserInfo implements UserInfo {
    private String passphrase;
    private String password;

    public MyUserInfo(String passphrase, String password) {
      this.passphrase = passphrase;
      this.password = password;
    }

    @Override
    public String getPassphrase() {
      return passphrase;
    }

    @Override
    public String getPassword() {
      return password;
    }

    @Override
    public boolean promptPassphrase(String arg0) {
      return passphrase != null;
    }

    @Override
    public boolean promptPassword(String arg0) {
      return password != null;
    }

    @Override
    public boolean promptYesNo(String arg0) {
      return true;
    }

    @Override
    public void showMessage(String arg0) {
    }
  }
}
