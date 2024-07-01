package com.rosenzest.electric.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class UserDeviceDto {

	/**
     * 设备类型
     */
	@ApiModelProperty("设备类型")
    @NotBlank(message = "设备类型不能为空")
    private String deviceType;

    /**
     * 设备ID
     */
	@ApiModelProperty("设备ID")
    @NotNull(message = "设备ID不能为空")
    private Long deviceId;
}
