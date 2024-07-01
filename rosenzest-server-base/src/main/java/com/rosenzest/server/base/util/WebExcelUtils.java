package com.rosenzest.server.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * excel 工具类
 * 
 * @author fronttang
 * @date 2021/07/30
 */
@Slf4j
public final class WebExcelUtils {

    public static void exportExcel(HttpServletResponse response, List<List<String>> excelList, String title,
        int rowSize) throws Exception {
        try {

            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.merge(rowSize - 1, title);
            writer.write(excelList, true);
            title += ".xlsx";

            // response.reset(); // reset会把跨域header清空掉
            String fileName = URLEncoder.encode(title, CharsetUtil.UTF_8).replaceAll("\\+", "%20");
            final String attachmentHeader = "Attachment; Filename*=utf-8''" + fileName;

            response.setHeader("Content-Disposition", attachmentHeader);
            response.setContentType("application/octet-stream;charset=UTF-8");

            ServletOutputStream out = response.getOutputStream();
            writer.flush(out, true);
            writer.close();
            IoUtil.close(out);

        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 使用流的方式导出excel
     *
     * @param excelName
     *            要导出的文件名称，如GunsUsers.xls
     * @param pojoClass
     *            Excel实体类
     * @param data
     *            要导出的数据集合
     */
    public static void exportExcelWithStream(String excelName, Class<?> pojoClass, Collection<?> data) {
        try {
            HttpServletResponse response = HttpServletUtil.getResponse();
            String fileName = URLEncoder.encode(excelName, CharsetUtil.UTF_8);
            // response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("application/octet-stream;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, data);
            workbook.write(outputStream);
            IoUtil.close(outputStream);
        } catch (IOException e) {
            log.error(">>> 导出数据异常：{}", e.getMessage());
        }
    }

    /**
     * 使用文件的方式导出excel
     *
     * @param filePath
     *            文件路径，如 d:/demo/demo.xls
     * @param pojoClass
     *            Excel实体类
     * @param data
     *            要导出的数据集合
     */
    public static void exportExcelWithFile(String filePath, Class<?> pojoClass, Collection<?> data) {
        try {
            // 先创建父文件夹
            FileUtil.mkParentDirs(filePath);
            File file = FileUtil.file(filePath);
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, data);
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            IoUtil.close(fos);
        } catch (IOException e) {
            log.error(">>> 导出数据异常：{}", e.getMessage());
        }

    }
}
