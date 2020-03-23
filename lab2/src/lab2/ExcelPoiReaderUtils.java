package lab2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelPoiReaderUtils {

	/**
	 * 
	 * ��ȡexcel ��1��sheet ��xls��xlsx��
	 * 
	 * 
	 * 
	 * @param filePath excel·��
	 * 
	 * @param columns  ��������ͷ��
	 * 
	 * @author lizixiang ,2018-05-08
	 * 
	 * @return
	 * 
	 */

	public List<Map<String, String>> readExcel(String filePath, String columns[]) {

		Sheet sheet = null;

		Row row = null;

		Row rowHeader = null;

		List<Map<String, String>> list = null;

		String cellData = null;

		Workbook wb = null;

		if (filePath == null) {

			return null;

		}

		String extString = filePath.substring(filePath.lastIndexOf("."));

		InputStream is = null;

		try {

			is = new FileInputStream(filePath);

			if (".xls".equals(extString)) {

				wb = new HSSFWorkbook(is);

			} else if (".xlsx".equals(extString)) {

				wb = new XSSFWorkbook(is);

			} else {

				wb = null;

			}

			if (wb != null) {

				// ������ű�������

				list = new ArrayList<Map<String, String>>();

				// ��ȡ��һ��sheet

				sheet = wb.getSheetAt(0);

				// ��ȡ�������

//				int rownum = sheet.getPhysicalNumberOfRows();
				int rownum = 20;

				// ��ȡ��һ��

				rowHeader = sheet.getRow(0);

				row = sheet.getRow(0);

				// ��ȡ�������

				int colnum = row.getPhysicalNumberOfCells();

				for (int i = 1; i < rownum; i++) {

					Map<String, String> map = new LinkedHashMap<String, String>();

					row = sheet.getRow(i);

					if (row != null) {

						for (int j = 1; j < 3; j++) {

							cellData = (String) getCellFormatValue(row

									.getCell(j));

							map.put(columns[j-1], cellData);

						}

					} else {

						break;

					}

					list.add(map);

				}

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return list;

	}

	/**
	 * ��ȡ������Ԫ������
	 * 
	 * @param cell
	 * 
	 * @return
	 * 
	 * @author lizixiang ,2018-05-08
	 * 
	 */

	public Object getCellFormatValue(Cell cell) {

		Object cellValue = null;

		if (cell != null) {

			// �ж�cell����

			switch (cell.getCellType()) {

			case Cell.CELL_TYPE_NUMERIC: {

				cellValue = String.valueOf(cell.getNumericCellValue());

				break;

			}

			case Cell.CELL_TYPE_FORMULA: {

				// �ж�cell�Ƿ�Ϊ���ڸ�ʽ

				if (DateUtil.isCellDateFormatted(cell)) {

					// ת��Ϊ���ڸ�ʽYYYY-mm-dd

					cellValue = cell.getDateCellValue();

				} else {

					// ����

					cellValue = String.valueOf(cell.getNumericCellValue());

				}

				break;

			}

			case Cell.CELL_TYPE_STRING: {

				cellValue = cell.getRichStringCellValue().getString();

				break;

			}

			default:

				cellValue = "";

			}

		} else {

			cellValue = "";

		}

		return cellValue;

	}

}
