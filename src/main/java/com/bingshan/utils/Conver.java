package com.bingshan.utils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

import static java.awt.SystemColor.info;

/**
 *
 *  <dependency>
 *             <groupId>commons-io</groupId>
 *             <artifactId>commons-io</artifactId>
 *             <version>2.11.0</version>
 *         </dependency>
 * @author bingshan
 * @date 2022/6/27 16:10
 */
public class Conver {
        private void process() {
            String srcFile = "E:\\utf8\\allbackupfile.sql";//gb2312编码
            String destFile = "E:\\utf8\\allbackupfile-utf8.sql";//UTF8编码
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;

            OutputStream os = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            try {
                is = new FileInputStream(srcFile);
                isr = new InputStreamReader(is, "Windows-1252");
                br = new BufferedReader(isr);
                os = new FileOutputStream(destFile);
                osw = new OutputStreamWriter(os, "UTF-8");
                bw = new BufferedWriter(osw);
                String line;
                byte[] bytes = new byte[1024];
                for (int len = is.read(bytes); len > 0; ) {
                    //line = UCS2ToUtf8(line);
                    System.out.println("line=" + bytes.toString());

                }
//                for (line = br.readLine(); line != null; line = br.readLine()) {
//                    //line = UCS2ToUtf8(line);
//                    System.out.println("line=" + line);
//
//                    bw.write(line + "\n");
//                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bw != null) {
                    try {
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (osw != null) {
                    try {
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        private static void process2() throws IOException {
            String srcFile = "E:\\utf8\\allbackupfile.sql";//gb2312编码
            String destFile = "E:\\utf8\\allbackupfile-utf8.sql";//UTF8编码
            List<String> list = IOUtils.readLines(new FileInputStream(srcFile), "UTF-16LE");  //UTF-16LE

            OutputStream os = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            os = new FileOutputStream(destFile);
            osw = new OutputStreamWriter(os, "UTF-8");
            bw = new BufferedWriter(osw);
            try {
                for(String s:list) {
                    String s1 = new String(s.getBytes("gbk"), "utf-8");
                    System.out.println(s1);
                    //bw.write(s1 + "\n");
                }

            } catch (IOException e) {
                    e.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (osw != null) {
                    try {
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

//
//    private static void process3() throws IOException {
//        String srcFile = "E:\\utf8\\allbackupfile.sql";//gb2312编码
//        String destFile = "E:\\utf8\\allbackupfile-utf8.sql";//UTF8编码
//         File file = new File(srcFile);
//        try {
//            String bianMa ;
//            bianMa = getFileCharsetName(file);//调用识别编码格式的逻辑
//            System.out.println("编码格式为："+bianMa);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /** 获得文件编码
     * @param file
     * @return
     * @throws Exception
     */

//    public static String getFileCharsetName(File file) throws Exception {
//        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
//        detector.add(new ParsingDetector(false));
//        detector.add(JChardetFacade.getInstance());
//        detector.add(ASCIIDetector.getInstance());
//        detector.add(UnicodeDetector.getInstance());
//        Charset charset = null;
//        try {
//            charset = detector.detectCodepage(file.toURI().toURL());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//        String charsetName = "GBK";
//        if (charset != null) {
//            if (charset.name().equals("US-ASCII")) {
//                charsetName = "ISO_8859_1";
//            } else if (charset.name().startsWith("UTF")) {
//                charsetName = charset.name();// 例如:UTF-8,UTF-16BE.
//            }else if(charset.name().equals("GB2312")){
//                charsetName="GBK";
//            }
//        }
//        return charsetName;//返回最终的编码格式
//    }
//


    public static void main(String[] args) throws IOException {
            //new Conver().process();

            new Conver().process2();

        //new Conver().process3();
        }
    }

