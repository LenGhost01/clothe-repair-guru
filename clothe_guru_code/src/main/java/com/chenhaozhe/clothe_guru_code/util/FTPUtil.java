package com.chenhaozhe.clothe_guru_code.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FTPUtil {
    public static boolean uploadFile(String server, int port, String user, String password,
                                     String remoteFilePath, InputStream fileStream) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            boolean login = ftpClient.login(user, password);
            if (!login) {
                ftpClient.disconnect();
                return false;
            }

            // 设置ftp的控制编码为UTF-8格式
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            boolean success = ftpClient.storeFile(remoteFilePath, fileStream);
            fileStream.close();
            ftpClient.logout();
            return success;
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }
}
