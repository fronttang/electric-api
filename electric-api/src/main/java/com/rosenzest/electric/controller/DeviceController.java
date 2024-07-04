package com.rosenzest.electric.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.server.base.controller.ServerBaseController;

import io.swagger.annotations.Api;

/**
 * @author fronttang
 * @since 2024-06-27
 */
@Api(tags = "检测仪器")
@RestController
@RequestMapping("/device")
public class DeviceController extends ServerBaseController {
	
}
