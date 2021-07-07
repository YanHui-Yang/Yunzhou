package com.yyh.recruit.controller;

import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import com.yyh.recruit.config.QiNiuYunConfig;
import com.yyh.recruit.pojo.Enterprise;
import com.yyh.recruit.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
public class EnterpriseController {

    // 热门企业：1
    private final static String HOTENTERPRISE = "1";

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private QiNiuYunConfig qiNiuYunConfig;

    @GetMapping("/enterprise")
    public Result findAll(@RequestParam(required = false) Integer pageNo,
                          @RequestParam(required = false) Integer pageSize,
                          @RequestParam(required = false) String name) {
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 999;
        }
        Page<Enterprise> pageDate = enterpriseService.pageQuery(name, pageNo, pageSize);
        return new Result(StatusCode.OK, "查询成功！", pageDate.getContent(), pageDate.getTotalElements());
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    @PostMapping("/enterprise")
    public Result add(@RequestBody Enterprise enterprise) {
        enterpriseService.add(enterprise);
        return new Result(StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    @PutMapping("/enterprise/{id}")
    public Result update(@RequestBody Enterprise enterprise, @PathVariable String id) {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return new Result(StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/enterprise/{id}")
    public Result delete(@PathVariable String id) {
        enterpriseService.deleteById(id);
        return new Result(StatusCode.OK, "删除成功");
    }

    /**
     * @Author Tong
     * @Description 热门企业查询，查询ishot=1的热门企业
     * @Date 2021/4/1
     * @Param
     * @return com.yyh.entity.Result
     */
    @GetMapping("/enterprise/search/hotlist")
    public Result hotList() {
        List<Enterprise> list = enterpriseService.hotList(HOTENTERPRISE);
        return new Result(StatusCode.OK, "查询成功", list, enterpriseService.countByIshot(HOTENTERPRISE));
    }

    @DeleteMapping("/enterprise/batch")
    public Result deleteBatch(@RequestParam(value = "ids", required = false) String str) {
        String[] split = str.split(",");
        List<String> ids = new ArrayList<>();
        for (String data : split)
            ids.add(data);
        enterpriseService.deleteBatch(ids);
        return new Result(StatusCode.OK, "删除成功！");
    }

    @PostMapping("/enterprise/image")
    public String qiNiuYunUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        filename = UUID.randomUUID() + filename;
        return qiNiuYunConfig.uploadImgToQiNiu(inputStream, filename);
    }
}
