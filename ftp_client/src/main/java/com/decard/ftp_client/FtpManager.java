package com.decard.ftp_client;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;


/**
 * @author wudongdong
 * @file FtpManager.java
 * @brief FTP功能类
 * @email wudongdong@decard.com
 * @date 2018/4/24
 * @attention 查看ftp4j javadoc @link  {http://www.sauronsoftware.it/projects/ftp4j/api/index.html}
 */
public class FtpManager {

    private FTPClient ftpClient;

    public FtpManager() {
        ftpClient = new FTPClient();
    }

    /**
     * 连接
     *
     * @param host 主机ip
     * @param port 端口
     * @return true：成功；false:失败
     */
    public boolean connect(String host, int port) {

        assert (host != null);

        try {
            ftpClient.setCharset("UTF-8");
            ftpClient.connect(host, port);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }


        return false;

    }


    public boolean isConnect() {
        return ftpClient.isConnected();
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return true：成功；false:失败
     */
    public boolean login(String username, String password) {

        assert (username != null);
        assert (password != null);

        try {

            ftpClient.login(username, password);
            return true;
        } catch (IOException e) {
            Log.e("trace", "ftp login IOException--->" + e.getMessage());
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            Log.e("trace", "ftp login FTPIllegalReplyException--->" + e.getMessage());
            e.printStackTrace();
        } catch (FTPException e) {
            Log.e("trace", "ftp login FTPException--->" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取FTP服务器当前工作目录。
     *
     * @return 当前工作目录
     */
    public String currentDirectory() {
        try {

            return ftpClient.currentDirectory();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 切换FTP服务器当前工作目录。
     *
     * @param newPath 目标工作目录
     * @return true：成功；false:失败
     */
    public boolean changeDirectory(String newPath) {

        try {

            ftpClient.changeDirectory(newPath);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 切换到上一级目录。
     *
     * @return true：成功；false:失败
     */
    public boolean changeDirectoryUp() {

        try {

            ftpClient.changeDirectoryUp();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 重命名文件或目录（也可用路径实现移动文件或目录）
     *
     * @param oldPath 原名称
     * @param newPath 新名称
     * @return true：成功；false:失败
     */
    public boolean rename(String oldPath, String newPath) {

        try {

            ftpClient.rename(oldPath, newPath);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取指定文件的大小
     *
     * @param path 指定文件的路径
     * @return 文件大小，单位为byte
     */
    public long fileSize(String path) {
        try {

            return ftpClient.fileSize(path);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        return -1;
    }


    /**
     * 创建
     *
     * @param dir 指定文件的路径(相对路径，绝对路径均可)
     */
    public boolean create(String dir) {
        try {

            ftpClient.createDirectory(dir);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 删除指定文件
     *
     * @param path 指定文件的路径(相对路径，绝对路径均可)
     */
    public void deleteFile(String path) {
        try {

            ftpClient.deleteFile(path);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除指定目录
     *
     * @param dir 指定目录的路径(相对路径，绝对路径均可)
     * @attation 通常只删除空目录
     */
    public boolean deleteDirectory(String dir) {
        try {

            ftpClient.deleteDirectory(dir);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 列举当前工作目录下符合过滤条件的文件或文件夹
     *
     * @param fileSpec 过滤条件（如："*.jpg"）
     * @return FTPFile[]数组对象
     */
    public FTPFile[] list(String fileSpec) {

        assert (fileSpec != null);

        try {

            return ftpClient.list(fileSpec);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPAbortedException e) {
            e.printStackTrace();
        } catch (FTPListParseException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }

        return null;
    }


    /***
     * 列举当前工作目录下所有文件信息（文件名、文件大小、文件类型(文件或者文件夹)）
     * @return FTPFile[]数组对象
     */
    public FTPFile[] list() {

        return list("");

    }


    /**
     * 获取指定文件或目录最后修改日期
     *
     * @param path 指定文件的路径
     * @return 修改日期
     */
    public java.util.Date modifiedDate(String path) {

        try {

            return ftpClient.modifiedDate(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 下载
     *
     * @param remoteFileName 远程文件名称，
     * @param localFile      本地存储文件
     * @param restartAt      重新开始的位置（已下载字节大小）,没有下载过，或者重新下载传0，调用isResumeSupported()来检测看服务器支不支持断电续传。
     * @param listener       监听，不需要监听，传null
     * @attention 阻塞方法
     */
    public boolean download(String remoteFileName, File localFile, long restartAt, FTPDataTransferListener listener) {
        try {

            ftpClient.download(remoteFileName, localFile, restartAt, listener);
            return true;

        } catch (IOException e) {
            Log.e("trace","ftp download IOException: " + e.getMessage());
            //1.FTPConnection closed
            //2.ftp download IOException: sendto failed: EPIPE (Broken pipe)
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            Log.e("trace","ftp download FTPIllegalReplyException: " + e.getMessage());
            e.printStackTrace();
        } catch (FTPAbortedException e) {
            Log.e("trace","ftp download FTPAbortedException: " + e.getMessage());
            e.printStackTrace();
        } catch (FTPDataTransferException e) {
            Log.e("trace","ftp download FTPDataTransferException: " + e.getMessage());
            e.printStackTrace();
        } catch (FTPException e) {
            Log.e("trace","ftp download FTPException: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Log.e("trace","ftp download IllegalStateException: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean isAuthenticated() {
        return ftpClient.isAuthenticated();
    }


    /**
     * 取消当前数据传输（在另一个线程中调用）
     */
    public void abortCurrentDataTransfer() {

        try {

            ftpClient.abortCurrentDataTransfer(true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }


    /**
     * 尝试取消当前正在连接的操作（在另一个线程中调用）
     */
    public void abortCurrentConnectionAttempt() {

        try {

            ftpClient.abortCurrentConnectionAttempt();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传
     *
     * @param localFile 本地文件
     * @param restartAt 重新开始的位置（已下载字节大小）,没有上传过，或者重新上传，传0，调用isResumeSupported()来检测看服务器支不支持断电续传。
     * @param listener  监听，不需要监听，传null
     * @attention 阻塞方法
     */
    public boolean upload(File localFile, long restartAt, FTPDataTransferListener listener) {
        try {

            ftpClient.upload(localFile, restartAt, listener);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPAbortedException e) {
            e.printStackTrace();
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 列举当前工作目录下所有文件信息（文件名、文件大小、文件类型(文件或者文件夹)）
     *
     * @return FTPFile[]数组对象
     */
    public String[] listNames() {

        try {

            return ftpClient.listNames();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPAbortedException e) {
            e.printStackTrace();
        } catch (FTPListParseException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 注销，退出登录
     */
    public void logout() {
        try {

            ftpClient.logout();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }
    }


    /**
     * 断开连接
     */
    public boolean disconnect(boolean isSendQuitCmd) {
        try {
            boolean connected = ftpClient.isConnected();
            if (connected){
                ftpClient.disconnect(isSendQuitCmd);
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }
        return false;
    }

}
