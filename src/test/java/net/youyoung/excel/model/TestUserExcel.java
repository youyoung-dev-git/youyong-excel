package net.youyoung.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import net.youyoung.excel.annotation.*;
import net.youyoung.excel.style.ExcelAlign;
import net.youyoung.excel.style.ExcelColor;

import java.time.LocalDate;

/**
 * Test model for Excel operations.
 */
@ExcelSheet(name = "Users")
@ExcelHeaderStyle(backgroundColor = ExcelColor.GREY_25_PERCENT, bold = true, align = ExcelAlign.CENTER)
public class TestUserExcel {

    @ExcelColumn(header = "Name", order = 1)
    @ExcelStyle(bold = true, fontColor = ExcelColor.BLUE)
    @ExcelProperty("Name")
    private String name;

    @ExcelColumn(header = "Email", order = 2, width = 30)
    @ExcelProperty("Email")
    private String email;

    @ExcelColumn(header = "Join Date", order = 3)
    @ExcelDateFormat("yyyy-MM-dd")
    @ExcelProperty("Join Date")
    private LocalDate joinDate;

    @ExcelColumn(header = "Points", order = 4, hideIfEmpty = true)
    @ExcelNumberFormat("#,###")
    @ExcelProperty("Points")
    private Integer points;

    public TestUserExcel() {
    }

    public TestUserExcel(String name, String email, LocalDate joinDate, Integer points) {
        this.name = name;
        this.email = email;
        this.joinDate = joinDate;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
