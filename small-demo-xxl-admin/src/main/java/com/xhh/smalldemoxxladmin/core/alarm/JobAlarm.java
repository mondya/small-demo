package com.xhh.smalldemoxxladmin.core.alarm;


import com.xhh.smalldemoxxladmin.core.model.XxlJobInfo;
import com.xhh.smalldemoxxladmin.core.model.XxlJobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

}
