package com.zkteco.dbs.common.utils;

import com.zkteco.dbs.common.tool.constants.FileTypeEnum;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

/**
 * FileUploadUtil
 *
 * @author sheldon.wu
 * @date 2020/11/30 14:22
 * @since 1.0.0
 */
@Slf4j
@Component
public class FileUploadUtil {

    private static final String FILE_FORMAT = "jpg,jpeg,gif,png";

    private static final long FILE_SIZE = 1024 * 1024 * 2;

    private static final String SECOND_PATH = "/temp";

    @Value("${dbs.upload.path}")
    private String basicPath;

    public String upload(MultipartFile file, String type, String lang) {
        ResultUtil.handldNullError(file, "E25", lang);

        //获取业务对应二级目录
        String path = SECOND_PATH + FileTypeEnum.getPathByType(type);
        //获取文件后缀
        String suffix = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!FILE_FORMAT.toUpperCase().contains(suffix.toUpperCase())) {
            // 图片格式不符合
            ResultUtil.handldErrorInfo("E22", lang);
        }

        //创建文件夹
        File folder = new File(basicPath + path);
        // 输出文件夹绝对路径  -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
        log.info("file upload path:{}", folder.getAbsolutePath());
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;

        try {
            file.transferTo(new File(folder, filename));
        } catch (Exception e) {
            e.printStackTrace();
            // 保存文件异常
            ResultUtil.handldErrorInfo("E08", lang);
        }

        String filePath = path + filename;

        return filePath;
    }

    /**
     * 通过图片的url获取图片的base64字符串
     * @param imgUrl    图片url
     * @return 返回图片base64的字符串
     */
    public String image2Base64(String imgUrl, String lang) throws FileNotFoundException {

        File file = new File(imgUrl);
        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return encode(outStream.toByteArray());
        } catch (Exception e) {
            ResultUtil.handldErrorInfo("E10", lang);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public String encode(byte[] image) {
        BASE64Encoder decoder = new BASE64Encoder();
        return replaceEnter(decoder.encode(image));
    }

    public String replaceEnter(String str) {
        String reg = "[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

}
