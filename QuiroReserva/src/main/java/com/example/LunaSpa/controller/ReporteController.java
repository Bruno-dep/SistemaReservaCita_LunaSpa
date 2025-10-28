package com.example.LunaSpa.controller;

import com.example.LunaSpa.model.Cita;
import com.example.LunaSpa.service.CitaService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private CitaService citaService;

    @GetMapping("/citas")
    public void exportarCitas(HttpServletResponse response) throws IOException {
        List<Cita> citas = citaService.listarTodas();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Citas");
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Paciente", "Doctor", "Fecha y Hora", "Duración", "Estado", "Notas"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        int rowNum = 1;
        for (Cita cita : citas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cita.getId() != null ? cita.getId().toString() : "");
            row.createCell(1).setCellValue(cita.getPaciente() != null ? cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido() : "");
            row.createCell(2).setCellValue(cita.getDoctor() != null ? cita.getDoctor().getNombre() + " (" + cita.getDoctor().getEspecialidad() + ")" : "");
            row.createCell(3).setCellValue(cita.getFechaHora() != null ? cita.getFechaHora().toString() : "");
            row.createCell(4).setCellValue(cita.getDuracion() != null ? cita.getDuracion().toString() : "");
            row.createCell(5).setCellValue(cita.getEstado() != null ? cita.getEstado() : "");
            row.createCell(6).setCellValue(cita.getNotas() != null ? cita.getNotas() : "");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=citas.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}