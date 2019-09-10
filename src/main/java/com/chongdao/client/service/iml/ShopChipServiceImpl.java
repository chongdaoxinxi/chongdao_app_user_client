package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceShopChip;
import com.chongdao.client.repository.InsuranceShopChipRepository;
import com.chongdao.client.service.ShopChipService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/9/10
 * @Version 1.0
 **/
@Service
public class ShopChipServiceImpl implements ShopChipService {
    @Autowired
    private InsuranceShopChipRepository insuranceShopChipRepository;

    @Override
    @Transactional
    public ResultResponse importShopChipData(String token, MultipartFile file) throws IOException {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Integer shopId = tokenVo.getUserId();
        InputStream inputStream = file.getInputStream();
//        Workbook workbook = new HSSFWorkbook(inputStream);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Integer successNum = 0;
        if (workbook != null) {
            //遍历工作簿中的sheet,第一层循环所有sheet表
            for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
                Sheet sheet = workbook.getSheetAt(index);
                if (sheet == null) {
                    continue;
                }
                System.out.println("表单行数：" + sheet.getLastRowNum());
                //如果当前行没有数据跳出循环，第二层循环单sheet表中所有行
                for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    //根据文件头可以控制从哪一行读取，在下面if中进行控制
                    if (row == null) {
                        continue;
                    }
                    Cell cell = row.getCell(0);
                    String core = getCellValue(cell);
                    if (StringUtils.isNotBlank(core)) {
                        InsuranceShopChip isc = new InsuranceShopChip();
                        isc.setCore(core);
                        isc.setShopId(shopId);
                        isc.setStatus(1);
                        isc.setCreateTime(new Date());
                        isc.setUpdateTime(new Date());
                        insuranceShopChipRepository.save(isc);
                        successNum++;
                    }
//                    //遍历每一行的每一列，第三层循环行中所有单元格
//                    for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
//                        Cell cell = row.getCell(cellIndex);
//                        System.out.println("遍历行中cell数据:" + getCellValue(cell));
//                    }
                }
            }
        } else {
            return ResultResponse.createBySuccessMessage("导入失败, 无数据!");
        }
        inputStream.close();
        if (successNum > 0) {
            return ResultResponse.createBySuccessMessage("成功导入" + successNum + "条宠物芯片数据, 可以在管理端查看!");
        } else {
            return ResultResponse.createBySuccessMessage("导入失败!");
        }
    }

    public static String getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        String cellValue = "";
        if (cell == null || cell.toString().trim().equals("")) {
            return null;
        }

        if (cellType == CellType.STRING) {
            cellValue = cell.getStringCellValue().trim();
            return cellValue = StringUtil.isEmpty(cellValue) ? "" : cellValue;
        }
        if (cellType == CellType.NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
//                cellValue = DateFormatUtil.formatDurationYMD(cell.getDateCellValue().getTime());
            } else {  //否
                cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
            }
            return cellValue;
        }
        if (cellType == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
            return cellValue;
        }
        return null;

    }
}
