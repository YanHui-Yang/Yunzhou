package com.yyh.gathering.controller;

import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.gathering.config.QiNiuYunConfig;
import com.yyh.gathering.pojo.Gathering;
import com.yyh.gathering.service.GatheringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@RefreshScope
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;

    @Autowired
    private QiNiuYunConfig qiNiuYunConfig;

    @GetMapping("/gathering")
    public Result findAll(@RequestParam(required = false) Integer pageNo,
                          @RequestParam(required = false) Integer pageSize,
                          @RequestParam(required = false) String name) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 999;
        }
        Page<Gathering> pageDate = gatheringService.pageQuery(name, pageNo, pageSize);
        return new Result(StatusCode.OK, "查询成功！", pageDate.getContent(), pageDate.getTotalElements());
    }

    /**
     * 增加
     *
     * @param gathering
     */
    @PostMapping("/gathering")
    public Result add(@RequestBody Gathering gathering) {

        gatheringService.add(gathering);
        return new Result(StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @PutMapping("/gathering/{id}")
    public Result update(@RequestBody Gathering gathering, @PathVariable String id) {
        gathering.setId(id);
        gatheringService.update(gathering);
        return new Result(StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/gathering/{id}")
    public Result delete(@PathVariable String id) {
        System.out.println(id);
        gatheringService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功");
    }

    @DeleteMapping("/gathering/batch")
    public Result deleteBatch(@RequestParam(value = "ids", required = false) String str) {
        String[] split = str.split(",");
        List<String> ids = new ArrayList<>();
        for (String data : split)
            ids.add(data);
        gatheringService.deleteBatch(ids);
        return new Result(StatusCode.OK, "删除成功！");
    }

    @PostMapping("/gathering/image")
    public String qiNiuYunUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        //为文件重命名：uuid+filename
        filename = UUID.randomUUID() + filename;
        return qiNiuYunConfig.uploadImgToQiNiu(inputStream, filename);
    }

}
