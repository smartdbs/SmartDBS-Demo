package com.zkteco.dbs.noScreenDevice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkteco.dbs.common.base.model.BaseDTO;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.AES256Utils;
import com.zkteco.dbs.common.utils.FileUploadUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.service.DeviceService;
import com.zkteco.dbs.noScreenDevice.dao.NoScreenDeviceMapper;
import com.zkteco.dbs.noScreenDevice.service.NoScreenDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BioTemplateServiceImpl
 * @Description: 服务实现类
 * @Author able.lee
 * @Date 2020/11/25 10:42
 * @Since v1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NoScreenDeviceServiceImpl extends ServiceImpl<NoScreenDeviceMapper, Device> implements NoScreenDeviceService {


    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    @Value("${dbs.upload.path}")
    private String basicPath;

    /**
     * 加密key
     */
    private static final String KEY = "c09eb7463827cda245f3a398bd453c42";


    @Override
    public void addNoScreenDevice(BaseDTO<DeviceDTO> dto) {

        DeviceDTO deviceDTO = dto.getPayload();
        String account =deviceDTO.getAccount();
        String password = deviceDTO.getPassword();

        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());

        // 修改企业集成账号和密码
        company.setUserName(account);
        company.setPassword(password);
        companyService.updateById(company);

        // 添加设备
        deviceService.save(dto);

    }

    @Override
    public boolean pingIP(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        // 将要执行的ping命令,此命令是windows格式的命令
        Runtime r = Runtime.getRuntime();

        String pingCommand = "ping -n " + pingTimes + " " + ipAddress;

        try {   // 执行命令并获取输出
            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            in = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回true
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回false
            return false;
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String generateQRCode() {
        // 二维码信息内返回4个信息 demo节点，集成账号，密码，公司名称
        // demo节点
        String demoEndPoint = dbsConfig.getDemoEndpoint();
        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        // 集成账号，密码，公司名称
        String account = company.getUserName();
        String password = company.getPassword();
        String companyName = company.getCompanyName();
        // 用Map封装
        Map<String,Object> map = new HashMap<>(SysConstants.INIT_SIZE);
        map.put("demoEndPoint",demoEndPoint);
        map.put("account",account);
        map.put("password",password);
        map.put("companyName",companyName);
        String jsonStr = JSON.toJSONString(map);

        // 对内容进行加密
        String encryption = AES256Utils.encryptToBase64(jsonStr,KEY);
        // 调用生成二维码
        String result = fileUploadUtil.generateQRCode(encryption);

        return result;

    }

    private static int getCheckResult(String line) {
        String trueZF = "字节=";
        if (line.contains(trueZF)) {
            return 1;
        } else {
            return 0;
        }
    }
}
