package com.rosenzest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.rosenzest.model.base.entity.BaseEntity;
import com.rosenzest.model.base.service.IModelBaseService;
import com.rosenzest.model.base.service.ModelBaseServiceImpl;

/**
 * @author fronttang
 * @date 2021/06/30
 */
public class CodeGenerator {

	/**
	 * <p>
	 * 读取控制台内容
	 * </p>
	 */
	public static String scanner(String tip) {
		Scanner scanner = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("请输入" + tip + "：");
		System.out.println(help.toString());
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StringUtils.isNotBlank(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}

	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		final String projectPath = System.getProperty("user.dir");
		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor("fronttang");
		gc.setOpen(false);
		gc.setDateType(DateType.ONLY_DATE);
		// gc.setIdType(IdType.ASSIGN_ID);
		// gc.setSwagger2(true); // 实体属性 Swagger2 注解
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl(
				"jdbc:mysql://127.0.0.1:3306/dianantong?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
		// dsc.setSchemaName("public");
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("password");
		mpg.setDataSource(dsc);

		// 包配置
		final PackageConfig pc = new PackageConfig();
		// pc.setModuleName(scanner("模块名"));
		pc.setModuleName("station");
		pc.setParent("com.rosenzest.electric");
		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		// 如果模板引擎是 freemarker
		String templatePath = "/templates/mapper.xml.ftl";
		// 如果模板引擎是 velocity
		// String templatePath = "/templates/mapper.xml.vm";

		// 自定义输出配置
		List<FileOutConfig> focList = new ArrayList();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
				return projectPath + "/src/main/resources/mapper/" + pc.getModuleName() + "/"
						+ tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
			}
		});
		/*
		 * cfg.setFileCreate(new IFileCreate() {
		 * 
		 * @Override public boolean isCreate(ConfigBuilder configBuilder, FileType
		 * fileType, String filePath) { // 判断自定义文件夹是否需要创建
		 * checkDir("调用默认方法创建的目录，自定义目录用"); if (fileType == FileType.MAPPER) { // 已经生成
		 * mapper 文件判断存在，不想重新生成返回 false return !new File(filePath).exists(); } //
		 * 允许生成模板文件 return true; } });
		 */
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig();

		// 配置自定义输出模板
		// 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
		// templateConfig.setEntity("templates/entity2.java");
		// templateConfig.setService();
		// templateConfig.setController();

		templateConfig.setXml(null);
		mpg.setTemplate(templateConfig);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setSuperEntityClass(BaseEntity.class);
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setSuperMapperClass("com.rosenzest.model.base.mapper.ModelBaseMapper");
		strategy.setSuperServiceClass(IModelBaseService.class);
		strategy.setSuperServiceImplClass(ModelBaseServiceImpl.class);
		// 公共父类
		// strategy.setSuperControllerClass("com.xmzy.base.controller.BaseController");
		// 写于父类中的公共字段
		strategy.setSuperEntityColumns("SEQ_NO", "CRE_TLR", "CRE_DTE", "CRE_BR", "CRE_BK", "UPD_TLR", "UPD_DTE",
				"UPD_BR", "UPD_BK", "CREATE_TIME", "CREATE_BY", "UPDATE_TIME", "UPDATE_BY");
		// strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
		strategy.setInclude("charging_pile");
		// strategy.setTablePrefix("xm_");
		strategy.setControllerMappingHyphenStyle(true);
		// strategy.setTablePrefix(pc.getModuleName() + "_");
		mpg.setStrategy(strategy);
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

	// ci_cust_info,ci_cust_info_ext,ci_cust_profile,ci_cust_identify,ci_cust_relation,ci_enterprise_info,ci_enterprise_info_ext,ci_group_info,ci_supplier_info,ci_cust_info_boa_ext

	// xm_cust_info,xm_user_info,xm_user_name

	// xm_address

	// xm_pact_info,xm_pact_sign,xm_euq_relate,xm_enterprise_info,xm_multiple_enterprise

	// xm_car_info,xm_car_number,xm_driver,xm_etc_address,xm_etc_audit,xm_etc_bank,xm_etc_bind,xm_etc_br,xm_etc_history,xm_etc_info,xm_etc_progress,xm_etc_trajectory,xm_etc_trajectory_combo

	// xm_bill_detail,xm_bill_info,xm_prepayment_apply,xm_repay_record,xm_toll_info,xm_trajectory_order
}
