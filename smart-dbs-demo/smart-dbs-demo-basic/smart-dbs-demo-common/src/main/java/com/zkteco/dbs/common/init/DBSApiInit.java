package com.zkteco.dbs.common.init;

import com.zkcloud.api.dbs.DBSClient;
import com.zkcloud.api.dbs.common.Credential;
import com.zkcloud.api.dbs.common.profile.HttpProfile;
import com.zkteco.dbs.common.tool.config.DbsConfig;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * DBSApiInit
 *
 * @author sheldon.wu
 * @date 2020/11/20 16:06
 * @since 1.0.0
 */
@Component
@EnableScheduling
public class DBSApiInit {

    @Autowired
    private DbsConfig dbsConfig;

    @PostConstruct
    public void init() {
        // 项目启动马上进行DBSClient初始化
        Credential credential = new Credential(dbsConfig.getAppKey(), dbsConfig.getAppSecret());
        DBSApi.dbsClient = new DBSClient(dbsConfig.getEndpoint(), credential);
        DBSApi.dbsClient.getClientProfile().getHttpProfile().setProtocol(dbsConfig.isSsl() ? HttpProfile.REQ_HTTPS : HttpProfile.REQ_HTTP);
        DBSApi.dbsClient.refreshToken();
    }

    /**
     * 定时刷新token，防止token过期
     */
    @Scheduled(cron = "0 0/9 * * * ? ")
    public void refreshToken() {
        if (DBSApi.dbsClient == null) {
            init();
            return;
        }

        DBSApi.dbsClient.refreshToken();
    }
}
