package com.larva.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.service.ICommomService;
import com.larva.vo.ResultVO;

@Controller
@RequestMapping("/main/commom")
public class CommomController {
	@Autowired
	private ICommomService areaManageService;
	
    //获取所有省级area 
    @RequestMapping("/get-prov-areas")
    public
    @ResponseBody
    ResultVO getProvAreas() {
    	ResultVO vo = areaManageService.getProvAreaTree();
        return vo;
    }
  //获取所运营商
    @RequestMapping("/get-isps")
    public
    @ResponseBody
    ResultVO getIsps() {
    	ResultVO vo = areaManageService.getIsps();
        return vo;
    }
}
