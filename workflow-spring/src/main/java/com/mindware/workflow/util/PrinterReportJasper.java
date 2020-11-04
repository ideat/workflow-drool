package com.mindware.workflow.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class PrinterReportJasper {
    public static byte[] imprimirComoPdf(InputStream istreams, Collection<?> datasources, Map<String, Object> params) throws JRException {
        Objects.requireNonNull(istreams,"'istreams' no puede ser omitido");
        Objects.requireNonNull(datasources,"'datasources' no puede ser omitido");

        ReportTemplateJrxml report = new ReportTemplateJrxml(istreams,datasources,params);


        JasperPrint jp = new JasperPrint();
        jp = report.getReport();
        return getReportPdf(jp);

    }

    public static byte[] printCustomFile(InputStream istreams, Collection<?> datasources, Map<String, Object> params, String typeFile) throws JRException {
        Objects.requireNonNull(istreams,"'istreams' no puede ser omitido");
        Objects.requireNonNull(datasources,"'datasources' no puede ser omitido");

        ReportTemplateJrxml report = new ReportTemplateJrxml(istreams,datasources,params);
        JasperPrint jp = new JasperPrint();
        jp = report.getReport();
        if(typeFile.equals("pdf")) {
            return getReportPdf(jp);
        }else if(typeFile.equals("xls")){
            return getReportExcel(jp);
        }
        return getReportPdf(jp);
    }

    public static byte[] getReportPdf(final JasperPrint jp)  {
        try {
            return JasperExportManager.exportReportToPdf(jp);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    public static byte[] getReportExcel(final JasperPrint jp) throws JRException {
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
        jp.setProperty(net.sf.jasperreports.engine.JRParameter.IS_IGNORE_PAGINATION, "true");
        xlsxExporter.setExporterInput(new SimpleExporterInput(jp));
        xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
        SimpleXlsxReportConfiguration xlsxreportConfig = new SimpleXlsxReportConfiguration();
        xlsxreportConfig.setSheetNames(new String[] { "Some Report" });
        xlsxreportConfig.setRemoveEmptySpaceBetweenRows(true);
        xlsxreportConfig.setForcePageBreaks(false);
        xlsxreportConfig.setWrapText(false);
        xlsxreportConfig.setCollapseRowSpan(true);
        xlsxExporter.setConfiguration(xlsxreportConfig);
        xlsxExporter.exportReport();
        return xlsReport.toByteArray();
    }
}
