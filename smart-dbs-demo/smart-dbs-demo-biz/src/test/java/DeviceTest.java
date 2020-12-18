import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zkcloud.api.dbs.common.Message;
import com.zkcloud.api.dbs.model.CreateDeviceRequest;
import com.zkcloud.api.dbs.model.User;
import com.zkteco.dbs.common.init.DBSApi;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import com.zkteco.dbs.common.tool.constants.SysConstants;
import com.zkteco.dbs.common.utils.ModelUtils;
import com.zkteco.dbs.common.utils.ResultUtil;
import com.zkteco.dbs.company.model.Company;
import com.zkteco.dbs.company.service.CompanyService;
import com.zkteco.dbs.device.dto.DeviceDTO;
import com.zkteco.dbs.device.model.Device;
import com.zkteco.dbs.device.service.DeviceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class DeviceTest {

    @Autowired
    private DbsConfig dbsConfig;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CompanyService companyService;

    @Test
    public void addDevice() {

        // 语言参数，前后端分离项目，返回前端的异常信息进行国际化处理
        String language = "zh_CN";

        // 构建设备DTO，设置设备相关基础信息
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setSn("123456789");
        deviceDTO.setAlias("二楼北门");
        // 设备类型(0:考勤设备-ATT 1:门禁设备-ACC)
        deviceDTO.setType(SysConstants.ACC);

        //检查参数是否合法
        ResultUtil.handldBlankError(deviceDTO.getSn(), "E11", language);
        ResultUtil.handldBlankError(deviceDTO.getAlias(), "E13", language);
        ResultUtil.handldNullError(deviceDTO.getType(), "E14", language);

        //判断设备是否已经存在，抛出异常信息
        Device device = deviceService.getOne(new LambdaQueryWrapper<Device>().eq(Device::getSn, deviceDTO.getSn()));
        ResultUtil.handleExistsError(device, "E15", language);

        // 查询企业信息
        Company company = companyService.getByAppKey(dbsConfig.getAppKey());
        Device entity = new Device();
        //拷贝对象属性至 新的设备对象
        ModelUtils.copyPropertiesIgnoreNull(deviceDTO, entity);
        entity.setCompanyId(company.getId());
        entity.setStatus(SysConstants.OFFLINE);
        entity.setEnable(SysConstants.ENABLE);
        entity.setActive(SysConstants.ACTIVATED);
        //保存demo端设备信息
        deviceService.save(entity);

        //调用 新增设备SDK
        // 构建API调用必须的用户,此用户即企业的集成用户
        User apiUser = new User(company.getUserName(), company.getPassword());
        // 初始化SDK的模块
        // 构造新增设备的请求实体
        CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
        createDeviceRequest.setSn(entity.getSn());
        createDeviceRequest.setAlais(entity.getAlias());
        createDeviceRequest.setApiUser(apiUser);
        // 调用SDK，并返回结果
        Message res = DBSApi.dbsClient.addDevice(createDeviceRequest);
        // 抛出异常信息
        ResultUtil.handleDbsResultError(res);


    }
}
