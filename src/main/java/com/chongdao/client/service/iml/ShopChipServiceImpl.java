package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceShopChip;
import com.chongdao.client.mapper.InsuranceShopChipMapper;
import com.chongdao.client.repository.InsuranceShopChipRepository;
import com.chongdao.client.service.ShopChipService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private InsuranceShopChipMapper insuranceShopChipMapper;

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

    @Override
    public ResultResponse getShopChipData(String token, String core, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        if(status != null && status == 99) {
            status = null;
        }
        List<InsuranceShopChip> list = insuranceShopChipMapper.getShopChipDataList(tokenVo.getUserId(), core, status, startDate, endDate);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    public ResultResponse getShopChipAppointShop(Integer shopId, String core, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<InsuranceShopChip> list = insuranceShopChipMapper.getShopChipDataList(shopId, core, status, null, null);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Transactional
    @Override
    public ResultResponse addShopChip(@RequestBody InsuranceShopChip insuranceShopChip) {
        Integer id = insuranceShopChip.getId();
        String core = insuranceShopChip.getCore();
        if(StringUtils.isBlank(core)) {
            return ResultResponse.createByErrorMessage("芯片代码不能为空!");
        }
        //校验芯片码是否已经存在
        Boolean flag = checkChipCodeUnique(id, core);
        if(flag) {
            return ResultResponse.createByErrorMessage("新增/添加的芯片代码已经存在!");
        }
        if(id != null) {
            //编辑
            insuranceShopChip.setUpdateTime(new Date());
            insuranceShopChipRepository.save(insuranceShopChip);
            return ResultResponse.createBySuccess();
        } else {
            //新增
            InsuranceShopChip add = new InsuranceShopChip();
            BeanUtils.copyProperties(insuranceShopChip, add);
            add.setStatus(1);
            add.setCreateTime(new Date());
            insuranceShopChipRepository.save(add);
            return ResultResponse.createBySuccess();
        }
    }

    @Transactional
    @Override
    public ResultResponse removeShopChop(Integer insuranceShopChipId) {
        insuranceShopChipRepository.deleteById(insuranceShopChipId);
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse startShopChipVerify(Integer insuranceShopChipId) {
        InsuranceShopChip insuranceShopChip = insuranceShopChipRepository.findById(insuranceShopChipId).orElse(null);
        if(insuranceShopChip != null) {
            return updateShopChipStatus(insuranceShopChip, 1);
        } else {
            return ResultResponse.createByErrorMessage("无效的宠物芯片ID!");
        }
    }

    @Override
    public ResultResponse confirmShopChipVerify(Integer insuranceShopChipId) {
        InsuranceShopChip insuranceShopChip = insuranceShopChipRepository.findById(insuranceShopChipId).orElse(null);
        if(insuranceShopChip != null) {
            return updateShopChipStatus(insuranceShopChip, 2);
        } else {
            return ResultResponse.createByErrorMessage("无效的宠物芯片ID!");
        }
    }

    @Override
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            //读到流中
//            InputStream inStream = this.getClass().getResourceAsStream("/template/chip_template.xlsx");
            InputStream inStream = new ClassPathResource("/template/chip_template.xlsx").getInputStream();
            // 设置输出的格式
            response.reset();
            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("chip_template.xlsx", "UTF-8"));
            // 循环取出流中的数据
            byte[] b = new byte[200];
            int len;

            while ((len = inStream.read(b)) > 0){
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ResultResponse updateShopChipStatus(InsuranceShopChip insuranceShopChip, Integer targetStatus) {
        insuranceShopChip.setStatus(targetStatus);
        insuranceShopChipRepository.save(insuranceShopChip);
        return ResultResponse.createBySuccess();
    }

    private Boolean checkChipCodeUnique(Integer id, String core) {
        List<InsuranceShopChip> list = insuranceShopChipRepository.findByCore(core);
        boolean flag = false;
        if(list != null && list.size() > 0) {
            if(id == null) {
                flag = true;
            } else {
                for(InsuranceShopChip isc : list) {
                    if(isc.getId() != id) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
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
