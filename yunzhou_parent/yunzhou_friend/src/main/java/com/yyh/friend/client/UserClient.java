package com.yyh.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author: Tong
 * Date: 2021/4/11
 * Description:
 */
@FeignClient("yunzhou-user")
public interface UserClient {

    @RequestMapping(value = "/user/{userid}/{friendid}/{type}", method = RequestMethod.PUT)
    void updateFanscountAndFollowcount(@PathVariable("type") int type,
                                       @PathVariable("userid") String userid,
                                       @PathVariable("friendid") String friendid);
}
