package com.rosenzest.electric.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosenzest.base.Result;
import com.rosenzest.electric.formb.dto.FormB1;
import com.rosenzest.electric.formb.dto.FormB10;
import com.rosenzest.electric.formb.dto.FormB11;
import com.rosenzest.electric.formb.dto.FormB12;
import com.rosenzest.electric.formb.dto.FormB13;
import com.rosenzest.electric.formb.dto.FormB14;
import com.rosenzest.electric.formb.dto.FormB15;
import com.rosenzest.electric.formb.dto.FormB2;
import com.rosenzest.electric.formb.dto.FormB3;
import com.rosenzest.electric.formb.dto.FormB4;
import com.rosenzest.electric.formb.dto.FormB5;
import com.rosenzest.electric.formb.dto.FormB6;
import com.rosenzest.electric.formb.dto.FormB7;
import com.rosenzest.electric.formb.dto.FormB8;
import com.rosenzest.electric.formb.dto.FormB9;
import com.rosenzest.electric.formb.dto.FormBB1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "检测表B")
@RestController
@RequestMapping("/formb/")
public class FormBController {

	@ApiOperation(tags = "检测表B", value = "B1")
	@PostMapping("/b1")
	public Result<?> formb1(@RequestBody FormB1 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "BB1")
	@PostMapping("/bb1")
	public Result<?> formbb1(@RequestBody FormBB1 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B2")
	@PostMapping("/b2")
	public Result<?> formb2(@RequestBody FormB2 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B3")
	@PostMapping("/b3")
	public Result<?> formb3(@RequestBody FormB3 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B4")
	@PostMapping("/b4")
	public Result<?> formb4(@RequestBody FormB4 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B5")
	@PostMapping("/b5")
	public Result<?> formb5(@RequestBody FormB5 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B6")
	@PostMapping("/b6")
	public Result<?> formb6(@RequestBody FormB6 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B7")
	@PostMapping("/b7")
	public Result<?> formb7(@RequestBody FormB7 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B8")
	@PostMapping("/b8")
	public Result<?> formb8(@RequestBody FormB8 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B9")
	@PostMapping("/b9")
	public Result<?> formb9(@RequestBody FormB9 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B10")
	@PostMapping("/b10")
	public Result<?> formb10(@RequestBody FormB10 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B11")
	@PostMapping("/b11")
	public Result<?> formb11(@RequestBody FormB11 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B12")
	@PostMapping("/b12")
	public Result<?> formb12(@RequestBody FormB12 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B13")
	@PostMapping("/b13")
	public Result<?> formb13(@RequestBody FormB13 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B14")
	@PostMapping("/b14")
	public Result<?> formb14(@RequestBody FormB14 form) {
		return Result.SUCCESS();
	}

	@ApiOperation(tags = "检测表B", value = "B15")
	@PostMapping("/b15")
	public Result<?> formb15(@RequestBody FormB15 form) {
		return Result.SUCCESS();
	}
}
